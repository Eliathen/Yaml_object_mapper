package com.szymanski.yamlobjectmapper;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReflectionHelper {

    public static List<Field> getSuperclassFields(Object object) {
        Class<?> clazz = object.getClass().getSuperclass();
        List<Field> fields = new ArrayList<>();
        while (!clazz.isInstance(Object.class)) {
            fields.addAll(Arrays.stream(clazz.getDeclaredFields()).filter(it -> it.getModifiers() != Modifier.PRIVATE).collect(Collectors.toList()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    public static boolean isFieldTypeOfCollection(Field field) {
        return Collection.class.isAssignableFrom(field.getType());
    }

    public static Object getFieldValue(Object object, String fieldName) throws IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        try {
            String name = getGetterNameForFieldName(fieldName);
            return object.getClass().getMethod(name).invoke(object);
        } catch (NoSuchMethodException e) {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object result = field.get(object);
            field.setAccessible(false);
            return result;
        }
    }
    public static String getGetterNameForFieldName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase(Locale.ROOT) + fieldName.substring(1);
    }

    public static Class<?> getFieldType(Field field) {
        return field.getType();
    }

    public static boolean isContainsAnnotation(AnnotatedElement element, Class<?> annotation) {
        for (Annotation fieldAnnotation : element.getAnnotations()) {
            if (fieldAnnotation.annotationType().equals(annotation)) return true;
        }
        return false;
    }

    public static Annotation[] getAnnotations(Class<?> clazz) {
        return clazz.getAnnotations();
    }
}
