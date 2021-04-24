package com.szymanski.yamlobjectmapper.structure;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class YamlSequence extends YamlNode {
    @Getter
    @Setter
    private String key;
    @Getter
    @Setter
    private List<YamlNode> yamlObjectList;

    public YamlSequence() {
        yamlObjectList = new ArrayList<>();
    }

    public YamlSequence(String key, List<YamlNode> yamlObjectList) {
        this.key = key;
        this.yamlObjectList = yamlObjectList;
    }

    @Override
    public Object resolve(String key, YamlNode value, Class name) {
        return null;
    }
}
