package com.szymanski.yamlobjectmapper;


import com.szymanski.yamlobjectmapper.testExample.Student;

public class Main {

    public static void main(String[] args) {
        YamlMapper mapper = new YamlMapper();
        Student student = new Student(1, 24, "John", "Dee");
        mapper.mapToYaml(student);
//        mapper.mapToObject("/home/user/Projects/Yaml_object_mapper/src/main/resources/file.yaml", Student.class);

    }
}
