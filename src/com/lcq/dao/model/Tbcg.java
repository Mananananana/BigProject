package com.lcq.dao.model;


import java.util.Objects;
import java.sql.Date;

public class Tbcg implements java.io.Serializable{
    private String id;
    private String gys_id;
    private String jsr_id;
    private String hw_id;
    private int sl;
    private Date time;
//    private String time;
    private float price;
    private String jd;
    private String jsfs;

    public Tbcg(){}

    public Tbcg(String id, String gys_id, String jsr_id, String hw_id, int sl, Date time, float price, String jd, String jsfs){
        this.id = id;
        this.gys_id = gys_id;
        this.jsr_id = jsr_id;
        this.hw_id = hw_id;
        this.sl = sl;
        this.time = time;
        this.price = price;
        this.jd = jd;
        this.jsfs = jsfs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGys_id() {
        return gys_id;
    }

    public void setGys_id(String gys_id) {
        this.gys_id = gys_id;
    }

    public String getJsr_id() {
        return jsr_id;
    }

    public void setJsr_id(String jsr_id) {
        this.jsr_id = jsr_id;
    }

    public String getHw_id() {
        return hw_id;
    }

    public void setHw_id(String hw_id) {
        this.hw_id = hw_id;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getJsfs() {
        return jsfs;
    }

    public void setJsfs(String jsfs) {
        this.jsfs = jsfs;
    }

    public String toString(){
        return getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tbcg tbcg = (Tbcg) o;
        return sl == tbcg.sl &&
                Objects.equals(id, tbcg.id) &&
                Objects.equals(gys_id, tbcg.gys_id) &&
                Objects.equals(jsr_id, tbcg.jsr_id) &&
                Objects.equals(hw_id, tbcg.hw_id) &&
                Objects.equals(time, tbcg.time) &&
                Objects.equals(price, tbcg.price) &&
                Objects.equals(jd, tbcg.jd) &&
                Objects.equals(jsfs, tbcg.jsfs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gys_id, jsr_id, hw_id, sl, time, price, jd, jsfs);
    }
}
