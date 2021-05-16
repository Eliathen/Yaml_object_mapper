package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.YamlClass;
import com.szymanski.yamlobjectmapper.annotations.YamlId;

@YamlClass(name = "user")
public class User {

    @YamlId
    protected int id;

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
