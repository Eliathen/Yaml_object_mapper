package com.szymanski.yamlobjectmapper;

import com.szymanski.yamlobjectmapper.converters.structure.YamlStructureConverter;
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
        result.forEach((k, v) -> {
            System.out.println(v);
        });
//        YamlObject yamlObject = converter.convertToYaml(result);

        return (T) new Object();
    }


}
