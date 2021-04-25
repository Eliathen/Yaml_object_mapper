package com.szymanski.yamlobjectmapper.structure;

import com.szymanski.yamlobjectmapper.exceptions.LineNotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class AllYamlLines {

    private List<YamlLine> allLines;

    public AllYamlLines(List<YamlLine> allLines) {
        this.allLines = allLines;
    }

    private YamlLine getLine(int number) {
        return allLines.stream()
                .filter(line -> line.getNumberOfLine().equals(number))
                .findFirst().orElseThrow(LineNotFoundException::new);
    }

}
