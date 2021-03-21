package com.szymanski.yamlobjectmapper.structure;

import java.util.HashMap;

public class YamlComplexObject extends YamlObject {
    private HashMap<String, YamlObject> complexObject;

    public YamlComplexObject(HashMap<String, YamlObject> complexObject) {
        this.complexObject = complexObject;
    }

    @Override
    Object resolve(String key, YamlObject value, Class name) {
        return null;
    }
}
