package com.szymanski.yamlobjectmapper.structure;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class YamlSequence extends YamlCollection {

    public YamlSequence() {
        anchors = new ArrayList<>();
        value = new ArrayList<>();
    }

    public YamlSequence(String key, List<YamlNode> value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Object resolve(String key, YamlNode value, Class name) {
        return null;
    }

    @Override
    public void addNode(YamlNode node) {
        value.add(node);
    }

    @Override
    public String toString() {
        return "YamlSequence{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
