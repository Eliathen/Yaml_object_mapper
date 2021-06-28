package com.szymanski.yamlobjectmapper.structure;

import java.util.List;

public abstract class YamlNode {

    private String anchor;

    protected List<String> tags;

    public void addNode(YamlNode node) {

    }
    public abstract Class<?> getType();

    public abstract String getKey();

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
