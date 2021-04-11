package com.szymanski.yamlobjectmapper.structure;

public abstract class YamlNode {

    abstract Object resolve(String key, YamlNode value, Class name);
}
