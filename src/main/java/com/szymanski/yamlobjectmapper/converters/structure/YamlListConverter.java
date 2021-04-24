package com.szymanski.yamlobjectmapper.converters.structure;

import com.szymanski.yamlobjectmapper.structure.YamlSequence;

import java.util.List;
import java.util.Map;

public class YamlListConverter {


    public YamlSequence toYaml(String key, Object value) {
        YamlConverter converter = YamlConverter.getInstance();
        YamlSequence list = new YamlSequence();
        list.setKey(key);
        if (value instanceof Map) {
            ((Map<?, ?>) value).forEach((k, v) ->
                    list.getYamlObjectList().add(converter.yamlComplexObjectConverter.toYaml((String) k, (Map<String, Object>) v)));
        }
        if (value instanceof List) {
            ((List<?>) value).forEach(el ->
                    list.getYamlObjectList().add(converter.yamlScalarConverter.toYaml((String) el))
            );
        }
        return list;
    }

}