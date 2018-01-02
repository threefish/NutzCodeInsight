package com.sgaop.project.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2017/12/11  14:13
 * 描述此类：
 */
public class SettingConfigUi {

    public JPanel root;

    private JTable templateTable;
    private JButton btnAddTemplte;
    private JTextField mark;

    public SettingConfigUi() {
        templateTable.setRowHeight(25);
        DefaultTableModel model = new DefaultTableModel(new String[]{"模版前缀", "文件名后缀",}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (row > 2) {
                    return true;
                }
                return false;
            }
        };
        templateTable.getTableHeader().setReorderingAllowed(false);
        templateTable.setModel(model);
        btnAddTemplte.addActionListener(e->model.addRow(new String[]{"",""}));
    }

    public JTable getTemplateTable() {
        return templateTable;
    }
    public JPanel getRootPanel() {

        return root;
    }

    public String getConfigString() {
        return "";
    }
}
