package com.szymanski.yamlobjectmapper;

import com.szymanski.yamlobjectmapper.annotations.YamlClass;
import com.szymanski.yamlobjectmapper.annotations.YamlKey;
import com.szymanski.yamlobjectmapper.converters.field.ConverterManager;
import com.szymanski.yamlobjectmapper.parser.YamlParser;
import com.szymanski.yamlobjectmapper.resolvers.YamlResolver;
import com.szymanski.yamlobjectmapper.structure.YamlComplexObject;
import com.szymanski.yamlobjectmapper.structure.YamlDictionary;
import com.szymanski.yamlobjectmapper.structure.YamlNode;
import com.szymanski.yamlobjectmapper.structure.YamlScalar;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

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

    public <T> void mapToYaml(T object) {
        Map<String, YamlNode> result = yamlResolver.resolveToYaml(object);
        result.keySet().forEach(key -> {
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
        });
    }

}
