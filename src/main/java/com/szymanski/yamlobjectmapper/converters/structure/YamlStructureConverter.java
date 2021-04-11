package com.szymanski.yamlobjectmapper.converters.structure;

import com.szymanski.yamlobjectmapper.structure.YamlList;
import com.szymanski.yamlobjectmapper.structure.YamlNode;

import java.util.List;
import java.util.Map;


public class YamlStructureConverter {


    public YamlNode convertToYaml(Map<String, Object> map) {
        YamlConverter converter = YamlConverter.getInstance();
        if (map.isEmpty()) {
            //TODO throw exception
        }
        YamlList yaml = new YamlList();
        map.forEach((k, v) -> {
            if (v instanceof Map) {
                yaml.getYamlObjectList().add(converter.yamlComplexObjectConverter.toYaml(k, (Map<String, Object>) v));
            }
            if (v instanceof List) {
                yaml.getYamlObjectList().add(converter.yamlListConverter.toYaml(k, v));
            }
        });
        return yaml;
    }

}
