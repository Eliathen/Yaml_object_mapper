package com.szymanski.yamlobjectmapper.resolvers;

import com.szymanski.yamlobjectmapper.annotations.YamlKey;
import com.szymanski.yamlobjectmapper.converters.ConverterManager;
import com.szymanski.yamlobjectmapper.helpers.ReflectionHelper;
import com.szymanski.yamlobjectmapper.structure.*;
import com.szymanski.yamlobjectmapper.testClass.Client;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class YamlResolverToObject {

    private final Map<String, YamlNode> yamlNodes = new HashMap<>();


    private final Map<String, Object> resolvedObjects = new HashMap<>();

    private final ConverterManager converterManager;

    public YamlResolverToObject() {
        this.converterManager = new ConverterManager();
    }

    @SneakyThrows
    public Object resolve(List<YamlNode> nodes, Class<?> clazz) {
        Collections.reverse(nodes);
        for (YamlNode node : nodes) {
            yamlNodes.put(node.getKey(), node);
        }
        return resolveYaml(nodes.get(0), clazz);
    }

    @SneakyThrows
    private Object resolveYaml(YamlNode node, Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if(node instanceof YamlComplexObject){
            var object = clazz.getConstructor().newInstance();
            for (YamlNode yamlNode : ((YamlComplexObject) node).getValue()) {
                if(yamlNode.getKey().contains("_")) {
                    resolvedObjects.put(((YamlDictionary) yamlNode).getValue().getValue(), object);
                    continue;
                }
                var field = ReflectionHelper.getFieldForObjectByKey(object, yamlNode.getKey());
                var method = ReflectionHelper.getSetterNameForFieldName(yamlNode.getKey());
                if(yamlNode instanceof YamlDictionary){
                    clazz.getMethod(method, field.getType()).invoke(object, resolveYamlDictionary((YamlDictionary) yamlNode, field));
                } else if(yamlNode instanceof YamlScalar){
                    return resolveYamlScalar((YamlScalar) yamlNode, field);
                } else if(yamlNode instanceof YamlSequence){
                    var fieldType = (ParameterizedType) field.getGenericType();
                    clazz.getMethod(method, field.getType()).invoke(object, resolveYaml(yamlNode, (Class<?>) fieldType.getActualTypeArguments()[0]));
                }
                else {
                    clazz.getMethod(method, field.getType()).invoke(object, resolveYaml(yamlNode, field.getType()));
                }
            }
            return object;
        } else if(node instanceof YamlSequence){
            return resolveYamlSequence((YamlSequence) node, clazz);
        }
        return converterManager.convertToValue(clazz, ((YamlScalar) node).getValue(), "" );
    }

    private Object resolveYamlDictionary(YamlDictionary node, Field field) {
        return converterManager.convertToValue(field.getType(), node.getValue().getValue(), field.getAnnotation(YamlKey.class).pattern());
    }
    private Object resolveYamlScalar(YamlScalar node, Field field){
        return converterManager.convertToValue(field.getType(), node.getValue(), field.getAnnotation(YamlKey.class).pattern());
    }
    @SneakyThrows
    private <T> List<T> resolveYamlSequence(YamlSequence node, Class<T> type) {
        List<T> list = new ArrayList<>();
        if(node.getTags() != null){
            Optional<String> tag = node.getTags().stream().findAny();
            if(tag.isPresent() && yamlNodes.containsKey(tag.get())) {
//                resolveYaml(yamlNodes.get(tag.get()), type);
//                list.add((T) res);
            } else {

            }
        }else {
            for (YamlNode yamlNode : (node).getValue()) {
                var res = resolveYaml(yamlNode, type);
                list.add((T) res);
            }
        }
        return list;
    }
}
