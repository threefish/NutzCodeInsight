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
 * @author 黄川 huchuc@vip.qq.com
 * <p>
 * 创建时间: 2017/12/11  14:07
 */
public class ToolConfigurable implements Configurable {

    private ToolCfiguration configuration = ToolCfiguration.getInstance();

    private SettingConfigUi ui = new SettingConfigUi();

    @Nls
    @Override
    public String getDisplayName() {
        return "NutzCodeInsight";
    }

    @Nls
    @Override
    public String getHelpTopic() {
        return "NutzCodeInsight";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return this.ui.getRootPanel();
    }

    @Override
    public boolean isModified() {
        String oldData = new Gson().toJson(configuration.getSettingVO());
        String newData = new Gson().toJson(getGlobalSettingVO());
        return !oldData.equals(newData);
    }

    @Override
    public void apply() {
        configuration.setSettingVO(getGlobalSettingVO());
    }

    @Override
    public void reset() {
        GlobalSettingVO vo = configuration.getSettingVO();
        DefaultTableModel model = (DefaultTableModel) ui.getTemplateTable().getModel();
        model.setRowCount(0);
        model.addRow(new String[]{"jsp:", ".jsp", "不可编辑"});
        model.addRow(new String[]{"->:", vo.getTableData().getOrDefault("->:", ".html"), "不可编辑"});
        model.addRow(new String[]{"btl:", vo.getTableData().getOrDefault("btl:", ".html"), "可编辑"});
        model.addRow(new String[]{"beetl:", vo.getTableData().getOrDefault("beetl:", ".html"), "可编辑"});
        for (Map.Entry<String, String> entry : vo.getTableData().entrySet()) {
            if (entry.getKey().equals("jsp:") || entry.getKey().equals("btl:") || entry.getKey().equals("beetl:") || entry.getKey().equals("->:")) {
                continue;
            }
            model.addRow(new String[]{entry.getKey(), entry.getValue()});
        }
        ui.getIncludeRegular().setText(vo.getBeetlIncludeRegular());
        ui.getLayoutRegular().setText(vo.getBeetlLayoutRegular());
        ui.getI18nRegular().setText(vo.getI18nRegular());
        ui.getI18nKeyRegular().setText(vo.getI18nKeyRegular());
    }

    private GlobalSettingVO getGlobalSettingVO() {
        GlobalSettingVO vo = new GlobalSettingVO();
        DefaultTableModel model = (DefaultTableModel) ui.getTemplateTable().getModel();
        int len = model.getRowCount();
        HashMap<String, String> tableData = new HashMap(len);
        for (int i = 0; i < len; i++) {
            String name = String.valueOf(ui.getTemplateTable().getValueAt(i, 0)).trim();
            String value = String.valueOf(ui.getTemplateTable().getValueAt(i, 1)).trim();
            if (name.length() > 0 && value.length() > 0) {
                tableData.put(name, value);
            }
        }
        vo.setTableData(tableData);
        vo.setBeetlLayoutRegular(ui.getLayoutRegular().getText());
        vo.setBeetlIncludeRegular(ui.getIncludeRegular().getText());
        vo.setI18nRegular(ui.getI18nRegular().getText());
        vo.setI18nKeyRegular(ui.getI18nKeyRegular().getText());
        return vo;
    }
}
