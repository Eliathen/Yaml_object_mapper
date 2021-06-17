package com.szymanski.yamlobjectmapper.structure.read;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class YamlLine {

    private String plainLine;

    private Integer numberOfLine;

    private String prefix;

    private List<String> tags;

    private String content;

    private String anchor;

    private String comment;

    public void extractContentFromWholeLine() {
        String content = plainLine
                .replace(prefix, "")
                .replace(anchor, "")
                .replace(comment, "");
        for (String tag : tags) {
            content = content.replace(tag, "");
        }
        this.content = content.trim();
    }

    public String getPrefix() {
        if(prefix.contains("-")) {
            return prefix.substring(0, prefix.indexOf("-") + 1);
        }
        return prefix;
    }
}
