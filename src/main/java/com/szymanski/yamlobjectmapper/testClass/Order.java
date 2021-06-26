package com.szymanski.yamlobjectmapper.testClass;


import com.szymanski.yamlobjectmapper.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@YamlClass
public class Order {

    @YamlKey
    private Long id;

    @YamlKey(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @YamlOneToMany
    private List<Product> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }
}
