package com.szymanski.yamlobjectmapper.resolvers;

import com.szymanski.yamlobjectmapper.ReflectionHelper;
import com.szymanski.yamlobjectmapper.annotations.*;
import com.szymanski.yamlobjectmapper.converters.field.ConverterManager;
import com.szymanski.yamlobjectmapper.structure.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class YamlResolver {

    private final ConverterManager converterManager;

    private Map<Object, Integer> mapping = new HashMap<>();

    public YamlResolver() {
        this.converterManager = new ConverterManager();
    }

    public <T> Map<String, YamlNode> resolveToYaml(T object) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        List<Field> fields = Arrays.stream(object.getClass().getDeclaredFields()).collect(Collectors.toList());
        fields.addAll(ReflectionHelper.getSuperclassFields(object));
        YamlComplexObject yamlComplexObject = new YamlComplexObject();
        yamlComplexObject.setKey(getKeyName(object));
        Optional<Field> fieldId = fields.stream().filter(it -> it.isAnnotationPresent(YamlId.class)).findFirst();
        if(fieldId.isPresent()){
            Integer value = (Integer) ReflectionHelper.getFieldValue(object, fieldId.get().getName());
            mapping.put(object,value);
        }
        Map<String, YamlNode> resolved = new HashMap<>();
        for (Field field : fields) {
            try {
                if (isFieldTypeOfRelation(field)) {
                    if (ReflectionHelper.isContainsAnnotation(field, YamlOneToOne.class)) {
                        resolveOneToOne(object, yamlComplexObject, field);
                    } else if (ReflectionHelper.isContainsAnnotation(field, YamlOneToMany.class)) {
                        resolveOneToMany(object, yamlComplexObject, field);
                    } else {
                        AtomicInteger generatedId = new AtomicInteger();
                        YamlSequence ids = new YamlSequence();
                        ids.setKey(field.getName());
                        Collection<?> value = (Collection<?>) ReflectionHelper.getFieldValue(object, field.getName());
                        List<YamlNode> manyToMany = new ArrayList<>();
                        for (Object obj : value) {
                            Map<String, YamlNode> tmp = resolveToYaml(obj);
                            tmp.keySet().forEach(it -> {
                                YamlScalar id = new YamlScalar(String.valueOf(generatedId.incrementAndGet()));
                                ids.addNode(id);
                                tmp.get(it).addNode(new YamlDictionary("_id", id));
                                manyToMany.add(tmp.get(it));
                            });
                        }
                        ids.setAnchors(Collections.singletonList(field.getName()));
                        yamlComplexObject.addNode(ids);
                        resolved.put(field.getName(), new YamlSequence(manyToMany));
                    }
                } else if (!ReflectionHelper.isContainsAnnotation(field, Mapped.class)) {
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
                } else {
                    YamlSequence yamlSequence = new YamlSequence();
                    yamlSequence.setKey(field.getName());
                    yamlSequence.setAnchors(Collections.singletonList(field.getAnnotation(Mapped.class).name()));
                    Collection<?> value = (Collection<?>) ReflectionHelper.getFieldValue(object, field.getName());
                    HashSet<String> ids= new HashSet<>();
                    for (Object o : value) {
                        if(mapping.containsKey(o)){
                            Optional<Field> id = ReflectionHelper.getSuperclassFields(o).stream().filter(it -> it.isAnnotationPresent(YamlId.class)).findFirst();
                            if(id.isPresent()){
                                Object fieldValue = ReflectionHelper.getFieldValue(o, id.get().getName());
                                ids.add(fieldValue.toString());
                            }
                        }
                    }
                    ids.forEach(it -> yamlSequence.addNode(new YamlScalar(it)));
                    yamlComplexObject.addNode(yamlSequence);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String fileName = object.getClass().getSimpleName().substring(0, 1).toLowerCase(Locale.ROOT) + object.getClass().getSimpleName().substring(1);
        resolved.put(fileName, yamlComplexObject);
        return resolved;
    }

    private <T> void resolveOneToOne(T object, YamlComplexObject yamlComplexObject, Field field) throws
            IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        YamlComplexObject oneToOneYaml = new YamlComplexObject();
        oneToOneYaml.setKey(getKeyName(field));
        Map<String, YamlNode> oneToOne = resolveToYaml(ReflectionHelper.getFieldValue(object, field.getName()));
        for (String key : oneToOne.keySet()) {
            yamlComplexObject.addNode(oneToOne.get(key));
        }
    }

    private <T> void resolveOneToMany(T object, YamlComplexObject yamlComplexObject, Field field) throws
            IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        YamlSequence oneToManyYaml = new YamlSequence();
        oneToManyYaml.setKey(field.getName());
        Collection<?> value = (Collection<?>) ReflectionHelper.getFieldValue(object, field.getName());
        for (Object obj : value) {
            Map<String, YamlNode> oneToMany = resolveToYaml(obj);
            oneToMany.keySet().forEach(it -> oneToManyYaml.addNode(oneToMany.get(it)));
        }
        yamlComplexObject.addNode(oneToManyYaml);
    }

    private <T> String getKeyName(T object) {
        if (object.getClass().isAnnotationPresent(YamlKey.class)) {
            return object.getClass().getAnnotation(YamlKey.class).name();
        } else {
            return object.getClass().getSimpleName().toLowerCase();
        }
    }

    private boolean isFieldTypeOfRelation(Field field) {
        return ReflectionHelper.isContainsAnnotation(field, YamlOneToOne.class) ||
                ReflectionHelper.isContainsAnnotation(field, YamlOneToMany.class) ||
                ReflectionHelper.isContainsAnnotation(field, YamlManyToMany.class);
    }

}
