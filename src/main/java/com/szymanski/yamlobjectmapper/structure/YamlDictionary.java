package com.szymanski.yamlobjectmapper.structure;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    public Object resolve(String key, YamlNode value, Class name) {
        return null;
    }
}
