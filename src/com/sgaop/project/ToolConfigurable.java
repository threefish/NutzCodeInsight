package com.sgaop.project;

import com.google.gson.Gson;
import com.intellij.openapi.options.Configurable;
import com.sgaop.project.ui.SettingConfigUi;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2017/12/11  14:07
 * 描述此类：
 */
public class ToolConfigurable implements Configurable {

    private ToolCfiguration configuration = ToolCfiguration.getInstance();

    private SettingConfigUi ui;

    @Nls
    @Override
    public String getDisplayName() {
        return "NutzCodeInsight";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (null == this.ui) {
            this.ui = new SettingConfigUi();
        }
        return this.ui.getRootPanel();
    }

    @Override
    public boolean isModified() {
        String oldData = new Gson().toJson(configuration.getData());
        String newData = new Gson().toJson(getTableListData());
        return !oldData.equals(newData);
    }

    @Override
    public void apply() {
        configuration.setData(getTableListData());
    }

    @Override
    public void reset() {
        HashMap<String, String> data = configuration.getData();
        DefaultTableModel model = (DefaultTableModel) ui.getTemplateTable().getModel();
        model.setRowCount(0);
        model.addRow(new String[]{"jsp:", ".jsp", "否"});
        model.addRow(new String[]{"btl:", data.getOrDefault("btl:", ".html"), "是"});
        model.addRow(new String[]{"beetl:", data.getOrDefault("beetl:", ".html"), "是"});
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (entry.getKey().equals("jsp:") || entry.getKey().equals("btl:") || entry.getKey().equals("beetl:")) {
                continue;
            }
            model.addRow(new String[]{entry.getKey(), entry.getValue()});
        }
    }

    private HashMap<String, String> getTableListData() {
        DefaultTableModel model = (DefaultTableModel) ui.getTemplateTable().getModel();
        int len = model.getRowCount();
        HashMap<String, String> nameVal = new HashMap(len);
        for (int i = 0; i < len; i++) {
            String name = String.valueOf(ui.getTemplateTable().getValueAt(i, 0)).trim();
            String value = String.valueOf(ui.getTemplateTable().getValueAt(i, 1)).trim();
            if (name.length() > 0 && value.length() > 0) {
                nameVal.put(name, value);
            }
        }
        return nameVal;
    }
}
