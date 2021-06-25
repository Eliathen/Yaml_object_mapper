package com.szymanski.yamlobjectmapper.testClass;

import com.szymanski.yamlobjectmapper.annotations.YamlClass;
import com.szymanski.yamlobjectmapper.annotations.YamlKey;
import com.szymanski.yamlobjectmapper.annotations.YamlOneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@YamlClass(name = "user")
@Getter
@Setter
public class User {

    @YamlKey(name = "id")
    protected int id;

    @YamlOneToOne
    @YamlKey(name = "address")
    protected Address address;

    @YamlKey(name = "isActive")
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
}
