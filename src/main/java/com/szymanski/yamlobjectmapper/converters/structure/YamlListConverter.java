package com.szymanski.yamlobjectmapper.converters.structure;

import com.szymanski.yamlobjectmapper.structure.YamlList;

import java.util.List;
import java.util.Map;

public class YamlListConverter {


    public YamlList toYaml(String key, Object value) {
        YamlConverter converter = YamlConverter.getInstance();
        YamlList list = new YamlList();
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