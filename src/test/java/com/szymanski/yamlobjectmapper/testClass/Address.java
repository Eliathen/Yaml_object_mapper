package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.YamlClass;
import com.szymanski.yamlobjectmapper.annotations.YamlKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@YamlClass(name = "address")
@EqualsAndHashCode
public class Address {

    @YamlKey(name = "city")
    private String city;
    @YamlKey(name = "street")
    private String street;
    @YamlKey(name = "buildingNumber")
    private String buildingNumber;

    public Address() {
    }
}
