package presistance.util;

import presistance.annotations.Column;

import java.lang.reflect.Field;

public class ColumnField {

    private Field field;
    private Column column;

    public ColumnField(Field field) {
        this.field = field;
        this.column=this.field.getAnnotation(Column.class);
    }

    @Override
    public String toString() {
        return "ColumnField{" +
                " field name = " + field.getName() + ", " +
                " field type = " + field.getType() +
                " }";
    }

    public Field getField() {
        return field;
    }

    public String getName(){
        return column.columnName();
    }
}
