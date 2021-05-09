package com.szymanski.yamlobjectmapper.structure;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class YamlCollection extends YamlNode {
    protected List<String> anchors;
    protected String key;
    protected List<YamlNode> value;

    public void addAnchor(String anchor) {
        anchors.add(anchor);
    }


    abstract Object resolve(String key, YamlNode value, Class<?> name);

}
