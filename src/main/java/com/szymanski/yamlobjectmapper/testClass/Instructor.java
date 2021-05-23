package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.YamlClass;
import com.szymanski.yamlobjectmapper.annotations.YamlKey;
import lombok.Getter;
import lombok.Setter;

@YamlClass
@Getter
@Setter
public class Instructor {

    @YamlKey(name = "first_name")
    private String firstName;
    @YamlKey(name = "second_name")
    private String secondName;
    @YamlKey(name = "degree")
    private String degree;

    public Instructor(String firstName, String secondName, String degree) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.degree = degree;
    }
}
