package com.szymanski.yamlobjectmapper;

import com.szymanski.yamlobjectmapper.converters.structure.YamlStructureConverter;
import com.szymanski.yamlobjectmapper.parser.YamlParser;

import java.util.List;

public class YamlMapper {

    private YamlStructureConverter converter;
    private YamlReader yamlReader;
    private YamlParser yamlParser;

    public YamlMapper() {
        yamlReader = new YamlReader();
        converter = new YamlStructureConverter();
        yamlParser = new YamlParser();
    }

    public <T> T mapToObject(String path, Class<T> type) {
        List<String> lines = yamlReader.convert(path);
        yamlParser.parse(lines);
        return (T) new Object();
    }


}
