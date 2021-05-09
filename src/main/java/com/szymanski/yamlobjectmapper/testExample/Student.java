package com.szymanski.yamlobjectmapper.testExample;

import com.szymanski.yamlobjectmapper.annotations.YamlClass;
import com.szymanski.yamlobjectmapper.annotations.YamlId;
import com.szymanski.yamlobjectmapper.annotations.YamlKey;

@YamlClass(name = "student")
public class Student {

    @YamlId
    protected int id;
    @YamlKey(name = "age")
    private int age;
    @YamlKey(name = "first_name")
    private String firstName;
    @YamlKey(name = "second_name")
    private String secondName;

    public Student(int id, int age, String firstName, String secondName) {
        this.id = id;
        this.age = age;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }
}
