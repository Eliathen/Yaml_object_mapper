package com.szymanski.yamlobjectmapper.testClass;


import com.szymanski.yamlobjectmapper.annotations.YamlClass;
import com.szymanski.yamlobjectmapper.annotations.YamlId;
import com.szymanski.yamlobjectmapper.annotations.YamlKey;
import com.szymanski.yamlobjectmapper.annotations.YamlManyToMany;
import lombok.*;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@YamlClass
public class Product {

    @YamlKey
    private Long productId;
    @YamlKey
    private String name;
    @YamlKey
    private Double price;

    @YamlManyToMany
    private List<Category> categories;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) && Objects.equals(name, product.name) && Objects.equals(price, product.price);
    }


    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", categories=" + categories +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, price);
    }
}
