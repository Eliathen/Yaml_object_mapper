package com.szymanski.yamlobjectmapper.structure;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class YamlComplexObject extends YamlCollection {

    public YamlComplexObject() {
        anchors = new ArrayList<>();
        value = new ArrayList<>();
    }

    public YamlComplexObject(String key, List<YamlNode> complexObject) {
        this.key = key;
        this.value = complexObject;
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

    @Override
    public void addNode(YamlNode node) {
        value.add(node);
    }

    @Override
    public String toString() {
        return "YamlComplexObject{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    Object resolve(String key, YamlNode value, Class<?> name) {
        return null;
    }
}
