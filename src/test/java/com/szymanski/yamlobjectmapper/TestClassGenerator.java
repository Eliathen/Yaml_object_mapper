package com.szymanski.yamlobjectmapper;

import com.szymanski.yamlobjectmapper.testClass.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class TestClassGenerator {

    public static Client getTestClass(){
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
