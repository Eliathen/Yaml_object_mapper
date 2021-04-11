package com.szymanski.yamlobjectmapper.converters.structure;

public class YamlConverter {

    protected YamlDictionaryConverter yamlDictionaryConverter;
    protected YamlListConverter yamlListConverter;
    protected YamlComplexObjectConverter yamlComplexObjectConverter;
    protected YamlScalarConverter yamlScalarConverter;

    private static YamlConverter converter;

    private YamlConverter() {
        this.yamlScalarConverter = new YamlScalarConverter();
        this.yamlComplexObjectConverter = new YamlComplexObjectConverter();
        this.yamlListConverter = new YamlListConverter();
        this.yamlDictionaryConverter = new YamlDictionaryConverter();
    }

    static YamlConverter getInstance() {
        if (converter == null) {
            converter = new YamlConverter();
        }
        return converter;
    }

}
