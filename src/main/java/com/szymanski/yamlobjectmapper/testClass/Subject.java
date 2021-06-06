package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@YamlClass
@Getter
@Setter
public class Subject {

    @YamlKey(name = "name")
    private String name;

    @YamlKey(name = "type")
    private String type;

    @YamlKey(name = "professors")
    @YamlOneToMany
    private List<Instructor> instructors;

    @Mapped(name = "student")
    private List<Student> students;

    public Subject(String name, String type, List<Instructor> instructors, List<Student> students) {
        this.name = name;
        this.type = type;
        this.instructors = instructors;
        this.students = students;
    }
}
