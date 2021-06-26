package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@YamlClass
@Getter
@Setter
public class User {

    @YamlKey
    protected int id;

    @YamlOneToOne
    @YamlKey
    protected Address address;

    @YamlKey
    protected boolean isActive = true;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", address=" + address +
                ", isActive=" + isActive +
                '}';
    }
}
