package presistance.util;

import java.lang.reflect.Field;

public class PrimaryKeyField {

    private Field field;

    public PrimaryKeyField(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "PrimaryKeyField{" +
                " field name = " + field.getName() + ", " +
                " field type = " + field.getType() +
                " }";
    }
}
