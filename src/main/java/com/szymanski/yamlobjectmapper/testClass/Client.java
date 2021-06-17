package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.YamlClass;
import com.szymanski.yamlobjectmapper.annotations.YamlKey;
import com.szymanski.yamlobjectmapper.annotations.YamlOneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;


@NoArgsConstructor
@Data
@YamlClass(name = "client")
public class Client extends User{

    @YamlKey(name = "age")
    private int age;
    @YamlKey(name = "first_name")
    private String firstName;
    @YamlKey(name = "second_name")
    private List<String> secondNames;
    @YamlOneToMany
    private List<Order> orders;

    public Client(int id, Address address, int age, String firstName, List<String> secondNames, List<Order> orders) {
        super(id, address);
        this.age = age;
        this.firstName = firstName;
        this.secondNames = secondNames;
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return age == client.age && Objects.equals(firstName, client.firstName) && Objects.equals(secondNames, client.secondNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, firstName, secondNames);
    }
}
