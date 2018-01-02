package com.sgaop.project;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
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
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        int len = ui.getTemplateTable().getRowCount() - 1;
        HashMap<String, String> nameVal = new HashMap(len);
        for (int i = len; i >= 0; i--) {
            String name = String.valueOf(ui.getTemplateTable().getValueAt(i, 0));
            String value = String.valueOf(ui.getTemplateTable().getValueAt(i, 1));
            if (name.trim().length() > 0 && value.trim().length() > 0) {
                nameVal.put(name, value);
            }
        }
        configuration.setData(nameVal);
    }

    @Override
    public void reset() {
        DefaultTableModel model = (DefaultTableModel) ui.getTemplateTable().getModel();
        model.setRowCount(0);
        model.addRow(new String[]{"jsp:", ".jsp"});
        for (Map.Entry<String, String> entry : configuration.getData().entrySet()) {
            if (entry.getKey().equals("jsp:")) {
                continue;
            }
            model.addRow(new String[]{entry.getKey(), entry.getValue()});
        }
    }
}
