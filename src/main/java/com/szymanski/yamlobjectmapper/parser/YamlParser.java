package com.szymanski.yamlobjectmapper.parser;

import java.util.List;
import java.util.regex.Pattern;

public class YamlParser {

    public void parse(List<String> lines) {
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Empty File");
        }
        for (String line : lines) {
            if (isListElement(line)) {
                System.out.println(line + " <- list element");
            } else if (isYamlDictionary(line)) {
                System.out.println(line + " <- dictionary");
            } else if (isComment(line)) {
                System.out.println(line + " <- comment");
            } else {
                System.out.println(line + " <- complex object");
            }
        }
    }


    private boolean isListElement(String line) {
//        Pattern pattern = Pattern.compile(".*\\[.*\\].*");
        if (line.trim().startsWith("-")
//            || pattern.matcher(line).matches()
        ) {
            return true;
        }
        return false;
    }

    private boolean isYamlDictionary(String line) {
        Pattern pattern = Pattern.compile("^[^{}\\[\\]].*");
        if (line.trim().contains(":")
                && !pattern.matcher(line).matches()
                && line.split(":").length == 2
                && !line.split(":")[1].isEmpty()
                && !line.split(":")[1].contains("&")
        ) {
            return true;
        }
        return false;
    }

    private boolean isComment(String line) {
        if (line.trim().startsWith("#")) return true;
        return false;
    }
}
