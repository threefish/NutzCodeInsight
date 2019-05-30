package com.sgaop.idea.project.ui;

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
 * <p>
 * 创建时间: 2017/12/11  14:13
 */
public class SettingConfigUi {

    public JPanel root;
    private JTable templateTable;
    private JButton btnAddTemplte;
    private JTextField layoutRegular;
    private JTextField includeRegular;
    private JTextField i18nRegular;
    private JLabel issues;
    private JTextField i18nKeyRegular;

    public SettingConfigUi() {
        SwingUtils.addMouseListenerAndSetUrl(issues, "https://github.com/threefish/NutzCodeInsight/issues");
        templateTable.setRowHeight(25);
        DefaultTableModel model = new DefaultTableModel(new String[]{"模版前缀", "文件名后缀", "可编辑"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return row > 1;
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

    public JTextField getI18nKeyRegular() {
        return i18nKeyRegular;
    }

    public JTextField getLayoutRegular() {
        return layoutRegular;
    }


    public JTextField getIncludeRegular() {
        return includeRegular;
    }


    public JTextField getI18nRegular() {
        return i18nRegular;
    }

    public JTable getTemplateTable() {
        return templateTable;
    }

    public JPanel getRootPanel() {
        return root;
    }
}
