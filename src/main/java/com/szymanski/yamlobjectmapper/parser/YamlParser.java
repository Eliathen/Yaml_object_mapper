package com.szymanski.yamlobjectmapper.parser;

import com.szymanski.yamlobjectmapper.structure.AllYamlLines;
import com.szymanski.yamlobjectmapper.structure.YamlLine;
import com.szymanski.yamlobjectmapper.structure.YamlNode;
import com.szymanski.yamlobjectmapper.structure.YamlScalar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class YamlParser {

    private int iterator = 0;

    public void parse(List<String> lines) {
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Empty File");
        }
        AllYamlLines yamlLines = parseStringLinesToYamlLines(lines);
        YamlNode yamlNode = analyze(yamlLines);
    }

    private AllYamlLines parseStringLinesToYamlLines(List<String> lines) {
        List<YamlLine> yamlLines = new ArrayList<>();
        int i = 0;
        for (String line : lines) {
            if (lines.isEmpty()) continue;
            YamlLine yamlLine = YamlLine.builder()
                    .plainLine(line)
                    .numberOfLine(i)
                    .prefix(getYamlPrefix(line))
                    .anchor(getYamlAnchors(line))
                    .tags(new ArrayList<>(getYamlTags(line)))
                    .comment(getYamlComment(line))
                    .build();
            yamlLine.extractContentFromWholeLine();
            yamlLines.add(yamlLine);
            i++;
        }
        return new AllYamlLines(yamlLines);
    }

    private YamlNode analyze(AllYamlLines lines) {
        for (; iterator < lines.getAllLines().size(); iterator++) {

        }
        return new YamlScalar();
    }

    private boolean isYamlDictionary(AllYamlLines lines) {
        YamlLine line = lines.getAllLines().get(iterator);
        if (line.getContent().contains(":") && line.getContent().split(":").length > 1) {//TO DO
            return true;
        }
        return false;
    }

    private boolean isYamlScalar(AllYamlLines lines) {
        YamlLine line = lines.getAllLines().get(iterator);
        //TO DO
        return false;
    }

    private Set<String> getYamlTags(String line) {
        return Arrays.stream(line.split(" "))
                .filter(el -> el.contains("!") || el.contains("!!"))
                .collect(Collectors.toSet());
    }

    private String getYamlComment(String line) {
        if (line.contains("#")) {
            int index = line.trim().indexOf("#");
            return line.substring(index);
        }
        return "";
    }

    private String getYamlAnchors(String line) {
        Set<String> anchors = Arrays.stream(line.split(" ")).filter(el -> el.startsWith("&") || el.startsWith("*")).collect(Collectors.toSet());
        return anchors.stream().findFirst().orElse("");
    }

    private String getYamlPrefix(String line) {
        StringBuilder prefix = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c == ' ') {
                prefix.append(c);
                continue;
            } else if (c == '-') {
                prefix.append(c);
                break;
            }
            break;
        }
        return prefix.toString();
    }
}
