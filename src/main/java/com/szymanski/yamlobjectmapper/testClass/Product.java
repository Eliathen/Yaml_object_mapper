package com.szymanski.yamlobjectmapper.testClass;


import com.szymanski.yamlobjectmapper.annotations.YamlClass;
import com.szymanski.yamlobjectmapper.annotations.YamlId;
import com.szymanski.yamlobjectmapper.annotations.YamlKey;
import com.szymanski.yamlobjectmapper.annotations.YamlManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@YamlClass(name = "product")
public class Product {

    @YamlKey(name = "productId")
    private Long productId;
    @YamlKey(name = "name")
    private String name;
    @YamlKey(name = "price")
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
    public int hashCode() {
        return Objects.hash(productId, name, price);
    }
}
