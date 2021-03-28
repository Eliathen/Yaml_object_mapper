package com.szymanski.yamlobjectmapper.structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public abstract class YamlObject {

        @Getter
        @Setter
        private String key;

        abstract Object resolve(String key, YamlObject value, Class name);
}
