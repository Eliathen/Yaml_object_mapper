package com.szymanski.yamlobjectmapper;


public class Main {

    public static void main(String[] args) {
        YamlMapper mapper = new YamlMapper();
        mapper.mapToObject("/home/user/Projects/Yaml_object_mapper/src/main/resources/file.yaml", newClass.class);

    }

    class newClass{

    }
}
