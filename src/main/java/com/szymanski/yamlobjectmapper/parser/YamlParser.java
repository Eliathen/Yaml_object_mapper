package com.szymanski.yamlobjectmapper.parser;

import com.szymanski.yamlobjectmapper.structure.*;
import com.szymanski.yamlobjectmapper.structure.read.AllYamlLines;
import com.szymanski.yamlobjectmapper.structure.read.LineLevel;
import com.szymanski.yamlobjectmapper.structure.read.YamlLine;

import java.util.*;
import java.util.stream.Collectors;

public class YamlParser {

    private final YamlReader yamlReader;
    int numberOfLines = 0;
    private int position = 0;

    public YamlParser() {
        this.yamlReader = new YamlReader();
    }

    public YamlNode parse(String path) {
        var lines = yamlReader.convert(path);
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Empty File");
        }
        AllYamlLines yamlLines = parseStringLinesToYamlLines(lines);
        numberOfLines = yamlLines.getAllLines().size();
        position = 0;
        return analyze(yamlLines);
    }

    private YamlNode analyze(AllYamlLines yamlLines) {
        var newNode = new YamlComplexObject();
        while (position < numberOfLines) {
            if (!yamlLines.getAllLines().get(position).getTags().isEmpty()) {
                newNode.setTags(yamlLines.getAllLines().get(position).getTags());
            }
            while (shouldSkipLine(yamlLines.getAllLines().get(position))) {
                position++;
            }
            if (isYamlBlockSequence(yamlLines)) {
                var sequence = new YamlSequence();
                if (!yamlLines.getAllLines().get(position).getTags().isEmpty()) {
                    sequence.setTags(yamlLines.getAllLines().get(position).getTags());
                }
                getKeyFromLine(yamlLines.getAllLines().get(position)).ifPresent(sequence::setKey);
                var rootLine = yamlLines.getAllLines().get(position);
                position++;
                while (position < numberOfLines) {
                    if (rootLine.getPrefix().length() < yamlLines.getAllLines().get(position).getPrefix().length()) {
                        sequence.addNode(analyze(yamlLines));
                    } else {
                        break;
                    }
                }
                return sequence;
            } else if (isYamlFlowSequence(yamlLines)) {
                var sequence = new YamlSequence();
                var line = yamlLines.getAllLines().get(position);
                if (!line.getTags().isEmpty()) {
                    sequence.setTags(yamlLines.getAllLines().get(position).getTags().stream().map(tag -> tag.replace("!", "")).collect(Collectors.toList()));
                }
                getKeyFromLine(line).ifPresent(sequence::setKey);
                var content = line.getContent().split(":")[1].trim();
                content = content.replace("[", "");
                content = content.replace("]", "");
                var split = content.split(",");
                for (String s : split) {
                    sequence.addNode(new YamlScalar(s.trim()));
                }
                position++;
                return sequence;
            } else if (isYamlComplexObject(yamlLines)) {
                getKeyFromLine(yamlLines.getAllLines().get(position)).ifPresent(newNode::setKey);
                position++;
                while (hasNextLine(yamlLines, position)) {
                    if (getLinesConnection(yamlLines.getAllLines().get(position),
                            yamlLines.getAllLines().get(position + 1)) != LineLevel.PARENT) {
                        newNode.addNode(analyze(yamlLines));
                    } else {
                        break;
                    }
                }
                if (position != numberOfLines) {
                    newNode.addNode(analyze(yamlLines));
                }
                return newNode;

            } else if (isYamlDictionary(yamlLines)) {
                var dictionary = extractYamlDictionary(yamlLines);
                position++;
                return dictionary;
            } else {
                YamlLine currentLine = yamlLines.getAllLines().get(position);
                if (currentLine.getPrefix().contains("-") && !currentLine.getContent().contains(":")) {
                    position++;
                    return new YamlScalar(currentLine.getContent().trim());
                }
                YamlComplexObject node = new YamlComplexObject();
                node.addNode(extractYamlDictionary(yamlLines));
                position++;
                while (position < numberOfLines) {
                    if (currentLine.getPrefix().length() < yamlLines.getAllLines().get(position).getPrefix().length()) {
                        node.addNode(analyze(yamlLines));
                    } else {
                        break;
                    }
                }
                return node;
            }
        }
        return newNode;
    }

    private YamlDictionary extractYamlDictionary(AllYamlLines yamlLines) {
        var line = yamlLines.getAllLines().get(position);
        String[] split = line.getContent().split(":");
        return new YamlDictionary(split[0].trim(), new YamlScalar(split[1].trim()));
    }

    private boolean isYamlDictionary(AllYamlLines lines) {
        YamlLine line = lines.getAllLines().get(position);
        if (line.getPrefix().contains("-")) return false;
        String[] split = line.getContent().split(":");
        return split.length > 1 && !split[1].isBlank();
    }

    private boolean isYamlBlockSequence(AllYamlLines lines) {
        YamlLine line = lines.getAllLines().get(position);
        String[] split = line.getContent().split(":");
        return (split.length == 1 || split[1].isBlank()) && !line.getPrefix().contains("-") && peekNextLine(lines, position).getPrefix().contains("-");
    }

    private boolean isYamlFlowSequence(AllYamlLines lines) {
        YamlLine line = lines.getAllLines().get(position);
        String[] split = line.getContent().split(":");
        return split.length > 1 && split[1].contains("[");
    }

    private boolean isYamlComplexObject(AllYamlLines lines) {
        YamlLine line = lines.getAllLines().get(position);
        if (!line.getContent().contains(":")) return false;
        String[] split = line.getContent().split(":");
        return split.length == 1 || split[1].isBlank();
    }

    private Optional<String> getKeyFromLine(YamlLine line) {
        return Arrays.stream(line.getContent().split(":")).findFirst();
    }

    private AllYamlLines parseStringLinesToYamlLines(List<String> lines) {
        List<YamlLine> yamlLines = new ArrayList<>();
        int i = 1;
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
        return line.getPlainLine().trim().startsWith("#") || line.getContent().isEmpty();
    }

    private LineLevel getLinesConnection(YamlLine first, YamlLine second) {
        if (first.getPrefix().length() == second.getPrefix().length()) return LineLevel.SIBLING;
        else if (first.getPrefix().length() < second.getPrefix().length()) return LineLevel.CHILD;
        else return LineLevel.PARENT;
    }

    private YamlLine peekNextLine(AllYamlLines lines, int lineNumber) {
        return lines.getAllLines().get(lineNumber + 1);
    }

    private boolean hasNextLine(AllYamlLines lines, int position) {
        return position < lines.getAllLines().size() - 1;
    }

    private Set<String> getYamlTags(String line) {
        return Arrays.stream(line.split(" "))
                .filter(el -> el.contains("!") || el.contains("!!"))
                .collect(Collectors.toSet());
    }

    private String getYamlComment(String line) {
        if (line.contains("#")) {
            int index = line.indexOf("#");
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
