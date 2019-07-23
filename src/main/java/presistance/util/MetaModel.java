package presistance.util;

import presistance.annotations.Column;
import presistance.annotations.PrimaryKey;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MetaModel {

    private Class<?> clazz;

    public  MetaModel(Class<?> clazz) {

        this.clazz = clazz;
    }

    public static <T> MetaModel of(Class<T> clazz){

        return new MetaModel(clazz);
    }

    public PrimaryKeyField getPrimaryKey(){
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            final PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
            if(primaryKey!=null){
                PrimaryKeyField primaryKeyField = new PrimaryKeyField(field);
                return  primaryKeyField;
            }

        }
        throw new IllegalArgumentException("No Primary Key found in class "+clazz.getSimpleName());
    }

    public List<ColumnField> getColumns(){
        Field[] fields = clazz.getDeclaredFields();
        List<ColumnField> columnFields = new ArrayList<ColumnField>();
        for (Field field : fields){
            final Column column = field.getAnnotation(Column.class);
            if(column!=null){
                ColumnField columnField = new ColumnField(field);
                columnFields.add(columnField);
            }

        }
        return columnFields;
    }


    public String buildInsertRequest() {
    	//insert into person (id, name, age) values (?, ?, ?)
        String elementNames = buildColumnNames();
        int numberOfColumns = getColumns().size()+1;
        String questionMarkString = IntStream.range(0,numberOfColumns).mapToObj(index->"?").collect(Collectors.joining(", "));
        return "insert into "+this.clazz.getSimpleName() + " ("+elementNames+") values ("+questionMarkString+")";
    }

    private String buildColumnNames() {
        String primaryKeyColumnName = getPrimaryKey().getField().getName();
        List<String> columnNames = getColumns().stream().map(field ->  field.getField().getName()).collect(Collectors.toList());
        columnNames.add(0,primaryKeyColumnName);
        return String.join(", ",columnNames);
    }

	public String buildReadRequest() {
		//select id, name, age from Person where id = ?
		String elementNames = buildColumnNames();
		String primaryKeyColumnName = getPrimaryKey().getField().getName();
		return "select "+elementNames+" from "+this.clazz.getSimpleName()+" where "+primaryKeyColumnName+" = ?" ;
	}
}
