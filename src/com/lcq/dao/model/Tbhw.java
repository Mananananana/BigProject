package com.lcq.dao.model;

import java.util.Objects;

public class Tbhw implements java.io.Serializable{
    private String id;
    private String name;
    private int sl;
    private float price;

    public Tbhw(){}

    public Tbhw(String id, String name, int sl, float price){
        this.id = id;
        this.name = name;
        this.sl = sl;
        this.price = price;
    }

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

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String toString(){
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tbhw tbhw = (Tbhw) o;
        return sl == tbhw.sl &&
                Float.compare(tbhw.price, price) == 0 &&
                Objects.equals(id, tbhw.id) &&
                Objects.equals(name, tbhw.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sl, price);
    }
}
