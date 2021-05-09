package com.szymanski.yamlobjectmapper;

import com.szymanski.yamlobjectmapper.annotations.YamlClass;
import com.szymanski.yamlobjectmapper.annotations.YamlKey;
import com.szymanski.yamlobjectmapper.converters.field.ConverterManager;
import com.szymanski.yamlobjectmapper.parser.YamlParser;
import com.szymanski.yamlobjectmapper.structure.YamlComplexObject;
import com.szymanski.yamlobjectmapper.structure.YamlDictionary;
import com.szymanski.yamlobjectmapper.structure.YamlNode;
import com.szymanski.yamlobjectmapper.structure.YamlScalar;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class YamlMapper {

    private YamlReader yamlReader;
    private YamlParser yamlParser;
    private ConverterManager converterManager;
    private YamlWriter writer;

    public YamlMapper() {
        yamlReader = new YamlReader();
        yamlParser = new YamlParser();
        converterManager = new ConverterManager();
        writer = new YamlWriter();
    }

    public <T> T mapToObject(String path, Class<T> type) {
        List<String> lines = yamlReader.convert(path);
        YamlNode node = yamlParser.parse(lines);
        System.out.println(node);
        return (T) new Object();
    }

    public <T> void mapToYaml(T object) {
        Field[] fields = object.getClass().getDeclaredFields();
        getAnnotations(object.getClass());
        YamlComplexObject yamlComplexObject = new YamlComplexObject();
        Optional<Annotation> yamlClass = Arrays.stream(getAnnotations(object.getClass()))
                .filter(it -> it.annotationType().equals(YamlClass.class))
                .findFirst();
        if (yamlClass.isPresent()) {
            yamlComplexObject.setKey(((YamlClass) yamlClass.get()).name());
        } else {
            yamlComplexObject.setKey(object.getClass().getSimpleName());
        }
        for (Field field : fields) {
            YamlDictionary dictionary = new YamlDictionary();
            String key;
            YamlKey annotation = null;
            if (isContainsAnnotation(field, YamlKey.class)) {
                annotation = field.getAnnotation(YamlKey.class);
                key = annotation.name();
            } else {
                key = field.getName();
            }
            dictionary.setKey(key);
            try {
                Object value = getFieldValue(object, field.getName());
                YamlScalar scalar = new YamlScalar(
                        converterManager.convertToString(
                                getFieldType(field),
                                value,
                                (null != annotation) ? annotation.pattern() : ""
                        ));
                dictionary.setValue(scalar);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            yamlComplexObject.addNode(dictionary);
        }
        writer.saveToFile(yamlComplexObject);
        List<String> result = writer.getResult();
        try {
            String fileName = object.getClass().getSimpleName().substring(0, 1).toLowerCase(Locale.ROOT) + object.getClass().getSimpleName().substring(1);
            FileWriter writer = new FileWriter(fileName + ".yaml");
            for (String s : result) {
                writer.write(s + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isFieldCollection(Field field) {
        return field.getType().equals(Collection.class);
    }

    public Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return object.getClass().getMethod(getGetterNameForFieldName(fieldName)).invoke(object);
    }

    public String getGetterNameForFieldName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase(Locale.ROOT) + fieldName.substring(1);
    }

    public Class<?> getFieldType(Field field) {
        return field.getType();
    }

    public boolean isContainsAnnotation(AnnotatedElement element, Class<?> annotation) {
        for (Annotation fieldAnnotation : element.getAnnotations()) {
            if (fieldAnnotation.annotationType().equals(annotation)) return true;
        }
        return false;
    }

    public Annotation[] getAnnotations(Class<?> clazz) {
        return clazz.getAnnotations();
    }
}
