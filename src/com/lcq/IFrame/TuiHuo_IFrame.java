package com.lcq.IFrame;

import com.lcq.MainFrame;
import com.lcq.Mytable;
import com.lcq.dao.Dao;
import com.lcq.dao.model.Tbth;
import com.lcq.utils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class TuiHuo_IFrame extends JInternalFrame {
    private JTextField th_id_field;
    private JTextField cg_id_field;
    private JTextField jsr_id_field;
    private JTextField time_field;
    private JTextField yy_field;

    private JPanel top_panel;
    private JPanel bottom_panel;

    private Mytable table;

    private JButton okButton;
    private JButton cancelButton;

    public JTextField getTh_id_field() throws SQLException {
        if(th_id_field == null){
            th_id_field = new JTextField();
            th_id_field.setEditable(false);
            th_id_field.setText(Dao.getTHMaxId());
        }
        return th_id_field;
    }

    public JTextField getCg_id_field() {
        if(cg_id_field == null){
            cg_id_field = new JTextField(8);
            cg_id_field.setEditable(false);
        }
        return cg_id_field;
    }

    public JTextField getJsr_id_field() {
        if(jsr_id_field == null){
            jsr_id_field = new JTextField();
            jsr_id_field.setEditable(false);
            jsr_id_field.setText(MainFrame.czy_label.getText());
        }
        return jsr_id_field;
    }

    public JTextField getTime_field() {
        if(time_field == null){
            time_field = new JTextField();
            time_field.setEditable(false);
            SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = new java.util.Date();
            time_field.setText(pattern.format(date));
        }
        return time_field;
    }

    public JTextField getYy_field() {
        if(yy_field == null){
            yy_field = new JTextField(25);
            yy_field.setEditable(true);
        }
        return yy_field;
    }

    public JPanel getTop_panel() throws SQLException {
        if(top_panel == null){
            top_panel = new JPanel(new GridBagLayout());
            JLabel th_label = new JLabel("退货单号：");
            JLabel cg_label = new JLabel("采购单号：");
            JLabel jsr_label = new JLabel("经手人：");
            JLabel time_label = new JLabel("日期：");
            JLabel yy_label = new JLabel("退货原因：");
            utils.setupComponet(top_panel, th_label, 0, 0, 0, 0, false);
            utils.setupComponet(top_panel, cg_label, 2, 0, 0, 0, false);
            utils.setupComponet(top_panel, jsr_label, 4, 0, 0, 0, false);
            utils.setupComponet(top_panel, time_label, 0, 1, 0, 0, false);
            utils.setupComponet(top_panel, yy_label, 2, 1, 0, 0, false);

            utils.setupComponet(top_panel, getTh_id_field(), 1, 0, 0, 0, false);
            utils.setupComponet(top_panel, getCg_id_field(), 3, 0, 0, 0, false);
            utils.setupComponet(top_panel, getJsr_id_field(), 5, 0, 0, 0, false);
            utils.setupComponet(top_panel, getTime_field(), 1, 1, 0, 0, false);
            utils.setupComponet(top_panel, getYy_field(), 3, 1, 3, 0, false);
        }
        return top_panel;
    }

    public Mytable getTable() throws SQLException {
        if(table == null){
            String[] header = {"采购单编号", "供应商编号", "经手人", "商品编号", "数量", "日期", "价格", "进度", "结算方式"};
            table = new Mytable();
            table.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            table.setShowGrid(true);
            //table.setEnabled(false);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            ((DefaultTableModel)table.getModel()).setColumnIdentifiers(header);
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int index = table.getSelectedRow();
                    String cg_id = (String)table.getValueAt(index, 0);
                    //将各个文本框置为表格中的相应数据
                    cg_id_field.setText(cg_id);
                }
            });
            updateTable();
        }
        return table;
    }

    public JButton getOkButton() {
        if(okButton == null){
            okButton = new JButton("确定");
            okButton.addActionListener(e->{
                if(cg_id_field == null || cg_id_field.getText().equals("")){
                    JOptionPane.showMessageDialog(this, "请选择一个选购单", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String th_id = th_id_field.getText();
                String cg_id = cg_id_field.getText();
                String jsr_id = jsr_id_field.getText();
                java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
                String th_yy = yy_field.getText();

                Tbth th = new Tbth(th_id, cg_id, jsr_id, th_yy, date);

                try {
                    int result = Dao.insertTH(th);
                    if(result > 0){
                        JOptionPane.showMessageDialog(this, "退货成功");
                        th_id_field.setText(Dao.getTHMaxId());
                        cg_id_field.setText("");
                        String[] header = {"采购单编号", "供应商编号", "经手人", "商品编号", "数量", "日期", "价格", "进度", "结算方式"};
                        table.setModel(new DefaultTableModel());
                        ((DefaultTableModel)table.getModel()).setColumnIdentifiers(header);
                        updateTable();
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "退货失败", "提醒", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            });
        }
        return okButton;
    }

    public JButton getCancelButton() {
        if(cancelButton == null){
            cancelButton = new JButton("取消");
            cancelButton.addActionListener(e->{
                try {
                    this.setClosed(true);
                } catch (PropertyVetoException ex) {
                    ex.printStackTrace();
                }
            });
        }
        return cancelButton;
    }

    public JPanel getBottom_panel() {
        if(bottom_panel == null){
            bottom_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            bottom_panel.add(getOkButton());
            bottom_panel.add(getCancelButton());
        }
        return bottom_panel;
    }

    private void updateTable() throws SQLException {
        ResultSet set  = Dao.findForResultSet("select * from tb_cg where cg_jd = '待采购'");
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        removeAllRow(model);
        while(set.next()){
            String[] arr = {"cg_id", "gys_id", "jsr_id", "hw_id", "cg_sl", "cg_time", "cg_price", "cg_jd", "cg_jsfs"};
            Vector<String> vec = new Vector<>();
            for(String s: arr){
                vec.add(set.getString(s));
            }
            model.addRow(vec);
        }
    }

    private void removeAllRow(DefaultTableModel model){
        for(int i = model.getRowCount() - 1; i >= 0; i--){
            model.removeRow(i);
        }
    }

    public TuiHuo_IFrame() throws SQLException {
        super("退货处理");
        setIconifiable(true);
        setClosable(true);

        Container container = getContentPane();
        container.add(getTop_panel(), BorderLayout.NORTH);

        JScrollPane table_pane = new JScrollPane(getTable());
        container.add(table_pane, BorderLayout.CENTER);
        container.add(getBottom_panel(), BorderLayout.SOUTH);

        setSize(400, 300);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setVisible(true);
    }

    public static void main(String[] args) throws SQLException {
        new TuiHuo_IFrame();
    }
}
