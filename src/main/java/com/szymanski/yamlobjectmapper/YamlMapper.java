package com.szymanski.yamlobjectmapper;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YamlMapper {

    public YamlMapper() {
    }

    public void getFile() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader().getResourceAsStream("file.yaml");
        Map<String, Object> result = yaml.load(inputStream);
    }
}
