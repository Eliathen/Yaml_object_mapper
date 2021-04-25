package com.szymanski.yamlobjectmapper.structure;

import lombok.Data;

import java.util.List;

@Data
public abstract class YamlCollection extends YamlNode {
    protected List<String> anchors;
    protected String key;
    protected List<YamlNode> value;

    public void addAnchor(String anchor) {
        anchors.add(anchor);
    }

    @Override
    public Object resolve(String key, YamlNode value, Class name) {
        return null;
    }

}
