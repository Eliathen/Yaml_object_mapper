package com.szymanski.yamlobjectmapper;

import com.szymanski.yamlobjectmapper.testClass.Client;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class YamlMapperTest {

    private YamlMapper yamlMapper = new YamlMapper();

    @SneakyThrows
    @Test
    public void objectsBeforeMappingAndAfterShouldBeEquals(){
        var testClass = TestClassGenerator.getTestClass();
        yamlMapper.mapToYamlFile(testClass);
        var result = yamlMapper.mapToObject("D:\\Projects\\Yaml_object_mapper", Client.class);
        Assertions.assertEquals(testClass.getOrders().size(), 2);
        Assertions.assertEquals(testClass, result);
    }

}