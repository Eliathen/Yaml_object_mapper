package com.szymanski.yamlobjectmapper;


import com.szymanski.yamlobjectmapper.testClass.Address;
import com.szymanski.yamlobjectmapper.testClass.Instructor;
import com.szymanski.yamlobjectmapper.testClass.Student;
import com.szymanski.yamlobjectmapper.testClass.Subject;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        YamlMapper mapper = new YamlMapper();
        Student student = generateTestObject();
        try {
            mapper.mapToYaml(student);
        } catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
//        mapper.mapToObject("/home/user/Projects/Yaml_object_mapper/src/main/resources/file.yaml", Student.class);
    }

    private static Student generateTestObject() {
        List<Integer> marks = new ArrayList<>();
        marks.add(5);
        marks.add(4);
        marks.add(3);
        Address address = new Address("Kielce", "Street", "ABC");

        LocalDate date = LocalDate.now();
        Instructor instructor1 = new Instructor("name1", "surname1", "degree1");
        Instructor instructor2 = new Instructor("name2", "surname2", "degree2");


        List<String> randomString = Arrays.asList("Random text 1", "Random text 2");

        Student student = new Student(1, address, 25, "John", "Dee", marks, date, randomString, true);

        Subject subject1 = new Subject("Subject 1", "Type 1", Arrays.asList(instructor1, instructor2), Collections.singletonList(student));
        Subject subject2 = new Subject("Subject 2", "Type 2", Arrays.asList(instructor1, instructor2), Collections.singletonList(student));
        List<Subject> subjects = Arrays.asList(subject1, subject2);
        student.setSubjects(subjects);
        return student;
    }
}
