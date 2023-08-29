package ru.Raingor.models;

public class Person {
    private int id;
    private String fullName;
    private String age;

    public Person() {
    }

    public Person(int id, String fullName, String yearOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.age = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String yearOfBirth) {
        this.age = yearOfBirth;
    }
}
