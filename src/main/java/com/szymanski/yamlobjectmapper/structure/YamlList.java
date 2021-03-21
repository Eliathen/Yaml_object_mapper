package com.szymanski.yamlobjectmapper.structure;

import java.util.List;

public class YamlList extends YamlObject {

    private List<YamlObject> yamlObjectList;

    public YamlList(List<YamlObject> yamlObjectList) {
        this.yamlObjectList = yamlObjectList;
    }

    @Override
    Object resolve(String key, YamlObject value, Class name) {
        return null;
    }
}
