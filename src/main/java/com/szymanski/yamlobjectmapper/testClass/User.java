package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@YamlClass(name = "user")
@Getter
@Setter
public class User {
    @YamlId
    protected int id;
    @YamlOneToOne
    @YamlKey(name = "address")
    protected Address address;

    public User(int id, Address address) {
        this.id = id;
        this.address = address;
    }
}
