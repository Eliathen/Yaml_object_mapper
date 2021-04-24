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

    public YamlLineType getYamlLineType(YamlLine line) {
        return getYamlLineType(line.getNumberOfLine());
    }

    public YamlLineType getYamlLineType(int number) {
        YamlLine currentLine = getLine(number);
        //TO DO
        return YamlLineType.EMPTY_LINE;
    }

    private YamlLine getLine(int number) {
        return allLines.stream()
                .filter(line -> line.getNumberOfLine().equals(number))
                .findFirst().orElseThrow(LineNotFoundException::new);
    }

}
