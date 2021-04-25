package com.szymanski.yamlobjectmapper;

import com.szymanski.yamlobjectmapper.parser.YamlParser;
import com.szymanski.yamlobjectmapper.structure.YamlNode;

import java.util.List;

public class YamlMapper {

    private YamlReader yamlReader;
    private YamlParser yamlParser;

    public YamlMapper() {
        yamlReader = new YamlReader();
        yamlParser = new YamlParser();
    }

    public <T> T mapToObject(String path, Class<T> type) {
        List<String> lines = yamlReader.convert(path);
        YamlNode node = yamlParser.parse(lines);
        System.out.println(node);
        return (T) new Object();
    }


}
