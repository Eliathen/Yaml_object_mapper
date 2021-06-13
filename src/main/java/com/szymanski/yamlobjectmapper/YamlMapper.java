package com.szymanski.yamlobjectmapper;

import com.szymanski.yamlobjectmapper.parser.YamlParser;
import com.szymanski.yamlobjectmapper.resolvers.YamlResolver;
import com.szymanski.yamlobjectmapper.structure.YamlNode;
import com.szymanski.yamlobjectmapper.structure.YamlSequence;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class YamlMapper {

    private YamlReader yamlReader;
    private YamlParser yamlParser;
    private YamlWriter writer;
    private YamlResolver yamlResolver;

    public YamlMapper() {
        yamlReader = new YamlReader();
        yamlParser = new YamlParser();
        writer = new YamlWriter();
        yamlResolver = new YamlResolver();
    }

    public <T> T mapToObject(String path, Class<T> type) {
        List<String> lines = yamlReader.convert(path);
        YamlNode node = yamlParser.parse(lines);
        System.out.println(node);
        return (T) new Object();
    }

    public <T> void mapToYaml(T object) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        var result = yamlResolver.resolve(object);
        for (String key : result.keySet()) {
            writer.saveToFile(result.get(key));
            List<String> lines = writer.getResult();
                try {
                    FileWriter writer = new FileWriter(key + ".yaml");
                    for (String s : lines) {
                        writer.write(s + "\n");
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

}
