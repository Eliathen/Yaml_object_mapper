package com.szymanski.yamlobjectmapper.converters.structure;

import com.szymanski.yamlobjectmapper.structure.YamlScalar;

public class YamlScalarConverter {


    public YamlScalar toYaml(String value) {
        return new YamlScalar(value);
    }
}