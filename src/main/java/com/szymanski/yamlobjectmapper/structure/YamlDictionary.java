package com.szymanski.yamlobjectmapper.structure;

import lombok.*;

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
    public Object resolve(String key, YamlNode value, Class<?> name) {
        return null;
    }

    @Override
    public String toString() {
        return key + ": " + value.toString();
    }
}
