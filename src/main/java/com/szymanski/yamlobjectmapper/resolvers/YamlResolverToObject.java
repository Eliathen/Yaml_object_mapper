package com.szymanski.yamlobjectmapper.resolvers;

import com.szymanski.yamlobjectmapper.annotations.YamlKey;
import com.szymanski.yamlobjectmapper.converters.ConverterManager;
import com.szymanski.yamlobjectmapper.helpers.ReflectionHelper;
import com.szymanski.yamlobjectmapper.structure.*;
import com.szymanski.yamlobjectmapper.testClass.Client;
import lombok.SneakyThrows;

import java.io.FileNotFoundException;
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

    public Object resolve(List<YamlNode> nodes, Class<?> clazz) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException {
        Collections.reverse(nodes);
        for (YamlNode node : nodes) {
            yamlNodes.put(node.getKey(), node);
        }
        var firstClass = nodes.stream().filter(it -> it.getKey().equals(clazz.getSimpleName().toLowerCase(Locale.ROOT))).findFirst();
        if(firstClass.isPresent()){
            var result =  resolveYaml(firstClass.get(), clazz, false);
            return result;
        } else {
            throw new FileNotFoundException("Not find file " + clazz.getSimpleName() + ".yaml");
        }
    }

    private Object resolveYaml(YamlNode node, Class<?> clazz, boolean shouldSkip) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        if(node instanceof YamlComplexObject){
            var object = clazz.getConstructor().newInstance();
            object = isContainsYamlId(object, node);
            for (YamlNode yamlNode : ((YamlComplexObject) node).getValue()) {
                if(yamlNode.getKey().equals("_id")) {
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
                    clazz.getMethod(method, field.getType()).invoke(object, resolveYaml(yamlNode, (Class<?>) fieldType.getActualTypeArguments()[0], shouldSkip));
                }
                else {
                    clazz.getMethod(method, field.getType()).invoke(object, resolveYaml(yamlNode, field.getType(), shouldSkip));
                }
            }
            return object;
        } else if(node instanceof YamlSequence){
            return resolveYamlSequence((YamlSequence) node, clazz, shouldSkip);
        }
        return converterManager.convertToValue(clazz, ((YamlScalar) node).getValue(), "" );
    }

    private Object isContainsYamlId(Object object, YamlNode yamlNode) {
        for (YamlNode node : ((YamlComplexObject) yamlNode).getValue()) {
            if(node.getKey().contains("_id") && !resolvedObjects.containsKey(((YamlDictionary) node).getValue().getValue())) {
                resolvedObjects.put(((YamlDictionary) node).getValue().getValue(), object);
            } else if(node.getKey().contains("_id")){
                object = resolvedObjects.get(((YamlDictionary) node).getValue().getValue());
            }
        }
        return object;
    }

    private Object resolveYamlDictionary(YamlDictionary node, Field field) {
        return converterManager.convertToValue(field.getType(), node.getValue().getValue(), field.getAnnotation(YamlKey.class).pattern());
    }
    private Object resolveYamlScalar(YamlScalar node, Field field){
        return converterManager.convertToValue(field.getType(), node.getValue(), field.getAnnotation(YamlKey.class).pattern());
    }
    private <T> List<T> resolveYamlSequence(YamlSequence node, Class<T> type, boolean shouldSkip) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        List<T> list = new ArrayList<>();
        if(node.getTags() != null){
            Optional<String> tag = node.getTags().stream().findAny();
            if(tag.isPresent() && !shouldSkip) {
                for (YamlNode yamlNode : node.getValue()) {
                    String key = ((YamlScalar) yamlNode).getValue();
                    if(!resolvedObjects.containsKey(key) && yamlNodes.containsKey(tag.get())){
                        resolveYaml(yamlNodes.get(tag.get()), type, true);
                    }
                    if(resolvedObjects.containsKey(key)){
                        list.add((T) resolvedObjects.get(key));
                    }
                }
            } else {
                for (YamlNode yamlNode : node.getValue()) {
                    String key = ((YamlScalar) yamlNode).getValue();
                    var obj = type.getConstructor().newInstance();
                    if(resolvedObjects.containsKey(key)){
                        list.add((T) resolvedObjects.get(key));
                    } else {
                        resolvedObjects.put(key, obj);
                        list.add(obj);
                    }
                }
            }
        }else {
            for (YamlNode yamlNode : (node).getValue()) {
                var res = resolveYaml(yamlNode, type, shouldSkip);
                list.add((T) res);
            }
        }
        return list;
    }
}
