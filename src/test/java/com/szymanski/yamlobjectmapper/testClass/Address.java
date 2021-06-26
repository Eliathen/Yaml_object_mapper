package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.YamlClass;
import com.szymanski.yamlobjectmapper.annotations.YamlKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@YamlClass
@EqualsAndHashCode
public class Address {

    @YamlKey
    private String city;
    @YamlKey
    private String street;
    @YamlKey
    private String buildingNumber;

    public Address() {
    }
}
