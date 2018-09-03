package com.sgaop.project.ui;

import com.google.gson.Gson;
import com.intellij.openapi.ui.Messages;
import com.sgaop.project.HttpUtil;
import com.sgaop.project.module.vo.NutzBootGroupVO;
import com.sgaop.project.module.vo.NutzBootItemVO;
import com.sgaop.project.module.vo.NutzBootProsVO;
import com.sgaop.project.module.vo.NutzBootVO;
import com.sgaop.project.ui.action.EnableGroupAction;
import com.sgaop.project.ui.gui.DataCheckBox;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/8/30
 */
public class ModuleWizardStepUI {

    private JPanel root;
    private JCheckBox pout;
    private JTextField packageName;
    private JTextField groupId;
    private JTextField artifactId;
    private JTextField finalName;
    private JComboBox version;
    private JPanel groupsPanel;
    private JScrollPane scrollPanel;
    private JTextField makerUrl;
    private JButton reloadButton;

    private Vector<UiCatch> uiCatches = new Vector<>();

    public ModuleWizardStepUI() {
        makerUrl.setText("https://get.nutz.io");
        reloadButton.addActionListener((event -> {
            try {
                refresh(HttpUtil.get(makerUrl.getText() + "/maker.json"));
            } catch (Exception e) {
                Messages.showErrorDialog("网络异常！请稍候尝试!" + e.getMessage(), "错误提示");
            }
        }));
    }

    public void refresh(String json) {
        uiCatches.clear();
        groupsPanel.removeAll();

        groupsPanel.setLayout(new BoxLayout(groupsPanel, BoxLayout.Y_AXIS));
        groupsPanel.setBorder(new EmptyBorder(5, 5, 5, 0));
        Gson gson = new Gson();
        NutzBootVO nutzBootVO = gson.fromJson(json, NutzBootVO.class);
        version.setModel(new DefaultComboBoxModel<>(nutzBootVO.getVersion()));
        for (NutzBootGroupVO groupVO : nutzBootVO.getGroups()) {
            JCheckBox groupCheckBox = new JCheckBox(groupVO.getLable());
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setBorder(new EmptyBorder(3, 20, 3, 0));
            UiCatch uiCatch = new UiCatch(groupCheckBox, itemPanel);
            for (NutzBootItemVO itemVO : groupVO.getItems()) {
                DataCheckBox itemCheckBox = new DataCheckBox(itemVO.getLable(), itemVO);
                itemCheckBox.addActionListener(new EnableGroupAction());
                uiCatch.getCheckBoxes().add(itemCheckBox);
                itemPanel.add(itemCheckBox);
                itemPanel.revalidate();
                itemPanel.repaint();
            }
            groupCheckBox.addActionListener(new EnableGroupAction(uiCatch));
            groupsPanel.add(groupCheckBox);
            itemPanel.setVisible(false);
            groupsPanel.add(itemPanel);
            scrollPanel.revalidate();
            scrollPanel.repaint();
            uiCatches.add(uiCatch);
        }
    }

    public HashMap<String, Object> getPostData() {
        HashMap<String, Object> data = new HashMap<>(5);
        data.put("packageName", packageName.getText());
        data.put("groupId", groupId.getText());
        data.put("artifactId", artifactId.getText());
        data.put("finalName", finalName.getText());
        data.put("version", version.getSelectedItem());
        for (UiCatch uiCatch : uiCatches) {
            for (DataCheckBox checkBox : uiCatch.getCheckBoxes()) {
                HashMap<String, Object> items = new HashMap<>(1);
                items.put("enable", checkBox.getItemVO().getEnable());
                for (NutzBootProsVO prosVO : checkBox.getItemVO().getPros()) {
                    items.put(prosVO.getName(), prosVO.getVal());
                }
                data.put(checkBox.getItemVO().getName(), items);
            }
        }
        return data;
    }


    public class UiCatch {

        JCheckBox groupCheckBox;

        JPanel jPanel;

        Vector<DataCheckBox> checkBoxes = new Vector<>();

        public UiCatch(JCheckBox groupCheckBox, JPanel itemPanel) {
            this.groupCheckBox = groupCheckBox;
            this.jPanel = itemPanel;
        }

        public JCheckBox getGroupCheckBox() {
            return groupCheckBox;
        }

        public void setGroupCheckBox(JCheckBox groupCheckBox) {
            this.groupCheckBox = groupCheckBox;
        }

        public JPanel getjPanel() {
            return jPanel;
        }

        public void setjPanel(JPanel jPanel) {
            this.jPanel = jPanel;
        }

        public Vector<DataCheckBox> getCheckBoxes() {
            return checkBoxes;
        }

        public void setCheckBoxes(Vector<DataCheckBox> checkBoxes) {
            this.checkBoxes = checkBoxes;
        }
    }

    public JTextField getMakerUrl() {
        return makerUrl;
    }

    public JPanel getRoot() {
        return root;
    }
}
