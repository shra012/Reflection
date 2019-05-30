package presistance.driver;

import presistance.pojos.Person;
import presistance.util.ColumnField;
import presistance.util.MetaModel;
import presistance.util.PrimaryKeyField;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        MetaModel<Person> metaModel = MetaModel.of(Person.class);

        PrimaryKeyField primaryKeyField = metaModel.getPrimaryKey();
        List<ColumnField> columnFieldList = metaModel.getColumns();
        System.out.println(primaryKeyField);
        System.out.println(columnFieldList);
    }
}
