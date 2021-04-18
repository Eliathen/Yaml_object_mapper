package com.szymanski.yamlobjectmapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class YamlReader {

    List<String> convert(String filePath) {
        File file = new File(filePath);
        System.out.println(filePath);
        try (FileInputStream inputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader reader = new BufferedReader(inputStreamReader);) {
            return reader.lines().collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
