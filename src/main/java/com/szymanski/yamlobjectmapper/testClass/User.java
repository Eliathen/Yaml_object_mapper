package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.YamlClass;
import com.szymanski.yamlobjectmapper.annotations.YamlId;
import com.szymanski.yamlobjectmapper.annotations.YamlKey;
import com.szymanski.yamlobjectmapper.annotations.YamlOneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@YamlClass(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

    @YamlId
    protected int id;

    @YamlOneToOne
    @YamlKey(name = "address")
    protected Address address;


}
