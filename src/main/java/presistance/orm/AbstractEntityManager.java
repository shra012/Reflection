package presistance.orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

import presistance.util.ColumnField;
import presistance.util.MetaModel;
import presistance.util.PrimaryKeyField;

public abstract class AbstractEntityManager<T> implements EntityManager<T> {

    private AtomicLong idGenerator = new AtomicLong(3L);

    private class PreparedStatementWrapper{

        private PreparedStatement statement;

        PreparedStatementWrapper(PreparedStatement statement) {
            this.statement = statement;
        }

        public PreparedStatement addParameters(T t) throws SQLException, IllegalAccessException {
            MetaModel metaModel = MetaModel.of(t.getClass());
            Class<?> primaryKeyType = metaModel.getPrimaryKey().getField().getType();
            if(primaryKeyType == long.class){

                long id = idGenerator.getAndIncrement();
                statement.setLong(1,id);
                Field field = metaModel.getPrimaryKey().getField();
                field.setAccessible(true);
                field.set(t,id);

            }
            for (int columnIndex = 0; columnIndex < metaModel.getColumns().size(); columnIndex++) {
                ColumnField columnField = metaModel.getColumns().get(columnIndex);
                Field field =  columnField.getField();
                Class<?> fieldType = field.getType();
                field.setAccessible(true);
                Object value = field.get(t);
                if(fieldType == int.class){
                    statement.setInt(columnIndex+2,(int)value);
                }else if(fieldType == String.class){
                    statement.setString(columnIndex+2,(String)value);
                }
            }
            return statement;
        }

        public PreparedStatement andPrimaryKey(Object primaryKey) throws SQLException {
            if(primaryKey.getClass() == Long.class) {
                statement.setLong(1, (Long) primaryKey);
            }
            return statement;
        }
    }

    public AbstractEntityManager(Class<T> clss) {

    }

    public AbstractEntityManager() {}

    @Override
    public void persist(T t) throws SQLException, IllegalAccessException {
        MetaModel metaModel = MetaModel.of(t.getClass());
        String sql = metaModel.buildInsertRequest();
        System.out.println("Prepared statement to execute query : "+sql);
        try (PreparedStatement statement = prepareStatementWith(sql).addParameters(t)) {
            statement.executeUpdate();
        }
    }

    @Override
    public T read(Class<T> clazz, long primaryKey) throws  Exception  {
        MetaModel metaModel = MetaModel.of(clazz);
        String sql = metaModel.buildReadRequest();
        System.out.println("Prepared statement to execute query : "+sql);
        try (PreparedStatement statement = prepareStatementWith(sql).andPrimaryKey(primaryKey)) {
            ResultSet resultSet = statement.executeQuery();
            return buildInstanceFrom(clazz,resultSet);
        }

    }

    private T buildInstanceFrom(Class<T> clazz, ResultSet resultSet) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
        MetaModel metaModel = MetaModel.of(clazz);
        T t = clazz.getConstructor().newInstance();
        PrimaryKeyField primaryKeyFieldInstance = metaModel.getPrimaryKey();
        Field primaryKeyField = metaModel.getPrimaryKey().getField();
        String primaryKeyElementName = primaryKeyFieldInstance.getName();
        Class<?> primaryKeyType = primaryKeyField.getType();
        resultSet.next();

        if(primaryKeyType == long.class) {
            long primaryKeyValue = resultSet.getInt(primaryKeyElementName);
            primaryKeyField.setAccessible(true);
            primaryKeyField.set(t,primaryKeyValue);
        }

        for (ColumnField column  : metaModel.getColumns()) {
            Field columnField = column.getField();
            String columnElementName = column.getName();
            Class<?> columnType = columnField.getType();

            if(columnType == int.class) {
                int columnValue = resultSet.getInt(columnElementName);
                columnField.setAccessible(true);
                columnField.set(t,columnValue);
            }
            if(columnType == String.class) {
                String columnValue = resultSet.getString(columnElementName);
                columnField.setAccessible(true);
                columnField.set(t,columnValue);
            }
        }

        return t;
    }

    private PreparedStatementWrapper prepareStatementWith(String sql) throws SQLException {
        Connection connection =  buildConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        return new PreparedStatementWrapper(statement);
    }

    public abstract  Connection buildConnection() throws SQLException;

}

