package com.szymanski.yamlobjectmapper.helpers;

import com.szymanski.yamlobjectmapper.annotations.YamlKey;
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
        return Collection.class.isAssignableFrom(field.getType()) || Collection.class == field.getType();
    }

    public static Object getFieldValue(Object object, String fieldName) throws IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        try {
            String name = getGetterNameForFieldName(fieldName);
            return object.getClass().getMethod(name).invoke(object);
        } catch (NoSuchMethodException e) {
            List<Field> superclassFields = getSuperclassFields(object);
            superclassFields.addAll(List.of(object.getClass().getDeclaredFields()));
            Optional<Field> field = superclassFields.stream().filter(it -> it.getName().equals(fieldName)).findFirst();
            Object result = null;
            if(field.isPresent()){
                field.get().setAccessible(true);
                result = field.get().get(object);
                field.get().setAccessible(false);
            } else {
                throw new NoSuchFieldException("Field " +fieldName + " not exist");
            }
            return result;
        }
    }
    public static String getGetterNameForFieldName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase(Locale.ROOT) + fieldName.substring(1);
    }

    public static String getSetterNameForFieldName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase(Locale.ROOT) + fieldName.substring(1);
    }
    public static Field getFieldForObjectByKey(Object object, String key){
        List<Field> fields = Arrays.stream(object.getClass().getDeclaredFields()).collect(Collectors.toList());
        fields.addAll(getSuperclassFields(object));
        Field retField = null;
        for (Field field : fields) {
//            var annotation = Arrays.stream(field.getAnnotationsByType(YamlKey.class)).filter(it -> it.annotationType().equals(YamlKey.class)).findFirst();
            /*if(annotation.isPresent() && annotation.get().name().equals(key)){
                retField = field;
                break;
            } else*/ if(field.getName().equals(key)){
                retField = field;
                break;
            }
        }
        return retField;
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
