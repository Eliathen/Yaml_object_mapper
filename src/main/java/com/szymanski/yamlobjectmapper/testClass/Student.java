package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@YamlClass(name = "students")
public class Student extends User {

    @YamlKey(name = "age")
    private int age;
    @YamlKey(name = "first_name")
    private String firstName;
    @YamlKey(name = "second_name")
    private String secondName;
    @YamlKey(name = "marks")
    private List<Integer> marks;
    @YamlKey(name = "birthDate", pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @YamlKey(name = "random_text")
    private List<String> randomText;
    @YamlKey(name = "is_alive")
    private boolean isAlive;
    @YamlManyToMany
    @YamlKey(name = "subjects")
    private List<Subject> subjects;

    public Student(int id, Address address, int age, String firstName, String secondName, List<Integer> marks, LocalDate birthDate, List<String> randomText, boolean isAlive) {
        super(id, address);
        this.age = age;
        this.firstName = firstName;
        this.secondName = secondName;
        this.marks = marks;
        this.birthDate = birthDate;
        this.randomText = randomText;
        this.isAlive = isAlive;
    }
}
