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
        return analyze(yamlLines, 0, yamlLines.getAllLines().size() - 1);
    }

    private YamlNode analyze(AllYamlLines yamlLines, int firstIndex, int lastIndex) {
        return null;
    }

    private int first = 0;

        private YamlDictionary extractYamlDictionary(AllYamlLines yamlLines, int startIndex) {
        var line = yamlLines.getAllLines().get(startIndex);
        String[] split = line.getContent().split(":");
        return new YamlDictionary(split[0], new YamlScalar(split[1]));
    }

    private boolean isYamlDictionary(AllYamlLines lines, int index) {
        YamlLine line = lines.getAllLines().get(index);
        String[] split = line.getContent().split(":");
        return split.length > 1 && !split[1].isBlank();
    }

    private boolean isYamlSequence(AllYamlLines yamlLines, int startIndex) {
        return false;
    }

    private boolean isYamlComplexObject(AllYamlLines lines, int index) {
        YamlLine line = lines.getAllLines().get(index);
        String[] split = line.getContent().split(":");
        return split.length == 1 || split[1].isBlank();
    }

    private Optional<String> getKeyFromLine(YamlLine line) {
        return Arrays.stream(line.getContent().split(":")).findFirst();
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

    public boolean shouldSkipLine(YamlLine line) {
        return line.getPlainLine().startsWith("#") || line.getContent().isEmpty();
    }

    private boolean isLinesHaveThisSameLevel(YamlLine first, YamlLine second) {
        return first.getPrefix().length() == second.getPrefix().length();
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
