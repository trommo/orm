package model;

import annotation.*;

import java.util.List;

@EntitySoft
@TableSoft (name = "users")
public class User {

    @IdSoft
    @ColumnSoft(name = "id")
    @GeneratedValueSoft
    private int id;

    @ColumnSoft()
    private String name;

    @ExcludeSoft
    private int ageR;

    @ColumnSoft(name = "age")
    //@ExcludeSoft
    private Integer age;

    @ColumnSoft(name = "city")
    private String city;

    @OneToManySoft()
    @ForeignKeySoft
    private List<Auto> autos;


    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }
}
