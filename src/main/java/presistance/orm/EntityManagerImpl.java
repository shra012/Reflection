package presistance.orm;

import presistance.util.ColumnField;
import presistance.util.MetaModel;
import sun.net.ConnectionResetException;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

public class EntityManagerImpl<T> implements EntityManager<T> {
    private AtomicLong idGenerator = new AtomicLong(0L);
    private class PreparedStatementWrapper{

        private PreparedStatement statement;

        public PreparedStatementWrapper(PreparedStatement statement) {
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
    }
    public EntityManagerImpl(Class<T> clss) {
    }

    public EntityManagerImpl() {
    }

    @Override
    public void persist(T t) throws SQLException, IllegalAccessException {
        MetaModel metaModel = MetaModel.of(t.getClass());
        String sql = metaModel.buildInsertRequest();
        PreparedStatement statement = prepareStatementWith(sql).addParameters(t);
        statement.executeUpdate();
    }

    private PreparedStatementWrapper prepareStatementWith(String sql) throws SQLException {
        Connection connection =  DriverManager.getConnection("jdbc:h2:~/Programming/IDE/Intellij/Workspace/Reflection/src/main/resources/h2-db/db-files","sa","");
        PreparedStatement statement = connection.prepareStatement(sql);
        return new PreparedStatementWrapper(statement);
    }

    @Override
    public T read(Class<?> clazz, long primaryKey) {
        return null;
    }
}
