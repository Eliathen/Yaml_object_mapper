package com.szymanski.yamlobjectmapper.resolvers;

import com.szymanski.yamlobjectmapper.ReflectionHelper;
import com.szymanski.yamlobjectmapper.annotations.*;
import com.szymanski.yamlobjectmapper.converters.field.ConverterManager;
import com.szymanski.yamlobjectmapper.structure.*;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Ref;
import java.util.*;
import java.util.stream.Collectors;

public class YamlResolver {

    private final ConverterManager converterManager;

    private final Map<Object, String> mapping = new HashMap<>();


    public final Map<String, YamlNode> retYaml = new HashMap<>();

    private Long id = 1L;

    public YamlResolver() {
        this.converterManager = new ConverterManager();
    }

    @SneakyThrows
    public <T> Map<String, YamlNode> resolve(T object) {
        var result = resolveToYaml(object);
        retYaml.put(getKeyName(object, object.getClass().getSimpleName()), result);
        return retYaml;
    }

    public <T> YamlNode resolveToYaml(T object) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        List<Field> fields = Arrays.stream(object.getClass().getDeclaredFields()).collect(Collectors.toList());
        fields.addAll(ReflectionHelper.getSuperclassFields(object));

        YamlComplexObject yamlComplexObject = new YamlComplexObject();
        yamlComplexObject.setKey(getKeyName(object, object.getClass().getSimpleName()));

        for (Field field : fields) {
            if (isFieldTypeOfRelation(field)) {
                if (ReflectionHelper.isContainsAnnotation(field, YamlOneToOne.class)) {
                    yamlComplexObject.addNode(resolveToYaml(ReflectionHelper.getFieldValue(object, field.getName())));
                } else if (ReflectionHelper.isContainsAnnotation(field, YamlOneToMany.class)) {
                    YamlSequence yamlSequence = new YamlSequence();
                    yamlSequence.setKey(getKeyName(field, field.getName()));
                    Collection<?> value = (Collection<?>) ReflectionHelper.getFieldValue(object, field.getName());
                    for (Object o : value) {
                        YamlNode resolvedNode = resolveToYaml(o);
                        yamlSequence.addNode(resolvedNode);
                        var generatedKey = new YamlDictionary();
                        if (mapping.containsKey(o)) {
                            generatedKey.setKey("_id");
                            generatedKey.setValue(new YamlScalar(mapping.get(o)));
                        } else {
                            generatedKey = generateKeyDictionary();
                            mapping.put(o, generatedKey.getValue().getValue());
                        }
                        resolvedNode.addNode(generatedKey);
                    }
                    yamlComplexObject.addNode(yamlSequence);
                } else {
                    YamlSequence yamlSequence = new YamlSequence();
                    String keyName = getKeyName(field, field.getName());
                    yamlSequence.setKey(keyName);
                    Collection<?> value = (Collection<?>) ReflectionHelper.getFieldValue(object, field.getName());
                    var keys = new YamlSequence();
                    keys.setKey(keyName);
                    keys.setAnchors(Collections.singletonList(keyName));
                    for (Object o : value) {
                        var generatedKey = new YamlDictionary();
                        YamlNode resolvedNode = resolveToYaml(o);
                        if (mapping.containsKey(o)) {
                            generatedKey.setKey("_id");
                            generatedKey.setValue(new YamlScalar(mapping.get(o)));
                            resolvedNode.addNode(generatedKey);
                        } else {
                            generatedKey = generateKeyDictionary();
                            mapping.put(o, generatedKey.getValue().getValue());
                            resolvedNode.addNode(generatedKey);
                            yamlSequence.addNode(resolvedNode);
                        }
                        keys.addNode(generatedKey.getValue());
                    }

                    if (retYaml.containsKey(keyName)) {
                        for (YamlNode yamlNode : yamlSequence.getValue()) {
                            retYaml.get(keyName).addNode(yamlNode);
                        }
                    } else {
                        retYaml.put(keyName, yamlSequence);
                    }
                    yamlComplexObject.addNode(keys);
                }
            } else if (!ReflectionHelper.isContainsAnnotation(field, Mapped.class)) {
                if (ReflectionHelper.isFieldTypeOfCollection(field)) {
                    Collection<?> value = (Collection<?>) ReflectionHelper.getFieldValue(object, field.getName());
                    YamlSequence yamlSequence = new YamlSequence();
                    var annotation = field.getAnnotation(YamlKey.class);
                    yamlSequence.setKey(getKeyName(field, field.getName()));
                    for (Object o : value) {
                        yamlSequence.addNode(new YamlScalar(
                                        converterManager.convertToString(
                                                o.getClass(),
                                                o,
                                                (annotation != null) ? annotation.pattern() : ""
                                        )
                                )
                        );
                    }
                    yamlComplexObject.addNode(yamlSequence);
                } else {
                    YamlDictionary yamlDictionary = new YamlDictionary();
                    yamlDictionary.setKey(getKeyName(field, field.getName()));
                    var annotation = field.getAnnotation(YamlKey.class);
                    Object value = ReflectionHelper.getFieldValue(object, field.getName());
                    yamlDictionary.setValue(new YamlScalar(
                            converterManager.convertToString(
                                    ReflectionHelper.getFieldType(field),
                                    value,
                                    (annotation != null) ? annotation.pattern() : ""
                            )
                    ));
                    yamlComplexObject.addNode(yamlDictionary);
                }
            } else {
                YamlSequence keys = new YamlSequence();
                keys.setKey(field.getName());
                keys.setAnchors(Collections.singletonList(field.getAnnotation(Mapped.class).name()));
                Collection<?> value = (Collection<?>) ReflectionHelper.getFieldValue(object, field.getName());
                for (Object o : value) {
                    if (mapping.containsKey(o)) {
                        var key = mapping.get(o);
                        keys.addNode(new YamlScalar(key));
                    } else {
                        var key = generateKeyDictionary().getValue();
                        mapping.put(o, key.getValue());
                        keys.addNode(new YamlScalar(key.getValue()));
                    }
                }
                yamlComplexObject.addNode(keys);
            }
        }
        return yamlComplexObject;
    }

    private <T> String getKeyName(T object, String name) {
        if (object.getClass().isAnnotationPresent(YamlKey.class)) {
            return object.getClass().getAnnotation(YamlKey.class).name();
        } else if (object.getClass().isAnnotationPresent(YamlClass.class)) {
            return object.getClass().getAnnotation(YamlClass.class).name();
        } else {
            return name;
        }
    }

    private boolean isFieldTypeOfRelation(Field field) {
        return ReflectionHelper.isContainsAnnotation(field, YamlOneToOne.class) ||
                ReflectionHelper.isContainsAnnotation(field, YamlOneToMany.class) ||
                ReflectionHelper.isContainsAnnotation(field, YamlManyToMany.class);
    }

    private YamlDictionary generateKeyDictionary() {
        YamlDictionary yamlDictionary = new YamlDictionary();
        yamlDictionary.setKey("_id");
        yamlDictionary.setValue(new YamlScalar(this.id.toString()));
        this.id++;
        return yamlDictionary;
    }

}
