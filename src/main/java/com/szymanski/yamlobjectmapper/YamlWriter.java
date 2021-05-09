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
        if (yaml instanceof YamlDictionary) {
            saveDictionary((YamlDictionary) yaml, "");
        } else if (yaml instanceof YamlComplexObject) {
            saveComplexObject((YamlComplexObject) yaml, "");
        } else if (yaml instanceof YamlSequence) {
            saveSequence((YamlSequence) yaml, "");

        } else if (yaml instanceof YamlScalar) {
            saveScalar((YamlScalar) yaml, "");

        }
    }

    private void saveDictionary(YamlDictionary yaml, String s) {
        String newLine = s + yaml.getKey() + ": " + yaml.getValue();
        result.add(newLine);
    }

    private void saveSequence(YamlSequence yaml, String prefix) {
        final Iterator<YamlNode> iterator = yaml.getValue().iterator();
        String newLine = prefix + yaml.getKey() + ": ";
    }

    private void saveScalar(YamlScalar scalar, String prefix) {
        String newLine = scalar.getValue();
        result.add(newLine);
    }

    private void saveComplexObject(YamlComplexObject yaml, String prefix) {
        final Iterator<YamlNode> iterator = yaml.getValue().iterator();
        String newLine = prefix + yaml.getKey() + ": ";
        result.add(newLine);
        while (iterator.hasNext()) {
            YamlNode node = iterator.next();
            if (node instanceof YamlDictionary) {
                saveDictionary((YamlDictionary) node, prefix + " ");
            } else if (node instanceof YamlComplexObject) {
                saveComplexObject((YamlComplexObject) node, prefix + " ");
            } else if (node instanceof YamlSequence) {
                saveSequence((YamlSequence) node, prefix);
            } else if (node instanceof YamlScalar) {
                saveScalar((YamlScalar) node, "");
            }
        }
    }

}
