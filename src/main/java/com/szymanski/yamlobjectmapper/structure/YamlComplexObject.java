package com.szymanski.yamlobjectmapper.structure;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class YamlComplexObject extends YamlObject {
    @Getter
    @Setter
    private Map<String, YamlObject> complexObject;

    public YamlComplexObject(String key, Map<String, YamlObject> complexObject) {
        super(key);
        this.complexObject = complexObject;
    }

    @Override
    public Object resolve(String key, YamlObject value, Class name) {
        return null;
    }
}
