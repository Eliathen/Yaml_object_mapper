package com.szymanski.yamlobjectmapper;


public class Main {

    public static void main(String[] args) {
        YamlMapper mapper = new YamlMapper();
        mapper.mapToObject("file.yaml", newClass.class);

    }

    class newClass{

    }
}
