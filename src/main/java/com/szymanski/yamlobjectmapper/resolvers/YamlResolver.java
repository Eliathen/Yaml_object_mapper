package com.szymanski.yamlobjectmapper.resolvers;

import com.szymanski.yamlobjectmapper.ReflectionHelper;
import com.szymanski.yamlobjectmapper.annotations.*;
import com.szymanski.yamlobjectmapper.converters.field.ConverterManager;
import com.szymanski.yamlobjectmapper.structure.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class YamlResolver {

    private final ConverterManager converterManager;

    private Long id = 0L;

    public YamlResolver() {
        this.converterManager = new ConverterManager();
    }

    public <T> Map<String, YamlNode> resolveToYaml(T object) {
        List<Field> fields = Arrays.stream(object.getClass().getDeclaredFields()).collect(Collectors.toList());
        fields.addAll(ReflectionHelper.getSuperclassFields(object));
        YamlComplexObject yamlComplexObject = new YamlComplexObject();
        yamlComplexObject.setKey(getKeyName(object));
        for (Field field : fields) {
            try {
                if (isFieldTypeOfRelation(field)) {
                    if(ReflectionHelper.isContainsAnnotation(field, YamlOneToOne.class)){
                        YamlComplexObject oneToOneYaml = new YamlComplexObject();
                        oneToOneYaml.setKey(getKeyName(field));
                        Map<String, YamlNode> oneToOne = resolveToYaml(ReflectionHelper.getFieldValue(object, field.getName()));
                        for (String key : oneToOne.keySet()) {
                            yamlComplexObject.addNode(oneToOne.get(key));
                        }
                    } else if(ReflectionHelper.isContainsAnnotation(field, YamlOneToMany.class)){
                        YamlSequence oneToManyYaml = new YamlSequence();
                        oneToManyYaml.setKey(getKeyName(field.getClass()));
                        Collection<?> value = (Collection<?>) ReflectionHelper.getFieldValue(object.getClass(), field.getName());
                        for (Object obj : value) {
                            Map<String, YamlNode> oneToMany = resolveToYaml(obj);
                            oneToMany.keySet().forEach(it ->  oneToManyYaml.addNode(oneToMany.get(it)));
                        }
                    } else {
                        //TODO manyToMany implements
                    }
                } else {
                    if (ReflectionHelper.isFieldTypeOfCollection(field)) {
                        Collection<?> value = (Collection<?>) ReflectionHelper.getFieldValue(object, field.getName());
                        YamlSequence yamlSequence = new YamlSequence();
                        YamlKey annotation = null;
                        if (ReflectionHelper.isContainsAnnotation(field, YamlKey.class)) {
                            annotation = field.getAnnotation(YamlKey.class);
                            yamlSequence.setKey(annotation.name());
                        } else {
                            yamlSequence.setKey(field.getName());
                        }
                        for (Object obj : value) {
                            YamlScalar yamlScalar = new YamlScalar();
                            yamlScalar.setValue(
                                    converterManager.convertToString(obj.getClass(),
                                            obj,
                                            (null != annotation) ? annotation.pattern() : "")
                            );
                            yamlSequence.addNode(yamlScalar);
                        }
                        yamlComplexObject.addNode(yamlSequence);
                    } else {
                        YamlDictionary dictionary = new YamlDictionary();
                        YamlKey annotation = null;
                        if (ReflectionHelper.isContainsAnnotation(field, YamlKey.class)) {
                            annotation = field.getAnnotation(YamlKey.class);
                            dictionary.setKey(annotation.name());
                        } else {
                            dictionary.setKey(field.getName());
                        }
                        Object value = ReflectionHelper.getFieldValue(object, field.getName());
                        YamlScalar scalar = new YamlScalar(
                                converterManager.convertToString(
                                        ReflectionHelper.getFieldType(field),
                                        value,
                                        (null != annotation) ? annotation.pattern() : ""
                                ));
                        dictionary.setValue(scalar);
                        yamlComplexObject.addNode(dictionary);
                    }
                }

            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        String fileName = object.getClass().getSimpleName().substring(0, 1).toLowerCase(Locale.ROOT) + object.getClass().getSimpleName().substring(1);
        Map<String, YamlNode> resolved = new HashMap<>();
        resolved.put(fileName, yamlComplexObject);
        return resolved;
    }

    private <T> String getKeyName(T object) {
        Annotation[] annotations = object.getClass().getAnnotations();
        Optional<Annotation> annotation = Arrays.stream(annotations).findFirst().filter(it -> it.annotationType().equals(YamlKey.class));
        return annotation.map(value -> ((YamlKey) value).name()).orElseGet(() -> object.getClass().getSimpleName().toLowerCase());
    }

    private boolean isFieldTypeOfRelation(Field field) {
        return ReflectionHelper.isContainsAnnotation(field, YamlOneToOne.class) ||
                ReflectionHelper.isContainsAnnotation(field, YamlOneToMany.class) ||
                ReflectionHelper.isContainsAnnotation(field, YamlManyToMany.class);
    }

}
