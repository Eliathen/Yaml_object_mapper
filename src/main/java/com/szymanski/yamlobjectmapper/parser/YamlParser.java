package com.szymanski.yamlobjectmapper.parser;

import com.szymanski.yamlobjectmapper.structure.*;
import com.szymanski.yamlobjectmapper.structure.read.AllYamlLines;
import com.szymanski.yamlobjectmapper.structure.read.YamlLine;

import java.util.*;
import java.util.stream.Collectors;

public class YamlParser {

    public YamlNode parse(List<String> lines) {
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Empty File");
        }
        AllYamlLines yamlLines = parseStringLinesToYamlLines(lines);
        return analyze(yamlLines);
    }

    private int first = 0;

    private YamlNode analyze(AllYamlLines yamlLines) {
        YamlNode node;
        while (shouldSkipLine(yamlLines.getAllLines().get(first))) {
            first++;
        }
        YamlLine firstLine = yamlLines.getAllLines().get(first);
        if (isYamlDictionary(yamlLines, first)) {
            node = extractYamlDictionary(firstLine);
        } else if (isYamlScalar(yamlLines, first)) {
            node = new YamlScalar(yamlLines.getAllLines().get(first).getContent().trim());
        } else {
            int second = first + 1;
            while ((second < (yamlLines.getAllLines().size() - 1)) &&
                    !isLinesHaveThisSameLevel(firstLine, peekNextLine(yamlLines, second))) {
                second++;
            }
            YamlCollection yamlCollection;
            if (isYamlBlockMapping(yamlLines, first)) {
                yamlCollection = new YamlComplexObject();
            } else {
                yamlCollection = new YamlSequence();
            }
            Optional<String> key = getKeyFromLine(firstLine);
            yamlCollection.getAnchors().add(firstLine.getAnchor());
            key.ifPresent(yamlCollection::setKey);
            do {
                first++;
                yamlCollection.addNode(analyze(yamlLines));
            } while (first != second);
            node = yamlCollection;
        }
        return node;
    }

    private Optional<String> getKeyFromLine(YamlLine firstLine) {
        return Arrays.stream(firstLine.getContent().split(":")).findFirst();
    }

    private AllYamlLines parseStringLinesToYamlLines(List<String> lines) {
        List<YamlLine> yamlLines = new ArrayList<>();
        int i = 0;
        for (String line : lines) {
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

    private YamlDictionary extractYamlDictionary(YamlLine line) {
        YamlDictionary dictionary = new YamlDictionary();
        String[] values = line.getContent().split(":");
        dictionary.setKey(values[0]);
        dictionary.setValue(new YamlScalar(values[1]));
        return dictionary;
    }

    public boolean shouldSkipLine(YamlLine line) {
        return line.getPlainLine().startsWith("#") || line.getContent().isEmpty();
    }

    private boolean isYamlDictionary(AllYamlLines lines, int lineNumber) {
        YamlLine line = lines.getAllLines().get(lineNumber);
        return line.getContent().contains(":") && line.getContent().split(":").length > 1;
    }

    private boolean isYamlScalar(AllYamlLines lines, int lineNumber) {
        YamlLine line = lines.getAllLines().get(lineNumber);
        return !line.getContent().contains(":");
    }

    private boolean isYamlFlowSequence(AllYamlLines lines, int lineNumber) {
        YamlLine line = lines.getAllLines().get(lineNumber);
        return line.getContent().contains("[");
    }

    private boolean isYamlBlockSequence(AllYamlLines lines, int lineNumber) {
        System.out.println(lines.getAllLines().get(lineNumber));
        if (!isYamlDictionary(lines, lineNumber)) {
            return peekNextLine(lines, lineNumber).getPrefix().contains("-");
        }
        return false;
    }

    private boolean isLinesHaveThisSameLevel(YamlLine first, YamlLine second) {
        return first.getPrefix().length() == second.getPrefix().length();
    }

    private boolean isYamlBlockMapping(AllYamlLines lines, int lineNumber) {
        if (!isYamlDictionary(lines, lineNumber)) {
            return isYamlDictionary(lines, lineNumber + 1);
        }
        return false;
    }

    private YamlLine peekNextLine(AllYamlLines lines, int lineNumber) {
        return lines.getAllLines().get(lineNumber + 1);
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
            } else if (c == '-') {
                prefix.append(c);
            } else {
                break;
            }
        }
        return prefix.toString();
    }
}
