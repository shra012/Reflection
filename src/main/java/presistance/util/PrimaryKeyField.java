package presistance.util;

import presistance.annotations.PrimaryKey;

import java.lang.reflect.Field;

public class PrimaryKeyField {

    private Field field;
    private PrimaryKey primaryKey;

    public PrimaryKeyField(Field field) {
        this.field = field;
        this.primaryKey = this.field.getAnnotation(PrimaryKey.class);
    }

    @Override
    public String toString() {
        return "PrimaryKeyField{" +
                " field name = " + field.getName() + ", " +
                " field type = " + field.getType() +
                " }";
    }

    public Field getField() {
        return field;
    }

    public String getName(){
        return primaryKey.columnName();
    }
}
