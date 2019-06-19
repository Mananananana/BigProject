package com.lcq;

import com.lcq.IFrame.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ToolBar extends JToolBar {
    private JMenuItem cgsq_item; //采购申请模块
    private JMenuItem cgsh_item; //采购审核模块
    private JMenuItem slyw_item; //收料业务模块
    private JMenuItem thyw_item; //退货业务模块
    private JMenuItem kcpd_item; //库存盘点模块
    private JMenuItem cgcx_item;

    private int nextFrameX, nextFrameY;

    private Map<JMenuItem, JInternalFrame> iFrames;

    private JDesktopPane desktopPanel = null;

    private JLabel stateLabel = null;

    public ToolBar(){
        super();
        initialize();
    }

    public ToolBar(JDesktopPane desktopPanel, JLabel stateLabel){
        super();
        this.iFrames = new HashMap<JMenuItem, JInternalFrame>();
        this.desktopPanel = desktopPanel;
        this.stateLabel = stateLabel;
        initialize();
    }

    public void initialize(){
        setSize(new Dimension(600, 24));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        add(createToolButton(getCgsq_item()));
        add(createToolButton(getCgsh_item()));
        add(createToolButton(getSlyw_item()));
        add(createToolButton(getThyw_item()));
        add(createToolButton(getKcpd_item()));
        add(createToolButton(getCgcx_item()));
    }

    public JMenuItem getCgsq_item() {
        if(cgsq_item == null){
            cgsq_item = new JMenuItem();
            cgsq_item.setText("采购申请");
            cgsq_item.addActionListener(e->{
                createIFrame(cgsq_item, QingGou_IFrame.class);
            });
        }
        return cgsq_item;
    }

    public JMenuItem getCgsh_item() {
        if(cgsh_item == null){
            cgsh_item = new JMenuItem();
            cgsh_item.setText("采购审核");
            cgsh_item.addActionListener(e->{
                createIFrame(cgsh_item, ShenHe_IFrame.class);
            });
        }
        return cgsh_item;
    }

    public JMenuItem getSlyw_item() {
        if(slyw_item == null){
            slyw_item = new JMenuItem();
            slyw_item.setText("收料业务");
            slyw_item.addActionListener(e->{
                createIFrame(slyw_item, ShouHuo_IFrame.class);
            });
        }
        return slyw_item;
    }

    public JMenuItem getThyw_item() {
        if(thyw_item == null){
            thyw_item = new JMenuItem();
            thyw_item.setText("退货业务");
            thyw_item.addActionListener(e->{
                createIFrame(thyw_item, TuiHuo_IFrame.class);
            });
        }
        return thyw_item;
    }

    public JMenuItem getKcpd_item() {
        if(kcpd_item == null){
            kcpd_item = new JMenuItem();
            kcpd_item.setText("库存盘点");
            kcpd_item.addActionListener(e->{
                createIFrame(kcpd_item, ChaXun_IFrame.class);
            });
        }
        return kcpd_item;
    }

    public JMenuItem getCgcx_item() {
        if(cgcx_item == null){
            cgcx_item = new JMenuItem();
            cgcx_item.setText("采购查询");
            cgcx_item.addActionListener(e->{
                createIFrame(cgcx_item, ChaXun2_IFrame.class);
            });
        }
        return cgcx_item;
    }

    private JButton createToolButton(final JMenuItem item){
        JButton button = new JButton();
        button.setText(item.getText());
        button.setToolTipText(item.getText());
        button.setFocusable(false);
        button.addActionListener(e->{
            item.doClick();
        });
        return button;
    }

    private JInternalFrame createIFrame(JMenuItem item, Class clazz) {
        Constructor constructor = clazz.getConstructors()[0];
        JInternalFrame iFrame = iFrames.get(item);
        try {
            if (iFrame == null || iFrame.isClosed()) {
                iFrame = (JInternalFrame) constructor.newInstance(new Object[] {});
                iFrames.put(item, iFrame);
                //iFrame.setFrameIcon(item.getIcon());
                iFrame.setLocation(nextFrameX, nextFrameY);
                int frameH = iFrame.getPreferredSize().height;
                int panelH = iFrame.getContentPane().getPreferredSize().height;
                int fSpacing = frameH - panelH;
                nextFrameX += fSpacing;
                nextFrameY += fSpacing;
                if (nextFrameX + iFrame.getWidth() > desktopPanel.getWidth())
                    nextFrameX = 0;
                if (nextFrameY + iFrame.getHeight() > desktopPanel.getHeight())
                    nextFrameY = 0;
                desktopPanel.add(iFrame);
                iFrame.setResizable(false);
                iFrame.setMaximizable(false);
                iFrame.setVisible(true);
            }
            iFrame.setSelected(true);
            stateLabel.setText(iFrame.getTitle());
            iFrame.addInternalFrameListener(new InternalFrameAdapter() {
                public void internalFrameActivated(InternalFrameEvent e) {
                    super.internalFrameActivated(e);
                    JInternalFrame frame = e.getInternalFrame();
                    stateLabel.setText(frame.getTitle());
                    try {
                        Method method = clazz.getDeclaredMethod("updateTable");
                        method.setAccessible(true);
                        method.invoke(frame);
                    } catch (Exception ex){
                        //ex.printStackTrace();
                    }
                }

                public void internalFrameDeactivated(InternalFrameEvent e) {
                    stateLabel.setText("没有选择窗体");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iFrame;
    }
}
