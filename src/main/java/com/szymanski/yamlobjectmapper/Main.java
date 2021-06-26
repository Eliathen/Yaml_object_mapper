package com.szymanski.yamlobjectmapper;


import com.szymanski.yamlobjectmapper.testClass.*;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.NotDirectoryException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        YamlMapper mapper = new YamlMapper();
        Client client = generateTestObject();
        try {
            mapper.mapToYamlFile(client);
            System.out.println();
            var obj = mapper.mapToObject("D:\\Projects\\Yaml_object_mapper", Client.class);
            System.out.println("Before mapping = " + client);
            System.out.println("After  mapping = " + obj);
            Set<Category> categories = new HashSet<>();
            System.out.println();
            for (Order order : obj.getOrders()) {
                for (Product product : order.getProducts()) {
                    categories.addAll(product.getCategories());
                }
            }
            for (Category category : categories) {
                System.out.println(category.getName());
                System.out.println(category.getProducts());
            }
            Address address = new Address("Kielce", "Street", "ABC");
            User user = new User(1, address);
            mapper.mapToYamlFile(user);
            User newUser = mapper.mapToObject("D:\\Projects\\Yaml_object_mapper", User.class);
            System.out.println(newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Client generateTestObject() {
        Address address = new Address("Kielce", "Street", "ABC");
        Product product = new Product(1L, "Kettle", 60.0, Collections.emptyList());
        Product product2 = new Product(2L, "Washing machine", 450.0, Collections.emptyList());
        Product product3 = new Product(3L, "Sink", 120.0, Collections.emptyList());

        Category category = new Category(1, "Kitchen", "Kitchen description", List.of(product, product3));
        Category category2 = new Category(2, "Bathroom", "Bathroom equipment", List.of(product2, product3));
        product.setCategories(List.of(category));
        product2.setCategories(List.of(category2));
        product3.setCategories(List.of(category, category2));

        Order order = new Order(1L, LocalDate.now(), List.of(product2, product3));
        Order order2 = new Order(2L, LocalDate.now().minusDays(3), List.of(product, product3));

        return new Client(1,address, 25, "John", List.of("Dee", "Dee1"), List.of(order, order2));
    }
}
