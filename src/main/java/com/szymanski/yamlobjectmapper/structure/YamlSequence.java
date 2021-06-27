package com.szymanski.yamlobjectmapper.structure;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class YamlSequence extends YamlCollection {

    public YamlSequence() {
        anchors = new ArrayList<>();
        value = new ArrayList<>();
        tags = new ArrayList<>();
    }

    public YamlSequence(List<YamlNode> nodes) {
        anchors = new ArrayList<>();
        value = new ArrayList<>();
        value.addAll(nodes);
    }

    @Override
    public void addNode(YamlNode node) {
        value.add(node);
    }

    @Override
    public Class<?> getType() {
        return YamlSequence.class;
    }

    @Override
    public String getKey() {
        return super.getKey();
    }

    @Override
    public String toString() {
        return "YamlSequence{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }


}
