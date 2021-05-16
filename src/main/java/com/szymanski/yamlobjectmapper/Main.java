package com.szymanski.yamlobjectmapper;


import com.szymanski.yamlobjectmapper.testClass.Address;
import com.szymanski.yamlobjectmapper.testClass.Student;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        YamlMapper mapper = new YamlMapper();
        Student student = generateTestObject();
        mapper.mapToYaml(student);
//        mapper.mapToObject("/home/user/Projects/Yaml_object_mapper/src/main/resources/file.yaml", Student.class);

    }

    private static Student generateTestObject() {
        List<Integer> marks = new ArrayList<>();
        marks.add(5);
        marks.add(4);
        marks.add(3);
        Address address = new Address("Street", "ABC");
        LocalDate date = LocalDate.now();
        System.out.println(date);
        List<String> randomString = new ArrayList<>();
        randomString.add("Random text 1");
        randomString.add("Random text 2");
        return new Student(1, 24, "John", "Dee", marks, address, date, randomString);
    }
}
