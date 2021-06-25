package com.szymanski.yamlobjectmapper;

import com.szymanski.yamlobjectmapper.parser.YamlParser;
import com.szymanski.yamlobjectmapper.resolvers.YamlResolverToFile;
import com.szymanski.yamlobjectmapper.resolvers.YamlResolverToObject;
import com.szymanski.yamlobjectmapper.structure.YamlNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.NotDirectoryException;
import java.util.*;

public class YamlMapper {

    private final YamlParser yamlParser;
    private final YamlWriter writer;
    private final YamlResolverToFile yamlResolverToFile;
    private final YamlResolverToObject yamlResolverToObject;

    public YamlMapper() {
        yamlParser = new YamlParser();
        writer = new YamlWriter();
        yamlResolverToFile = new YamlResolverToFile();
        yamlResolverToObject = new YamlResolverToObject();
    }

    public <T> T mapToObject(String path, Class<T> type) throws NotDirectoryException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        File file = new File(path);
        if (!file.isDirectory()) throw new NotDirectoryException("Path is not to directory");
        List<YamlNode> nodes = new ArrayList<>();
        Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .filter(it -> it.isFile() && (it.getName().contains(".yml") || it.getName().contains(".yaml")))
                .forEach(it -> nodes.add(yamlParser.parse(it.getAbsolutePath())));
        return (T) yamlResolverToObject.resolve(nodes, type);
    }

    public <T> void mapToYamlFile(T object) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        var result = yamlResolverToFile.resolve(object);
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
