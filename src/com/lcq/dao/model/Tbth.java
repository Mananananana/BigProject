package com.lcq.dao.model;

import java.util.Objects;
import java.sql.Date;

public class Tbth implements java.io.Serializable{
    private String id;
    private String cg_id;
    private String jsr_id;
    private String yy;
    private Date time;

    public Tbth(){}

    public Tbth(String id, String cg_id, String jsr_id, String yy, Date time){
        this.id = id;
        this.cg_id = cg_id;
        this.jsr_id = jsr_id;
        this.yy = yy;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCg_id() {
        return cg_id;
    }

    public void setCg_id(String cg_id) {
        this.cg_id = cg_id;
    }

    public String getJsr_id() {
        return jsr_id;
    }

    public void setJsr_id(String jsr_id) {
        this.jsr_id = jsr_id;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String toString(){
        return getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tbth tbth = (Tbth) o;
        return Objects.equals(id, tbth.id) &&
                Objects.equals(cg_id, tbth.cg_id) &&
                Objects.equals(jsr_id, tbth.jsr_id) &&
                Objects.equals(yy, tbth.yy) &&
                Objects.equals(time, tbth.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cg_id, jsr_id, yy, time);
    }
}
