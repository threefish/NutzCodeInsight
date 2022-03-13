package io.github.threefish.idea.linemarker.navigation.ui;

import com.intellij.openapi.ui.ComboBoxWithWidePopup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2019/5/25
 */
public class OkJsonFastEditor extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable jsonDataBooleanTable;
    private JTable jsonDataTextTable;
    private JTable jsonDataDateTable;

    public OkJsonFastEditor(JsonFormat jsonFormat, OkJsonOnOkAction okJsonOnOkAction) {
        setContentPane(contentPane);
        setModal(true);
        int w = 500, h = 500;
        int x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (w / 2));
        int y = 200;
        setBounds(x, y, w, h);
        setTitle("修改JSON格式化");
        jsonDataTextTable.setRowHeight(25);
        jsonDataBooleanTable.setRowHeight(25);
        jsonDataDateTable.setRowHeight(25);
        jsonDataTextTable.getTableHeader().setReorderingAllowed(false);
        jsonDataDateTable.getTableHeader().setReorderingAllowed(false);
        jsonDataBooleanTable.getTableHeader().setReorderingAllowed(false);
        initTextTable(jsonFormat);
        initDateTable(jsonFormat);
        initBooleanTable(jsonFormat);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK(okJsonOnOkAction));

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void initDateTable(JsonFormat jsonFormat) {
        ComboBoxWithWidePopup dates = new ComboBoxWithWidePopup(new Object[]{
                "",
                "yyyy-MM-dd",
                "yyyy-MM-dd HH:mm",
                "yyyy-MM-dd HH:mm:ss",
                "yyyy年MM月",
                "yyyy年MM月dd日",
                "yyyy年MM月dd日 HH时mm分ss秒",
                "yyyy年MM月dd日 HH:mm",
                "yyyy/MM/dd",
                "yyyy/MM/dd HH:mm",
                "yyyy/MM/dd HH:mm:ss",
                "yyyyMMdd",
                "HH时mm分ss秒",
        });
        DefaultTableModel dateTableModel = new DefaultTableModel(new Object[]{"日期", "value"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }
        };
        dateTableModel.addRow(new Object[]{"dateFormat", jsonFormat.getDateFormat()});
        jsonDataDateTable.setModel(dateTableModel);
        jsonDataDateTable.getColumn("value").setCellEditor(new DefaultCellEditor(dates));

    }

    private void initBooleanTable(JsonFormat jsonFormat) {
        ComboBoxWithWidePopup trueFalse = new ComboBoxWithWidePopup(new Object[]{"", true, false});
        DefaultTableModel booleanTableModel = new DefaultTableModel(new Object[]{"boolean", "value"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }
        };
        booleanTableModel.addRow(new Object[]{"ignoreNull", jsonFormat.getIgnoreNull()});
        booleanTableModel.addRow(new Object[]{"compact", jsonFormat.getCompact()});
        booleanTableModel.addRow(new Object[]{"nullAsEmtry", jsonFormat.getNullAsEmtry()});
        booleanTableModel.addRow(new Object[]{"nullListAsEmpty", jsonFormat.getNullListAsEmpty()});
        booleanTableModel.addRow(new Object[]{"nullStringAsEmpty", jsonFormat.getNullStringAsEmpty()});
        booleanTableModel.addRow(new Object[]{"nullBooleanAsFalse", jsonFormat.getNullBooleanAsFalse()});
        booleanTableModel.addRow(new Object[]{"nullNumberAsZero", jsonFormat.getNullNumberAsZero()});
        booleanTableModel.addRow(new Object[]{"quoteName", jsonFormat.getQuoteName()});
        booleanTableModel.addRow(new Object[]{"ignoreJsonShape", jsonFormat.getIgnoreJsonShape()});
        booleanTableModel.addRow(new Object[]{"autoUnicode", jsonFormat.getAutoUnicode()});
        booleanTableModel.addRow(new Object[]{"unicodeLower", jsonFormat.getUnicodeLower()});
        booleanTableModel.addRow(new Object[]{"timeZone", jsonFormat.getTimeZone()});
        booleanTableModel.addRow(new Object[]{"locale", jsonFormat.getLocale()});
        booleanTableModel.addRow(new Object[]{"dateFormatRaw", jsonFormat.getDateFormatRaw()});
        jsonDataBooleanTable.setModel(booleanTableModel);
        jsonDataBooleanTable.getColumn("value").setCellEditor(new DefaultCellEditor(trueFalse));
    }

    private void initTextTable(JsonFormat jsonFormat) {
        DefaultTableModel textTableModel = new DefaultTableModel(new Object[]{"项目", "value"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }
        };
        textTableModel.addRow(new Object[]{"actived", jsonFormat.getActived()});
        textTableModel.addRow(new Object[]{"locked", jsonFormat.getLocked()});
        textTableModel.addRow(new Object[]{"indentBy", jsonFormat.getIndentBy()});
        textTableModel.addRow(new Object[]{"numberFormat", jsonFormat.getNumberFormat()});
        jsonDataTextTable.setModel(textTableModel);
    }

    private void onOK(OkJsonOnOkAction okJsonOnOkAction) {
        HashMap<String, Object> hashMap = new HashMap<>(16);
        hashMap.putAll(getJsonData(jsonDataTextTable.getModel()));
        hashMap.putAll(getJsonData(jsonDataBooleanTable.getModel()));
        hashMap.putAll(getJsonData(jsonDataDateTable.getModel()));
        String json = "@Ok(\"json\")";
        if (hashMap.size() > 0) {
            json = "@Ok(\"json:" + getJsonString(hashMap) + "\")";
        }
        okJsonOnOkAction.onOK(json);
        dispose();
    }

    private String getJsonString(HashMap<String, Object> hashMap) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        Iterator iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) iterator.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            builder.append(key + ":");
            if (value instanceof String) {
                builder.append("'" + value + "'");
            } else {
                builder.append(value);
            }
            if (iterator.hasNext()) {
                builder.append(",");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    private HashMap<String, Object> getJsonData(TableModel model) {
        HashMap<String, Object> hashMap = new HashMap<>(16);
        for (int i = 0, l = model.getRowCount(); i < l; i++) {
            String key = String.valueOf(model.getValueAt(i, 0));
            Object value = model.getValueAt(i, 1);
            if (value != null && !"".equals(String.valueOf(value))) {
                hashMap.put(key, value);
            }
        }
        return hashMap;
    }

    private void onCancel() {
        dispose();
    }
}
