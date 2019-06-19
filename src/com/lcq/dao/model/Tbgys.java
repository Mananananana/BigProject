package com.lcq.dao.model;

import java.util.Objects;

public class Tbgys implements java.io.Serializable {
    private String id;
    private String name;
    private int xy;
    private String dz;

    public Tbgys(){}

    public Tbgys(String id, String name, int xy, String dz){
        this.id = id;
        this.name = name;
        this.xy = xy;
        this.dz = dz;
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

    public int getXy() {
        return xy;
    }

    public void setXy(int xy) {
        this.xy = xy;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public String toString(){
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tbgys tbgys = (Tbgys) o;
        return xy == tbgys.xy &&
                Objects.equals(id, tbgys.id) &&
                Objects.equals(name, tbgys.name) &&
                Objects.equals(dz, tbgys.dz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, xy, dz);
    }
}
