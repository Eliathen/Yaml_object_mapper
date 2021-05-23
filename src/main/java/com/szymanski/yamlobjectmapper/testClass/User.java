package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@YamlClass(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

    @YamlId
    protected int id;

    @YamlOneToMany
    @YamlKey(name = "address")
    protected List<Address> address;

}
