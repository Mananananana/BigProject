package com.lcq.IFrame;

import com.lcq.Mytable;
import com.lcq.dao.Dao;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.lcq.utils;

public class ShenHe_IFrame extends JInternalFrame {
    private Mytable table;
    private JButton shButton;

    public Mytable getTable() {
        if(table == null){
            table = new Mytable();
            String[] header = {"采购表编号", "供应商编号", "经手人", "货物编号", "采购数量", "采购日期", "采购价格", "采购进度", "接收方式"};
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            model.setColumnIdentifiers(header);
            ResultSet set  = null;
            try {
                set = Dao.findForResultSet("select * from tb_cg where cg_jd='待审核'");
                String[] arr = {"cg_id", "gys_id", "jsr_id", "hw_id", "cg_sl", "cg_time", "cg_price", "cg_jd", "cg_jsfs"};
                while(set.next()){
                    model.addRow(utils.getMessage(arr, set));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return table;
    }

    public JButton getShButton() {
        if(shButton == null){
            shButton = new JButton("通过");
            shButton.addActionListener(e->{
                //将表格中对应的请购状态改为待采购
                int index = table.getSelectedRow();
                if(index == -1){
                    JOptionPane.showMessageDialog(this, "请选择一个采购单", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String cg_id = (String)table.getValueAt(index, 0);
                try {
                    if(cg_id != null && !cg_id.equals("")){
                        Statement state = Dao.conn.createStatement();
                        int result = state.executeUpdate("update tb_cg set cg_jd='待采购' where cg_id = '" + cg_id + "'");
                        if (result > 0){
                            JOptionPane.showMessageDialog(this, "验收成功！");
                            updateTable();
                        }
                        else{
                            JOptionPane.showMessageDialog(this, "验收失败", "错误", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        }
        return shButton;
    }

    private void updateTable(){
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        removeAllRow(model);
        ResultSet set  = null;
        try {
            set = Dao.findForResultSet("select * from tb_cg where cg_jd='待审核'");
            String[] arr = {"cg_id", "gys_id", "jsr_id", "hw_id", "cg_sl", "cg_time", "cg_price", "cg_jd", "cg_jsfs"};
            while(set.next()){
                model.addRow(utils.getMessage(arr, set));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void removeAllRow(DefaultTableModel model){
        for(int i = model.getRowCount() - 1; i >= 0; i--){
            model.removeRow(i);
        }
    }

    public ShenHe_IFrame(){
        super("审核请购单");
        setIconifiable(true);
        setClosable(true);

        Container container = getContentPane();

        JScrollPane table_pane = new JScrollPane(getTable());
        container.add(table_pane, BorderLayout.CENTER);

        JPanel bottom_pane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom_pane.add(getShButton());
        container.add(bottom_pane, BorderLayout.SOUTH);

        setSize(700, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ShenHe_IFrame();
    }
}
