package com.szymanski.yamlobjectmapper.structure;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class YamlDictionary extends YamlNode {
    @Getter
    @Setter
    private String key;
    @Getter
    @Setter
    private YamlScalar value;

    public YamlDictionary(String key, YamlScalar value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "YamlDictionary{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public Class<?> getType() {
        return YamlDictionary.class;
    }
}
