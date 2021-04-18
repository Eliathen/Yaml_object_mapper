package com.szymanski.yamlobjectmapper.structure;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class YamlComplexObject extends YamlNode {

    List<String> anchors;
    @Getter
    @Setter
    private String key;
    @Getter
    @Setter
    private List<YamlNode> value;

    public YamlComplexObject() {
        anchors = new ArrayList<>();
        value = new ArrayList<>();
    }

    public YamlComplexObject(String key, List<YamlNode> complexObject) {
        this.key = key;
        this.value = complexObject;
    }

    public void addAnchor(String anchor) {
        anchors.add(anchor);
    }

    @Override
    public Object resolve(String key, YamlNode value, Class name) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YamlComplexObject that = (YamlComplexObject) o;
        return Objects.equals(anchors, that.anchors) && Objects.equals(key, that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(anchors, key, value);
    }
}
