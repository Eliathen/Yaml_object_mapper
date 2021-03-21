package com.szymanski.yamlobjectmapper.structure;

public class YamlDictionary extends YamlObject{

    private String name;
    private String value;

    public YamlDictionary(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    Object resolve(String key, YamlObject value, Class name) {
        return null;
    }
}
