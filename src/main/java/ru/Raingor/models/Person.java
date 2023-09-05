package ru.Raingor.models;

public class Person {
    private int person_id;
    private String fullName;
    private String age;

    public Person() {
    }

    public Person(int person_id, String fullName, String age) {
        this.person_id = person_id;
        this.fullName = fullName;
        this.age = age;
    }


    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
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

    public void setAge(String age) {
        this.age = age;
    }
}
