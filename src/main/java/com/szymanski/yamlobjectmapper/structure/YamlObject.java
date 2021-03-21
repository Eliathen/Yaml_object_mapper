package com.szymanski.yamlobjectmapper.structure;

abstract class YamlObject {
    abstract Object resolve(String key, YamlObject value, Class name);
}
