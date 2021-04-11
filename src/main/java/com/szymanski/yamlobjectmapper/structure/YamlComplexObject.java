package com.szymanski.yamlobjectmapper.structure;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class YamlComplexObject extends YamlNode {

    @Getter
    @Setter
    private String key;
    @Getter
    @Setter
    private List<YamlNode> value;

    public YamlComplexObject() {
        value = new ArrayList<>();
    }

    public YamlComplexObject(String key, List<YamlNode> complexObject) {
        this.key = key;
        this.value = complexObject;
    }

    @Override
    public Object resolve(String key, YamlNode value, Class name) {
        return null;
    }
}
