package com.lcq.IFrame;

import javax.swing.*;

import com.lcq.MainFrame;
import com.lcq.dao.Dao;
import com.lcq.dao.model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import com.lcq.utils;

public class QingGou_IFrame extends JInternalFrame {
    private JTextField cg_id_field; //采购单号
    private JComboBox<Tbgys> gys_id_combobox;//供应商面板
    private JTextField jsr_id_field;//经手人
    private JComboBox<Tbhw> hw_combobox;//选择货物

    private JTextField time_field;//时间
    private JComboBox jsfs_combobox;//结算方式

    private JTextField sl_field;//数量
    private JTextField price_field;//商品单价
    private JTextField sum_field;//总价

    private JButton okButton;//确定按钮

    //采购文本框的初始化
    public JTextField getCg_id_field() throws SQLException {
        if(cg_id_field == null){
            cg_id_field = new JTextField();
            //自动生成编号
            cg_id_field.setText(Dao.getCGMaxId(new Date(new java.util.Date().getTime())));
            cg_id_field.setEditable(false);//设置为不可编辑
        }
        return cg_id_field;
    }

    //供应商单选框的初始化
    public JComboBox getGys_id_combobox() throws SQLException{
        if(gys_id_combobox == null) {
            Vector<Tbgys> vec = new Vector<>();
            ResultSet set = Dao.findForResultSet("select * from tb_gys");
            //将数据库中所有供应商信息加入vec中
            while (set.next()){
                String id = set.getString("gys_id");
                String name = set.getString("gys_name");
                int xy = set.getInt("gys_xy");
                String dz = set.getString("gys_dz");

                Tbgys gys = new Tbgys(id, name, xy, dz);
                vec.add(gys);
            }

            gys_id_combobox = new JComboBox<>(vec);
            gys_id_combobox.addItemListener(e-> {
                Tbgys gys = (Tbgys)gys_id_combobox.getSelectedItem();
                if(gys != null && gys.getId() != null){
                    try {
                        ResultSet set2 = Dao.findForResultSet("select * from tb_hw where gys_id='" + gys.getId() + "'");
                        update_hw_combobox(set2);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
        return gys_id_combobox;
    }

    //经手人文本框的初始化
    public JTextField getJsr_id_field() {
        if (jsr_id_field == null){
            jsr_id_field = new JTextField();
            jsr_id_field.setText(MainFrame.czy_label.getText());
            jsr_id_field.setEditable(false);
        }
        return jsr_id_field;
    }

    //日期文本框的初始化
    public JTextField getTime_field() {
        if(time_field == null){
            time_field = new JTextField();
            java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
            time_field.setText(date.toString());
            time_field.setEditable(false);
        }
        return time_field;
    }

    //结算方式的初始化
    public JComboBox getJsfs_combobox() {
        if(jsfs_combobox == null){
            String[] arr = {"现金支付", "移动支付", "银行卡"};
            jsfs_combobox = new JComboBox<>(arr);
        }
        return jsfs_combobox;
    }

    //货物数量的初始化
    public JTextField getSl_field() {
        if(sl_field == null){
            sl_field = new JTextField();
            sl_field.setText("0");
            sl_field.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    int KeyChar = e.getKeyChar();
                    if(KeyChar >= KeyEvent.VK_0 && KeyChar <= KeyEvent.VK_9){
                        //calculateSum();
                    }
                    else{
                        e.consume();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    super.keyPressed(e);
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    calculateSum();
                }
            });
        }
        return sl_field;
    }

    //总价文本框的初始化
    public JTextField getSum_field() {
        if(sum_field == null){
            sum_field = new JTextField();
            sum_field.setText("0");
            sum_field.setEditable(false);
        }
        return sum_field;
    }

    //确认按钮的初始化
    public JButton getOkButton() {
        if(okButton == null){
            okButton = new JButton("确定");
            okButton.addActionListener(e -> {
                //将采购单添加到数据库
                String cg_id = cg_id_field.getText();
                Tbgys gys = (Tbgys)gys_id_combobox.getSelectedItem();
                String gys_id = gys.getId();
                String jsr_name = MainFrame.getCzy_label().getText();
                Tbhw hw = (Tbhw)hw_combobox.getSelectedItem();
                String hw_id = hw.getId();
                int sl = Integer.parseInt(sl_field.getText());
                float price = Float.parseFloat(sum_field.getText());
                java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());

                String jsfs = (String)jsfs_combobox.getSelectedItem();

                if(gys_id == null || gys_id.equals("")){
                    JOptionPane.showMessageDialog(this, "请选择供应商！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(hw_id == null || hw_id.equals("")) {
                    JOptionPane.showMessageDialog(this, "请选择货物！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(sl <= 0){
                    JOptionPane.showMessageDialog(this, "请输入正确的数量！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Tbcg cg = new Tbcg(cg_id, gys_id, jsr_name, hw_id, sl, date, price, "待审核", jsfs);

                try {
                    int result = Dao.insertCG(cg);
                    System.out.println(result);
                    if(result > 0){
                        System.out.println("插入成功！");
                        JOptionPane.showMessageDialog(this, "插入成功！");
                        resetField();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            });
        }
        return okButton;
    }

    public JComboBox<Tbhw> getHw_combobox() {
        if(hw_combobox == null){
            hw_combobox = new JComboBox<Tbhw>();
            hw_combobox.addItem(new Tbhw());
            hw_combobox.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    //设置各个属性
                    Tbhw hw = (Tbhw)hw_combobox.getSelectedItem();
                    if(hw != null && hw.getId() != null){
                        getPrice_field().setText(String.valueOf(hw.getPrice()));
                        calculateSum();
                    }
                }
            });
        }
        return hw_combobox;
    }

    public JTextField getPrice_field() {
        if(price_field == null){
            price_field = new JTextField();
            price_field.setText("0");
            price_field.setEditable(false);
        }
        return price_field;
    }

    public void update_hw_combobox(ResultSet set) throws SQLException {
        hw_combobox.removeAllItems();
        while(set.next()){
            String id = set.getString("hw_id");
            String name = set.getString("hw_name");
            int sl = set.getInt("hw_sl");
            float price = set.getFloat("price");
            Tbhw hw = new Tbhw(id, name, sl, price);

            hw_combobox.addItem(hw);
        }
    }

    public void calculateSum(){
        try{
            int sl = Integer.parseInt(sl_field.getText());
            float price = Float.parseFloat(price_field.getText());
            float sum = sl * price;
            sum_field.setText(String.valueOf(sum));
        }
        catch (NumberFormatException e){
            System.out.println("出错了");
        }
    }

    public QingGou_IFrame() throws SQLException {
        super("请购单");
        setIconifiable(true);
        setClosable(true);
        Container container = getContentPane();
        container.setLayout(new GridBagLayout());

        JLabel cg_label = new JLabel("采购编号：");
        JLabel gys_label = new JLabel("供应商：");
        JLabel jsr_label = new JLabel("经手人：");

        JLabel hw_label = new JLabel("货物：");
        JLabel time_label = new JLabel("时间：");
        JLabel price_label = new JLabel("价格：");

        JLabel sl_label = new JLabel("数量：");
        JLabel sum_label = new JLabel("总价：");
        JLabel jsfs_label = new JLabel("结算方式：");


        utils.setupComponet(container, cg_label, 0, 0, 0, 0, false);
        utils.setupComponet(container, gys_label, 2, 0, 0, 0, false);
        utils.setupComponet(container, jsr_label, 4, 0, 0, 0, false);
        utils.setupComponet(container, hw_label, 0, 1, 0, 0, false);
        utils.setupComponet(container, sl_label, 2, 1, 0, 0, false);
        utils.setupComponet(container, price_label, 4, 1, 0, 0, false);
        utils.setupComponet(container, time_label, 0, 2, 0, 0, false);
        utils.setupComponet(container, sum_label, 2, 2, 0, 0, false);
        utils.setupComponet(container, jsfs_label, 4, 2, 0, 0, false);

        utils.setupComponet(container, getCg_id_field(), 1, 0, 0, 0, true);
        utils.setupComponet(container, getGys_id_combobox(), 3, 0, 0, 0, true);
        utils.setupComponet(container, getJsr_id_field(), 5, 0, 0, 0, true);
        utils.setupComponet(container, getHw_combobox(), 1, 1, 0, 0, true);
        utils.setupComponet(container, getSl_field(), 3, 1, 0, 0, true);
        utils.setupComponet(container, getPrice_field(), 5, 1, 0, 0, true);
        utils.setupComponet(container, getTime_field(), 1, 2, 0, 0, true);
        utils.setupComponet(container, getSum_field(), 3, 2, 0, 0, true);
        utils.setupComponet(container, getJsfs_combobox(), 5, 2, 0, 0, true);

        utils.setupComponet(container, getOkButton(), 2, 3, 2, 0, false);

        setSize(530, 260);
        setVisible(true);
    }

    private void resetField() throws SQLException {
        sl_field.setText("0");
        sum_field.setText("0");
        cg_id_field.setText(Dao.getCGMaxId(new java.sql.Date(new java.util.Date().getTime())));
    }

    public static void main(String[] args) {
        try {
            new QingGou_IFrame();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
