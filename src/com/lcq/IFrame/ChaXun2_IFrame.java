package com.lcq.IFrame;

import com.lcq.dao.Dao;
import com.lcq.dao.model.Tbcg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChaXun2_IFrame extends JInternalFrame {
    private JTextField cx_field;
    private JButton okButton;

    private JTextArea info;

    private JButton prevButton;
    private JButton nextButton;
    private Tbcg cg;

    private JPanel topPanel;
    private JScrollPane infoPanel;
    private JPanel bottomPanel;

    public JTextField getCx_field() {
        if(cx_field == null){
            cx_field = new JTextField(22);
            cx_field.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    if(e.getKeyChar() == '\n'){
                        okButton.doClick();
                    }
                }
            });
        }
        return cx_field;
    }

    public JButton getOkButton() {
        if(okButton == null){
            okButton = new JButton("查询");
            okButton.addActionListener(e->{
                try {
                    ResultSet set = Dao.findForResultSet("select * from tb_cg where cg_id = '" + cx_field.getText() + "'");
                    if(set.next()){
                        String cg_id = set.getString("cg_id");
                        String gys_id = set.getString("gys_id");
                        String jsr_id = set.getString("jsr_id");
                        String hw_id = set.getString("hw_id");
                        int cg_sl = set.getInt("cg_sl");
                        Date cg_time = set.getDate("cg_time");
                        float cg_price = set.getFloat("cg_price");
                        String cg_jd = set.getString("cg_jd");
                        String cg_jsfs = set.getString("cg_jsfs");
                        this.cg = new Tbcg(cg_id, gys_id, jsr_id, hw_id, cg_sl, cg_time, cg_price, cg_jd, cg_jsfs);
                        prevButton.setEnabled(false);
                        nextButton.setEnabled(true);
                        show_cg();
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "无此采购单");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        }
        return okButton;
    }

    public JTextArea getInfo() {
        if(info == null){
            info = new JTextArea();
            info.setEnabled(false);
        }
        return info;
    }

    public JButton getPrevButton() {
        if(prevButton == null){
            prevButton = new JButton("上一级");
            prevButton.addActionListener(e->{
                show_cg();
                nextButton.setEnabled(true);
                prevButton.setEnabled(false);
            });
            prevButton.setEnabled(false);
        }
        return prevButton;
    }

    public JButton getNextButton() {
        if(nextButton == null){
            nextButton = new JButton("下一级");
            nextButton.addActionListener(e->{
                try {
                    if(cg.getJd().equals("已收货")){
                        show_sh();
                    }
                    else if(cg.getJd().equals("已退货")){
                        show_th();
                    }
                } catch (SQLException ex) {
                ex.printStackTrace();
                }
                nextButton.setEnabled(false);
                prevButton.setEnabled(true);
            });
            nextButton.setEnabled(false);
        }
        return nextButton;
    }

    private void show_cg(){
        if(this.cg != null && this.cg.getId() != null){
            info.setText("");
            info.append("采购编号：" + cg.getId() + "\n");
            info.append("供应商编号：" + cg.getGys_id() + "\n");
            info.append("经手人：" + cg.getJsr_id() + "\n");
            info.append("货物编号：" + cg.getHw_id() + "\n");
            info.append("采购数量：" + cg.getSl() + "\n");
            info.append("采购时间：" + cg.getTime() + "\n");
            info.append("采购价格：" + cg.getPrice() + "\n");
            info.append("采购进度：" + cg.getJd() + "\n");
            info.append("结算方式：" + cg.getJsfs() + "\n");
        }
    }

    private void show_th() throws SQLException {
        if(this.cg != null && this.cg.getId() != null){
            ResultSet set = Dao.findForResultSet("select * from tb_th where cg_id = '" + cg.getId() + "'");
            if(set.next()){
                info.setText("");
                info.append("退货单编号：" + set.getString("th_id") + "\n");
                info.append("采购单编号：" + set.getString("cg_id") + "\n");
                info.append("经手人：" + set.getString("jsr_id") + "\n");
                info.append("退货原因：" + set.getString("th_yy") + "\n");
                info.append("退货时间：" + set.getString("th_time") + "\n");
            }
            else{
                System.out.println("没有退货");
            }
        }
    }

    private void show_sh() throws SQLException {
        if(cg != null && cg.getId() != null){
            ResultSet set = Dao.findForResultSet("select * from tb_sh where cg_id = '" + cg.getId() + "'");
            if(set.next()){
                info.setText("");
                info.append("收货单编号：" + set.getString("sh_id") + "\n");
                info.append("采购单编号：" + set.getString("cg_id") + "\n");
                info.append("经手人：" + set.getString("jsr_id") + "\n");
                info.append("收货时间：" + set.getString("sh_time") + "\n");
                info.append("收货结论：" + set.getString("sh_jl") + "\n");
            }
            else{
                System.out.println("没有收货");
            }
        }
    }

    public JPanel getTopPanel() {
        if(topPanel == null){
            topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            topPanel.add(getCx_field());
            topPanel.add(getOkButton());
        }
        return topPanel;
    }

    public JScrollPane getInfoPanel() {
        if(infoPanel == null){
            infoPanel = new JScrollPane(getInfo());
            infoPanel.setBorder(BorderFactory.createEtchedBorder());
        }
        return infoPanel;
    }

    public JPanel getBottomPanel() {
        if(bottomPanel == null){
            bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            bottomPanel.add(getPrevButton());
            bottomPanel.add(getNextButton());
        }
        return bottomPanel;
    }

    public ChaXun2_IFrame(){
        setTitle("采购查询");
        setIconifiable(true);
        setClosable(true);
        Container container = getContentPane();

        container.add(getTopPanel(), BorderLayout.NORTH);
        container.add(getInfoPanel(), BorderLayout.CENTER);
        container.add(getBottomPanel(), BorderLayout.SOUTH);

        setSize(230, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ChaXun2_IFrame();
    }
}
