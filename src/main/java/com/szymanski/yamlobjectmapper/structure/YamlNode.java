package com.szymanski.yamlobjectmapper.structure;

import java.util.List;

public abstract class YamlNode {

    private String anchor;

    private List<String> tags;

    abstract Object resolve(String key, YamlNode value, Class<?> name);

    public void addNode(YamlNode node) {

    }
    public abstract Class<?> getType();

    public abstract String getKey();

    public String getAnchor() {
        return anchor;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
