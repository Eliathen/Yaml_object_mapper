package com.szymanski.yamlobjectmapper.structure;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class YamlDictionary extends YamlObject {
    @Getter
    @Setter
    private String value;

    public YamlDictionary(String key, String value) {
        super(key);
        this.value = value;
    }

    @Override
    public Object resolve(String key, YamlObject value, Class name) {
        return null;
    }
}
