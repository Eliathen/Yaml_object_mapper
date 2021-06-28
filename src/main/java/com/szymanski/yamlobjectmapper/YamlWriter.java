package com.szymanski.yamlobjectmapper;

import com.szymanski.yamlobjectmapper.structure.*;

import java.util.ArrayList;
import java.util.List;

public class YamlWriter {

    private final List<String> result = new ArrayList<>();

    public YamlWriter() {
    }

    public List<String> getResult() {
        return result;
    }

    public void saveToFile(YamlNode yaml) {
        result.clear();
        resolveNextNode(yaml, "");
    }

    private void saveDictionary(YamlDictionary yaml, String s) {
        String newLine = s + yaml.getKey() + ": " + yaml.getValue();
        result.add(newLine);
    }

    private void saveFlowSequence(YamlSequence yaml, String prefix) {
        String line = prefix + yaml.getKey() + ": " + getTagsAsString(yaml) + " [";
        StringBuilder lineBuilder = new StringBuilder(line);
        if (yaml.getValue().isEmpty()) {
            lineBuilder.append("]");
            line = lineBuilder.toString();
        } else {
            for (YamlNode yamlNode : yaml.getValue()) {
                lineBuilder.append(yamlNode).append(", ");
            }
            line = lineBuilder.toString();
            line = line.substring(0, line.length() - 2) + "]";
        }
        result.add(line);
    }

    private String getTagsAsString(YamlSequence yaml) {
        StringBuilder tags = new StringBuilder();
        for (String tag : yaml.getTags()) {
            tags.append("!").append(tag).append(" ");
        }
        return tags.toString().trim();
    }

    private void saveScalar(YamlScalar scalar, String prefix) {
        String newLine = prefix + scalar.getValue();
        result.add(newLine);
    }

    private void saveComplexObject(YamlComplexObject yaml, String prefix) {
        String newLine = prefix + (yaml).getKey() + ": ";
        result.add(newLine);
        for (YamlNode yamlNode : yaml.getValue()) {
            resolveNextNode(yamlNode, prefix + " ");
        }
    }

    private void saveBlockSequence(YamlSequence yaml, String prefix) {
        if (yaml.getKey() != null) {
            String newLine = prefix + (yaml).getKey() + ": ";
            result.add(newLine);
        }
        for (YamlNode yamlNode : yaml.getValue()) {
            if (yamlNode instanceof YamlComplexObject) {
                for (int i = 0; i < ((YamlComplexObject) yamlNode).getValue().size(); i++) {
                    if (i == 0) {
                        resolveNextNode(((YamlComplexObject) yamlNode).getValue().get(0), prefix + "- ");
                    } else {
                        resolveNextNode(((YamlComplexObject) yamlNode).getValue().get(i), prefix.replace("-", " ") + "  ");
                    }
                }
            } else {
                resolveNextNode(yamlNode, prefix + "- ");
            }
        }
    }

    private void resolveNextNode(YamlNode yaml, String prefix) {
        if (yaml instanceof YamlCollection) {
            if (yaml instanceof YamlComplexObject) {
                saveComplexObject((YamlComplexObject) yaml, prefix.replace("-", " "));
            } else if (yaml instanceof YamlSequence) {
                if (yaml.getTags().isEmpty()) {
                    saveBlockSequence((YamlSequence) yaml, prefix);
                } else {
                    saveFlowSequence((YamlSequence) yaml, prefix);
                }
            } else {
                resolveNextNode(yaml, prefix);
            }
        } else if (yaml instanceof YamlDictionary) {
            saveDictionary((YamlDictionary) yaml, prefix);
        } else {
            saveScalar((YamlScalar) yaml, prefix);
        }
    }
}
