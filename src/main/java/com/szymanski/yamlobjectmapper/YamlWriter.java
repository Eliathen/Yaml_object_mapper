package com.szymanski.yamlobjectmapper;

import com.szymanski.yamlobjectmapper.structure.*;

import java.util.ArrayList;
import java.util.Iterator;
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
        String line = prefix + yaml.getKey() + ": [";
        StringBuilder lineBuilder = new StringBuilder(line);
        for (YamlNode yamlNode : yaml.getValue()) {
            lineBuilder.append(((YamlScalar) yamlNode).getValue()).append(", ");
        }
        line = lineBuilder.toString();
        line = line.substring(0, line.length() - 2) + "]";
        result.add(line);
    }

    private void saveBlockSequence(YamlSequence yaml, String prefix) {
        String line = prefix + yaml.getKey() + ":";
        result.add(line);
        yaml.getValue().forEach(it -> {
            resolveNextNode(it, prefix + " - ");
        });
    }

    private void saveScalar(YamlScalar scalar, String prefix) {
        String newLine = prefix + scalar.getValue();
        result.add(newLine);
    }

    private void saveComplexObject(YamlComplexObject yaml, String prefix) {
        String newLine = prefix + yaml.getKey() + ": ";
        result.add(newLine);
        resolveNextNode(yaml, prefix);
    }

    private void resolveNextNode(YamlNode yaml, String prefix) {
        System.out.println("Yaml ==== " + yaml);
        if (yaml instanceof YamlCollection) {
            for (YamlNode node : ((YamlCollection) yaml).getValue()) {
                if (node instanceof YamlComplexObject) {
                    saveComplexObject((YamlComplexObject) node, prefix + " ");
                } else if(node instanceof YamlSequence) {
                    if (!((YamlSequence) node).getAnchors().isEmpty()) {
                        saveFlowSequence((YamlSequence) node, prefix + " ");
                    } else {
                        saveBlockSequence((YamlSequence) node, prefix + " ");
                    }
                } else {
                    resolveNextNode(node, prefix);
                }
            }
        } else if (yaml instanceof YamlDictionary) {
            saveDictionary((YamlDictionary) yaml, prefix + " ");
        } else {
            saveScalar((YamlScalar) yaml, prefix);
        }
    }

}
