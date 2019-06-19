package com.lcq.dao.model;

import java.util.Objects;
import java.sql.Date;

public class Tbsh implements java.io.Serializable{
    private String id;
    private String cg_id;
    private String jsr_id;
    private Date time;
    private String jl;

    public Tbsh(){}

    public Tbsh(String id, String cg_id, String jsr_id, Date time, String jl){
        this.id = id;
        this.cg_id = cg_id;
        this.jsr_id = jsr_id;
        this.time = time;
        this.jl = jl;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getJl() {
        return jl;
    }

    public void setJl(String jl) {
        this.jl = jl;
    }

    public String toString(){
        return getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tbsh tbsh = (Tbsh) o;
        return Objects.equals(id, tbsh.id) &&
                Objects.equals(cg_id, tbsh.cg_id) &&
                Objects.equals(jsr_id, tbsh.jsr_id) &&
                Objects.equals(time, tbsh.time) &&
                Objects.equals(jl, tbsh.jl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cg_id, jsr_id, time, jl);
    }
}
