package com.szymanski.yamlobjectmapper.structure;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
public class YamlList extends YamlObject {
    @Getter
    @Setter
    private List<YamlObject> yamlObjectList;

    public YamlList(String key, List<YamlObject> yamlObjectList) {
        super(key);
        this.yamlObjectList = yamlObjectList;
    }

    @Override
    public Object resolve(String key, YamlObject value, Class name) {
        return null;
    }
}
