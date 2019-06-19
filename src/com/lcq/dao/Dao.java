package com.lcq.dao;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import com.lcq.Item;
import com.lcq.dao.model.*;
import sun.java2d.pipe.SpanShapeRenderer;

public class Dao {
    protected static String dbClassName = "com.mysql.jdbc.Driver";
    protected static String CONNECTION = "jdbc:mysql://127.0.0.1/db_database53";

    public static Connection conn = null;

    static {
        try{
            if(conn == null){
                Class.forName(dbClassName).newInstance();
                Properties p = new Properties();
                p.put("user", "root");
                p.put("password", "123456!");
                p.put("useSSL", "false");
                p.put("verifyServerCertificate", "false");
                conn = DriverManager.getConnection(CONNECTION, p);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private Dao(){}

    public static ResultSet findForResultSet(String sql) throws SQLException {
        Statement statement = conn.createStatement();
        return statement.executeQuery(sql);
    }

    public static Tbzy getTbzy(Item item){
        String where = "name='" + item.getName() + "'";
        if (item.getId() != null){
            where = "zy_id='" + item.getId() + "'";
        }
        Tbzy zy = new Tbzy();
        try{
            ResultSet set = findForResultSet("select * from tb_zy where " + where);
            if(set.next()){
                zy.setId(set.getString("zy_id"));
                zy.setName(set.getString("name"));
                zy.setAge(set.getInt("age"));
                zy.setGender(set.getString("gender"));
                zy.setZw(set.getString("zy_zw"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return zy;
    }

    public static Tbhw getTbhw(Item item){
        String where = "name='" + item.getName() + "'";
        if(item.getId() != null){
            where = "hw_id='" + item.getId() + "'";
        }
        Tbhw hw = new Tbhw();
        try{
            ResultSet set = findForResultSet("select * from tb_hw where " + where);
            hw.setId(set.getString("hw_id"));
            hw.setName(set.getString("hw_name"));
            hw.setPrice(set.getFloat("price"));
            hw.setSl(set.getInt("hw_sl"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return hw;
    }

    public static Tbgys getTbgys(Item item){
        String where = "name='" + item.getName() + "'";
        if(item.getId() != null){
            where = "gys_id='" + item.getId() + "'";
        }
        Tbgys gys = new Tbgys();
        try{
            ResultSet set = findForResultSet("select * from tb_gys where " + where);
            gys.setId(set.getString("gys_id"));
            gys.setName(set.getString("gys_name"));
            gys.setXy(set.getInt("gys_xy"));
            gys.setDz(set.getString("gys_dz"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return gys;
    }

    public static Tbcg getTbcg(Item item){
        String where = "name='" + item.getName() + "'";
        if(item.getId() != null){
            where = "cg_id='" + item.getId() + "'";
        }
        Tbcg cg = new Tbcg();
        try{
            ResultSet set = findForResultSet("select * from tb_cg where " + where);
            cg.setId(set.getString("hw_id"));
            cg.setGys_id(set.getString("gys_id"));
            cg.setJsr_id(set.getString("jsr_id"));
            cg.setHw_id(set.getString("hw_id"));
            cg.setSl(set.getInt("cg_sl"));
            cg.setTime(set.getDate("cg_time"));
            cg.setPrice(set.getInt("cg_price"));
            cg.setJd(set.getString("cg_jd"));
            cg.setJsfs(set.getString("cg_jsfs"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return cg;
    }

    public static Tbsh getTbsh(Item item){
        String where = "name='" + item.getName() + "'";
        if(item.getId() != null){
            where = "sh_id='" + item.getId() + "'";
        }
        Tbsh sh = new Tbsh();
        try{
            ResultSet set = findForResultSet("select * from tb_sh where " + where);
            sh.setId(set.getString("sh_id"));
            sh.setCg_id(set.getString("cg_id"));
            sh.setJsr_id(set.getString("jsr_id"));
            sh.setTime(set.getDate("sh_time"));
            sh.setJl(set.getString("sh_jl"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return sh;
    }

    public static Tbth getTbth(Item item){
        String where = "name='" + item.getName() + "'";
        if(item.getId() != null){
            where = "th_id='" + item.getId() + "'";
        }
        Tbth th = new Tbth();
        try{
            ResultSet set = findForResultSet("select * from tb_th where " + where);
            th.setId(set.getString("th_id"));
            th.setCg_id(set.getString("cg_id"));
            th.setJsr_id(set.getString("jsr_id"));
            th.setYy(set.getString("th_yy"));
            th.setTime(set.getDate("th_time"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return th;
    }
    public static boolean checkLogin(String user_name, String password) throws SQLException{
        ResultSet set = findForResultSet("select * from user_list where username='" + user_name + "' and password='" + password + "'");
        if(set == null){
            return false;
        }
        return set.next();
    }

    public static boolean insert(String sql) throws SQLException {
        Statement state = conn.createStatement();
        return state.execute(sql);
    }

    public static boolean insertZY(Tbzy zy) throws SQLException {
        PreparedStatement sql = conn.prepareStatement("insert into tb_zy (zy_id, name, age, gender, zy_zw) values (?, ?, ?, ?, ?)");
        sql.setString(1, zy.getId());
        sql.setString(2, zy.getName());
        sql.setInt(3, zy.getAge());
        sql.setString(4, zy.getGender());
        sql.setString(5, zy.getZw());

        return sql.execute();
    }

    public static boolean insertHW(Tbhw hw) throws SQLException {
        PreparedStatement sql = conn.prepareStatement("insert into tb_hw (hw_id, hw_name, hw_sl, price) values (?, ?, ?, ?)");

        sql.setString(1, hw.getId());
        sql.setString(2, hw.getName());
        sql.setInt(3, hw.getSl());
        sql.setFloat(4, hw.getPrice());

        return sql.execute();
    }

    public static boolean insertGYS(Tbgys gys) throws SQLException{
        PreparedStatement sql = conn.prepareStatement("insert into tb_gys (gys_id, gys_name, gys_xy, gys_dz) values (?, ?, ?, ?)");

        sql.setString(1, gys.getId());
        sql.setString(2, gys.getName());
        sql.setInt(3, gys.getXy());
        sql.setString(4, gys.getDz());

        return sql.execute();
    }

    public static int insertCG(Tbcg cg) throws SQLException{
        PreparedStatement sql = conn.prepareStatement("insert into tb_cg (cg_id, gys_id, jsr_id, hw_id, cg_sl, cg_time, cg_price, cg_jd, cg_jsfs) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        sql.setString(1, cg.getId());
        sql.setString(2, cg.getGys_id());
        sql.setString(3, cg.getJsr_id());
        sql.setString(4, cg.getHw_id());
        sql.setInt(5, cg.getSl());
        sql.setDate(6, cg.getTime());
        sql.setFloat(7, cg.getPrice());
        sql.setString(8, cg.getJd());
        sql.setString(9, cg.getJsfs());

        return sql.executeUpdate();
    }

    public static int insertSH(Tbsh sh) throws SQLException{
        boolean autoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);

        PreparedStatement sql = conn.prepareStatement("insert into tb_sh (sh_id, cg_id, jsr_id, sh_time, sh_jl) values (?, ?, ?, ?, ?)");
        Statement sql2 = conn.createStatement();

        sql.setString(1, sh.getId());
        sql.setString(2, sh.getCg_id());
        sql.setString(3, sh.getJsr_id());
        sql.setDate(4, sh.getTime());
        sql.setString(5, sh.getJl());
        int result = sql2.executeUpdate("update tb_cg set cg_jd = '已收货' where cg_id = '" + sh.getCg_id() + "'");
        int result1 = sql2.executeUpdate("update tb_hw set hw_sl = hw_sl + (select cg_sl from tb_cg where cg_id = '" + sh.getCg_id() + "') where hw_id = (select hw_id from tb_cg where cg_id = '" + sh.getCg_id() + "')");
        int result2 = sql.executeUpdate();


        if(result > 0 && result1 > 0 && result2 > 0){
            conn.commit();
            conn.setAutoCommit(autoCommit);
            return 1;
        }
        conn.rollback();
        conn.setAutoCommit(autoCommit);
        return 0;
    }

    public static int insertTH(Tbth th) throws SQLException{
        boolean autoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);

        PreparedStatement sql = conn.prepareStatement("insert into tb_th (th_id, cg_id, jsr_id, th_yy, th_time) values (?, ?, ?, ?, ?)");

        sql.setString(1, th.getId());
        sql.setString(2, th.getCg_id());
        sql.setString(3, th.getJsr_id());
        sql.setString(4, th.getYy());
        sql.setDate(5, th.getTime());

        Statement sql2 = conn.createStatement();


        int result = sql.executeUpdate();
        int result2  =sql2.executeUpdate("update tb_cg set cg_jd = '已退货' where cg_id = '" + th.getCg_id() + "'");

        if(result > 0 && result2 > 0){
            conn.commit();
            conn.setAutoCommit(autoCommit);
            return 1;
        }
        conn.rollback();
        conn.setAutoCommit(autoCommit);
        return 0;
    }

    public static String getCGMaxId(Date date) throws SQLException {
        SimpleDateFormat pattern = new SimpleDateFormat("MMdd");
        String date_string = pattern.format(date);
        ResultSet set = findForResultSet("select count(*) as count from tb_cg where cg_id like 'CG" + date_string + "__'");
        int count = 100;
        if(set.next()){
            count += set.getInt("count");
        }
        return "CG" + date_string + String.valueOf(count).substring(1);
    }

    public static String getSHMaxId() throws SQLException {
        SimpleDateFormat pattern = new SimpleDateFormat("MMdd");
        String date_string = pattern.format(new java.util.Date());
        ResultSet set = findForResultSet("select count(*) as count from tb_sh where sh_id like 'SH" + date_string + "__'");
        int count = 100;
        if(set.next()){
            count += set.getInt("count");
        }
        return "SH" + date_string + String.valueOf(count).substring(1);
    }

    public static String getTHMaxId() throws SQLException {
        SimpleDateFormat pattern = new SimpleDateFormat("MMdd");
        String date_string = pattern.format(new java.util.Date());
        ResultSet set = findForResultSet("select count(*) as count from tb_th where th_id like 'TH" + date_string + "__'");
        int count = 100;
        if(set.next()){
            count += set.getInt("count");
        }
        return "TH" + date_string + String.valueOf(count).substring(1);
    }
    
    public static void main(String[] args){ }
}
