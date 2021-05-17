package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.YamlClass;
import com.szymanski.yamlobjectmapper.annotations.YamlKey;
import lombok.AllArgsConstructor;
import lombok.Data;

@YamlClass
@Data
@AllArgsConstructor
public class Address {

    @YamlKey(name = "city")
    private String city;
    @YamlKey(name = "street")
    private String street;
    @YamlKey(name = "number")
    private String buildingNumber;

}
