package com.szymanski.yamlobjectmapper.structure;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
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

    @Override
    public String toString() {
        return value;
    }
}
