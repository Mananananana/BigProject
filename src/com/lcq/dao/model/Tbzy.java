package com.lcq.dao.model;

import java.util.Objects;

public class Tbzy implements java.io.Serializable {
    private String id;
    private String name;
    private int age;
    private String gender;
    private String zw;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getZw() {
        return zw;
    }

    public void setZw(String zw) {
        this.zw = zw;
    }

    public String toString(){
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tbzy tbzy = (Tbzy) o;
        return age == tbzy.age &&
                Objects.equals(id, tbzy.id) &&
                Objects.equals(name, tbzy.name) &&
                Objects.equals(gender, tbzy.gender) &&
                Objects.equals(zw, tbzy.zw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, gender, zw);
    }
}
