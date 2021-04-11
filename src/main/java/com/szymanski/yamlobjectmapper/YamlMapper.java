package com.szymanski.yamlobjectmapper;

import com.szymanski.yamlobjectmapper.converters.structure.YamlStructureConverter;
import com.szymanski.yamlobjectmapper.structure.YamlNode;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YamlMapper {

    private YamlStructureConverter converter;

    public YamlMapper() {
        converter = new YamlStructureConverter();
    }

    public <T> T mapToObject(String path, Class<T> type) {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader().getResourceAsStream(path);
        Map<String, Object> result = yaml.load(inputStream);

        YamlNode yamlNode = converter.convertToYaml(result);
        System.out.println(yamlNode);
        return (T) new Object();
    }


}
