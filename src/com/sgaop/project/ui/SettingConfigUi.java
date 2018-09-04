package com.sgaop.project.ui;

import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com

 * 创建时间: 2017/12/11  14:13

 */
public class SettingConfigUi {

    public JPanel root;
    private JTable templateTable;
    private JButton btnAddTemplte;
    private JTextField mark;
    private JTextField layoutRegular;
    private JTextField includeRegular;

    public SettingConfigUi() {
        templateTable.setRowHeight(25);
        DefaultTableModel model = new DefaultTableModel(new String[]{"模版前缀", "文件名后缀", "可编辑"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (row > 1) {
                    return true;
                }
                return false;
            }
        };
        model.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 0) {
                String newvalue = model.getValueAt(e.getLastRow(), 0).toString().trim();
                Set<String> names = new HashSet<>();
                for (int i = 0, l = model.getRowCount(); i < l; i++) {
                    if (i == e.getLastRow()) {
                        continue;
                    }
                    names.add(model.getValueAt(i, 0).toString());
                }
                if (names.contains(newvalue)) {
                    Messages.showErrorDialog("模版前缀已经存在，请使用其他名称！", "错误提示");
                    model.setValueAt("", e.getLastRow(), 0);
                }
            }
        });
        templateTable.getTableHeader().setReorderingAllowed(false);
        templateTable.setModel(model);
        btnAddTemplte.addActionListener(e -> {
            for (int i = 0, l = model.getRowCount(); i < l; i++) {
                String val = model.getValueAt(i, 0).toString().trim();
                if (val.length() == 0) {
                    Messages.showErrorDialog("请填写完整后再添加！", "错误提示");
                    return;
                }
            }
            model.addRow(new String[]{"", "", ""});
        });
    }

    public JTextField getLayoutRegular() {
        return layoutRegular;
    }

    public void setLayoutRegular(JTextField layoutRegular) {
        this.layoutRegular = layoutRegular;
    }

    public JTextField getIncludeRegular() {
        return includeRegular;
    }

    public void setIncludeRegular(JTextField includeRegular) {
        this.includeRegular = includeRegular;
    }

    public JTable getTemplateTable() {
        return templateTable;
    }

    public JPanel getRootPanel() {
        return root;
    }
}
