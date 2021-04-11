package com.szymanski.yamlobjectmapper.converters.structure;

import com.szymanski.yamlobjectmapper.structure.YamlDictionary;

public class YamlDictionaryConverter {


    public YamlDictionary toYaml(String key, Object value) {
        YamlConverter converter = YamlConverter.getInstance();
        YamlDictionary dictionary = new YamlDictionary();
        dictionary.setKey(key);
        dictionary.setValue(converter.yamlScalarConverter.toYaml(value.toString()));
        return dictionary;
    }
}