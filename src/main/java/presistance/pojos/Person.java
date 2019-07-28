package presistance.pojos;

import presistance.annotations.Column;
import presistance.annotations.PrimaryKey;

public class Person {

    @PrimaryKey(columnName = "id")
    private long id;
    @Column(columnName = "name")
    private String name;
    @Column(columnName = "age")
    private int age;

    public Person() {
    }

    public Person( String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
