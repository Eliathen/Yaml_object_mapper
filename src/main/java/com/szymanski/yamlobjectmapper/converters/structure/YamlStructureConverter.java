package com.szymanski.yamlobjectmapper.converters.structure;

import com.szymanski.yamlobjectmapper.structure.YamlComplexObject;
import com.szymanski.yamlobjectmapper.structure.YamlObject;

import java.util.List;
import java.util.Map;


public class YamlStructureConverter {

    public  YamlObject convertToYaml(Map<String, Object> map) {
        if( map.isEmpty()){
            //TODO throw exception
        }
        YamlComplexObject yaml = new YamlComplexObject();
        map.forEach((k,v)->{
            if(v instanceof Map){

            } else if(v instanceof List){

            }
            else {

            }
        });
        return yaml;
    }

}
