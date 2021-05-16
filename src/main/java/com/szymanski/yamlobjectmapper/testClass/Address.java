package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.YamlClass;
import com.szymanski.yamlobjectmapper.annotations.YamlKey;
import lombok.Getter;

@YamlClass
@Getter
public class Address {

    @YamlKey(name = "street")
    private String street;
    @YamlKey(name = "number")
    private String number;

    public Address(String street, String number) {
        this.street = street;
        this.number = number;
    }

}
