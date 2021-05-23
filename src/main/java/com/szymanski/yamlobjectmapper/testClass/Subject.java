package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.YamlClass;
import com.szymanski.yamlobjectmapper.annotations.YamlKey;
import com.szymanski.yamlobjectmapper.annotations.YamlOneToMany;
import lombok.Data;
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

    public Subject(String name, String type, List<Instructor> instructors) {
        this.name = name;
        this.type = type;
        this.instructors = instructors;
    }
}
