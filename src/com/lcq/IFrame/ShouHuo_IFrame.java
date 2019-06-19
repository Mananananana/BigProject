package com.lcq.IFrame;

import com.lcq.MainFrame;
import com.lcq.Mytable;
import com.lcq.dao.Dao;
import com.lcq.dao.model.Tbsh;

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


public class ShouHuo_IFrame extends JInternalFrame {
    private JTextField sh_id_field;
    private JTextField cg_id_field;
    private JTextField jsr_field;
    private JTextField time_field;
    private JComboBox<String> jl_field;

    private Mytable table;

    private JButton okButton;
    private JButton cancelButton;

    private JScrollPane tablePane;
    private JPanel topPane;
    private JPanel bottomPane;

    public JTextField getSh_id_field() throws SQLException {
        if(sh_id_field == null){
            sh_id_field = new JTextField();
            sh_id_field.setText(Dao.getSHMaxId());
            sh_id_field.setEditable(false);
        }
        return sh_id_field;
    }

    public JTextField getCg_id_field() {
        if(cg_id_field == null){
            cg_id_field = new JTextField();
            cg_id_field.setEditable(false);
        }
        return cg_id_field;
    }

    public JTextField getJsr_field() {
        if(jsr_field == null){
            jsr_field = new JTextField();
            jsr_field.setText(MainFrame.czy_label.getText());
            jsr_field.setEditable(false);
        }
        return jsr_field;
    }

    public JTextField getTime_field() {
        if(time_field == null){
            time_field = new JTextField();
            SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");
            String date_string = pattern.format(new java.util.Date());
            time_field.setText(date_string);
            time_field.setEditable(false);
        }
        return time_field;
    }

    public JComboBox<String> getJl_field() {
        if(jl_field == null){
            String[] arr = {"通过"};
            jl_field = new JComboBox<>(arr);
        }
        return jl_field;
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
                //将各个文本框中的数据加入到数据库
                if (cg_id_field == null || cg_id_field.getText().equals("")){
                    JOptionPane.showMessageDialog(this, "请选择一个请购单！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String sh_id = sh_id_field.getText();
                String cg_id = cg_id_field.getText();
                String jsr_id = jsr_field.getText();
                java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
                String jl = (String)jl_field.getSelectedItem();
                Tbsh sh = new Tbsh(sh_id, cg_id, jsr_id, date, jl);
                try {
                    int result = Dao.insertSH(sh);
                    if(result > 0){
                        JOptionPane.showMessageDialog(this, "收货成功");
                        table.setModel(new DefaultTableModel());
                        String[] header = {"采购单编号", "供应商编号", "经手人", "商品编号", "数量", "日期", "价格", "进度", "结算方式"};
                        ((DefaultTableModel)table.getModel()).setColumnIdentifiers(header);
                        updateTable();
                        sh_id_field.setText(Dao.getSHMaxId());
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "插入失败", "提示", JOptionPane.ERROR_MESSAGE);
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
                //将各种文本框清零并将窗口关闭
                //setVisible(false);
                try {
                    this.setClosed(true);
                } catch (PropertyVetoException ex) {
                    ex.printStackTrace();
                }
            });
        }
        return cancelButton;
    }

    public JScrollPane getTablePane() throws SQLException {
        if(tablePane == null){
            tablePane = new JScrollPane(getTable());
        }
        return tablePane;
    }

    public JPanel getTopPane() throws SQLException {
        if(topPane == null){
            topPane = new JPanel(new GridBagLayout());

            JLabel sh_label = new JLabel("收货单编号:");
            JLabel cg_label = new JLabel("采购单编号:");
            JLabel jsr_label = new JLabel("经手人:");
            JLabel time_label = new JLabel("日期:");
            JLabel jl_label = new JLabel("收货结论:");
            setupComponet(sh_label, 0, 0, 0, 0, GridBagConstraints.BOTH);
            setupComponet(cg_label, 3, 0, 0, 0, GridBagConstraints.BOTH);
            setupComponet(jsr_label, 6, 0, 0,0, GridBagConstraints.BOTH);
            setupComponet(time_label, 0, 1, 0,0, GridBagConstraints.BOTH);
            setupComponet(jl_label, 3, 1, 0,0, GridBagConstraints.BOTH);

            setupComponet(getSh_id_field(), 1, 0, 2,0, GridBagConstraints.BOTH);
            setupComponet(getCg_id_field(), 4, 0, 2,0, GridBagConstraints.BOTH);
            setupComponet(getJsr_field(), 7, 0, 2,0, GridBagConstraints.BOTH);
            setupComponet(getTime_field(), 1, 1, 2,0, GridBagConstraints.BOTH);
            setupComponet(getJl_field(), 4, 1, 2,0, GridBagConstraints.BOTH);
        }
        return topPane;
    }

    public JPanel getBottomPane() {
        if(bottomPane == null){
            bottomPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
            bottomPane.add(getOkButton());
            bottomPane.add(getCancelButton());
        }
        return bottomPane;
    }

    public ShouHuo_IFrame() throws SQLException {
        super("收货单");
        setIconifiable(true);
        setClosable(true);
        Container container = getContentPane();

        container.add(getTopPane(), BorderLayout.NORTH);
        container.add(getTablePane(), BorderLayout.CENTER);
        container.add(getBottomPane(), BorderLayout.SOUTH);

        setSize(400, 300);
        setVisible(true);
    }

    private void setupComponet(JComponent component, int gridx, int gridy, int gridwidth, int ipadx, int fill) {
        GridBagConstraints gridBagConstrains = new GridBagConstraints();
        gridBagConstrains.gridx = gridx;
        gridBagConstrains.gridy = gridy;
        if (ipadx > 0)
            gridBagConstrains.ipadx = ipadx;
        if (gridwidth > 1){
            gridBagConstrains.gridwidth = gridwidth;
        }
        gridBagConstrains.insets = new Insets(5, 1, 3, 1);
        gridBagConstrains.fill = fill;
        topPane.add(component, gridBagConstrains);
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

    public static void main(String[] args) {
        try {
            new ShouHuo_IFrame();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
