package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Data
@YamlClass(name = "category")
public class Category {

    @YamlKey(name = "id")
    private Integer id;

    @YamlKey(name = "name")
    private String name;

    @YamlKey(name = "description")
    private String description;

    @Mapped(name = "products")
    private List<Product> products;

    public Category(Integer id, String name, String description, List<Product> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
