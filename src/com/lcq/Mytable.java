package com.lcq;

import javax.swing.*;

public class Mytable extends JTable {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
