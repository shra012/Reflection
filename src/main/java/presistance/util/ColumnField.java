package presistance.util;

import java.lang.reflect.Field;

public class ColumnField {

    private Field field;

    public ColumnField(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "ColumnField{" +
                " field name = " + field.getName() + ", " +
                " field type = " + field.getType() +
                " }";
    }
}
