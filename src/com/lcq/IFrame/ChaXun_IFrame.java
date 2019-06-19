package com.lcq.IFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.lcq.Mytable;
import com.lcq.dao.Dao;
import com.lcq.utils;

public class ChaXun_IFrame extends JInternalFrame {
    private JButton hw_button;
    private JButton cg_button;
    private JButton gys_button;
    private JButton th_button;
    private JButton sh_button;

    private Mytable table;

    private JPanel topPane;

    public ChaXun_IFrame(){
        super("信息汇总");
        setIconifiable(true);
        setClosable(true);
        Container container = getContentPane();

        container.add(getTopPane(), BorderLayout.NORTH);
        JScrollPane table_pane = new JScrollPane(getTable());
        container.add(table_pane, BorderLayout.CENTER);

        setSize(600, 400);
        setVisible(true);
    }

    public JButton getHw_button() {
        if(hw_button == null){
            hw_button = new JButton("查询货物");
            hw_button.addActionListener(e->{
                //查询货物信息并显示到table上
                String[] header = {"货物编号", "货物名", "货物数量", "价格"};
                DefaultTableModel model = (DefaultTableModel)table.getModel();
                removeAllRow(model);
                model.setColumnIdentifiers(header);
                ResultSet set  = null;
                try {
                    set = Dao.findForResultSet("select * from tb_hw");
                    String[] arr = {"hw_id", "hw_name", "hw_sl", "price"};
                    while(set.next()){
                        model.addRow(getMessage(arr, set));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        }
        return hw_button;
    }

    public JButton getCg_button() {
        if(cg_button == null){
            cg_button = new JButton("采购单查询");
            cg_button.addActionListener(e->{
                //查询采购信息
                String[] header = {"采购表编号", "供应商编号", "经手人", "货物编号", "采购数量", "采购日期", "采购价格", "采购进度", "结算方式"};
                DefaultTableModel model = (DefaultTableModel)table.getModel();
                removeAllRow(model);
                model.setColumnIdentifiers(header);
                ResultSet set  = null;
                try {
                    set = Dao.findForResultSet("select * from tb_cg");
                    String[] arr = {"cg_id", "gys_id", "jsr_id", "hw_id", "cg_sl", "cg_time", "cg_price", "cg_jd", "cg_jsfs"};
                    while(set.next()){
                        model.addRow(getMessage(arr, set));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        }
        return cg_button;
    }

    public JButton getGys_button() {
        if(gys_button == null){
            gys_button = new JButton("供应商信息查询");
            gys_button.addActionListener(e->{
                //查询供应商信息
                String[] header = {"供应商编号", "名称", "信誉", "地址"};
                DefaultTableModel model = (DefaultTableModel)table.getModel();
                removeAllRow(model);
                model.setColumnIdentifiers(header);
                ResultSet set  = null;
                try {
                    set = Dao.findForResultSet("select * from tb_gys");
                    String[] arr = {"gys_id", "gys_name", "gys_xy", "gys_dz"};
                    while(set.next()){
                        model.addRow(getMessage(arr, set));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        }
        return gys_button;
    }

    public JButton getTh_button() {
        if(th_button == null){
            th_button = new JButton("退货单查询");
            th_button.addActionListener(e->{
                //查询退货单信息
                String[] header = {"退货单编号", "采购单编号", "经手人", "退货原因", "退货时间"};
                DefaultTableModel model = (DefaultTableModel)table.getModel();
                removeAllRow(model);
                model.setColumnIdentifiers(header);
                ResultSet set  = null;
                try {
                    set = Dao.findForResultSet("select * from tb_th");
                    String[] arr = {"th_id", "cg_id", "jsr_id", "th_yy", "th_time"};
                    while(set.next()){
                        model.addRow(getMessage(arr, set));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        }
        return th_button;
    }

    public JButton getSh_button() {
        if(sh_button == null){
            sh_button = new JButton("收货单查询");
            sh_button.addActionListener(e->{
                //查询收货单信息
                String[] header = {"收货单编号", "采购单编号", "经手人", "收货时间", "收货结论"};
                DefaultTableModel model = (DefaultTableModel)table.getModel();
                removeAllRow(model);
                model.setColumnIdentifiers(header);
                ResultSet set  = null;
                try {
                    set = Dao.findForResultSet("select * from tb_sh");
                    String[] arr = {"sh_id", "cg_id", "jsr_id", "sh_time", "sh_jl"};
                    while(set.next()){
                        model.addRow(getMessage(arr, set));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        }
        return sh_button;
    }

    public Mytable getTable() {
        if(table == null){
            table = new Mytable();
            //table.setEnabled(false);
        }
        return table;
    }

    public JPanel getTopPane(){
        if(topPane == null){
            topPane = new JPanel(new GridBagLayout());
            utils.setupComponet(topPane, getCg_button(), 0, 0, 0, 0, false);
            utils.setupComponet(topPane, getGys_button(), 1, 0, 0, 0, false);
            utils.setupComponet(topPane, getHw_button(), 2, 0, 0, 0, false);
            utils.setupComponet(topPane, getSh_button(), 0, 1, 0, 0, false);
            utils.setupComponet(topPane, getTh_button(), 1, 1, 0, 0, false);
        }
        return topPane;
    }

    private Vector<String> getMessage(String[] arr, ResultSet set) throws SQLException{
        Vector<String> vec = new Vector<>();
        for(String s: arr){
            vec.add(set.getString(s));
        }
        return vec;
    }

    private void removeAllRow(DefaultTableModel model){
        for(int i = model.getRowCount() - 1; i >= 0; i--){
            model.removeRow(i);
        }
    }

    public static void main(String[] args) {
        new ChaXun_IFrame();
    }
}
