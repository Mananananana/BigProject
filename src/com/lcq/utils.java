package com.lcq;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class utils {
    public static void setupComponet(Container container, JComponent component, int gridx, int gridy, int gridwidth, int ipadx, boolean fill) {
        GridBagConstraints gridBagConstrains = new GridBagConstraints();
        gridBagConstrains.gridx = gridx;
        gridBagConstrains.gridy = gridy;
        if (gridwidth > 1)
            gridBagConstrains.gridwidth = gridwidth;
        if (ipadx > 0)
            gridBagConstrains.ipadx = ipadx;
        gridBagConstrains.insets = new Insets(5, 1, 3, 1);
        if (fill)
            gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
        container.add(component, gridBagConstrains);
    }

    public static Vector<String> getMessage(String[] arr, ResultSet set) throws SQLException {
        Vector<String> vec = new Vector<>();
        for(String s: arr){
            vec.add(set.getString(s));
        }
        return vec;
    }
}
