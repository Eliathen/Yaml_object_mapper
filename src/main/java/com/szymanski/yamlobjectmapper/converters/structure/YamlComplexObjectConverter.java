package com.szymanski.yamlobjectmapper.converters.structure;

import com.szymanski.yamlobjectmapper.structure.YamlComplexObject;

import java.util.List;
import java.util.Map;

public class YamlComplexObjectConverter {

    public YamlComplexObject toYaml(String key, Map<String, Object> value) {
        YamlConverter converter = YamlConverter.getInstance();
        YamlComplexObject yaml = new YamlComplexObject();
        yaml.setKey(key);
        value.forEach((k, v) -> {
            if (v instanceof List) {
                //TODO implements
            } else if (v instanceof Map) {
                ((Map<?, ?>) v).forEach((k1, v1) -> {
                    if (v1 instanceof List) {
                        yaml.getValue().add(converter.yamlListConverter.toYaml((String) k1, v1));
                    } else if (v1 instanceof Map) {
                        yaml.getValue().add(converter.yamlComplexObjectConverter.toYaml((String) k1, (Map<String, Object>) v1));
                    }
                });
            } else {
                yaml.getValue().add(converter.yamlDictionaryConverter.toYaml(k, v));
            }
        });
        return yaml;
    }
}
