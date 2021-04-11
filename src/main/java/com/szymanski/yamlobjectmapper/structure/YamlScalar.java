package com.szymanski.yamlobjectmapper.structure;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class YamlScalar extends YamlNode {

    @Getter
    @Setter
    private String value;

    public YamlScalar(String value) {
        this.value = value;
    }

    @Override
    Object resolve(String key, YamlNode value, Class name) {
        return null;
    }
}
