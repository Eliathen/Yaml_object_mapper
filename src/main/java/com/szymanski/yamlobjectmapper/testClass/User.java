package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@YamlClass(name = "user")
@Getter
@Setter
public class User {
    @YamlId
    protected int id;
    @YamlOneToOne
    @YamlKey(name = "address")
    protected Address address;

    public User(int id, Address address) {
        this.id = id;
        this.address = address;
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address);
    }
}
