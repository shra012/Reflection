package presistance.util;

import presistance.annotations.Column;
import presistance.annotations.PrimaryKey;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MetaModel<T> {

    private Class<T> clazz;

    public  MetaModel(Class<T> clazz) {

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


}
