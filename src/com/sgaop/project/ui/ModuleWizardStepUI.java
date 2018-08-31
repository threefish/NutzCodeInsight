package com.sgaop.project.ui;

import com.google.gson.Gson;
import com.sgaop.project.module.vo.NutzBootGroupVO;
import com.sgaop.project.module.vo.NutzBootItemVO;
import com.sgaop.project.module.vo.NutzBootVO;
import com.sgaop.project.ui.action.EnableGroupAction;
import com.sgaop.project.ui.gui.DataCheckBox;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/8/30
 */
public class ModuleWizardStepUI {

    private JPanel root;
    private JCheckBox pout;
    private JTextField makeUrl;
    private JTextField packageName;
    private JTextField groupId;
    private JTextField artifactId;
    private JTextField finalName;
    private JComboBox version;
    private JPanel groupsPanel;
    private JScrollPane scrollPanel;
    private JButton test;

    private Vector<UiCatch> uiCatches = new Vector<>();

    public ModuleWizardStepUI() {
        groupsPanel.setLayout(new BoxLayout(groupsPanel, BoxLayout.Y_AXIS));
        groupsPanel.setBorder(new EmptyBorder(5, 5, 5, 0));
        Gson gson = new Gson();
        String json = "{\"version\":[\"2.2.4\",\"2.3-SNAPSHOT\"],\"groups\":[{\"lable\":\"添加Web容器支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加Web容器支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加Web容器支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加Web容器支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加Web容器支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加Web容器支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加Web容器支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加Web容器支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加Web容器支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加Web容器支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加Web容器支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加Web容器支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加Web容器支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加模板引擎支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加模板引擎支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加模板引擎支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加模板引擎支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加模板引擎支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加模板引擎支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加模板引擎支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]},{\"lable\":\"添加模板引擎支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true},{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]}]}\n";
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
        test.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (UiCatch uiCatch : uiCatches) {
                    if (uiCatch.getGroupCheckBox().isSelected()) {
                        for (DataCheckBox checkBox : uiCatch.getCheckBoxes()) {
                            if (checkBox.isSelected()) {
                                System.out.println(checkBox.getItemVO().getName());
                            }
                        }
                    }
                }
            }
        });
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


    public JScrollPane getScrollPanel() {
        return scrollPanel;
    }

    public void setScrollPanel(JScrollPane scrollPanel) {
        this.scrollPanel = scrollPanel;
    }

    public Vector<UiCatch> getUiCatches() {
        return uiCatches;
    }

    public void setUiCatches(Vector<UiCatch> uiCatches) {
        this.uiCatches = uiCatches;
    }

    public JPanel getGroupsPanel() {
        return groupsPanel;
    }

    public void setGroupsPanel(JPanel groupsPanel) {
        this.groupsPanel = groupsPanel;
    }

    public JPanel getRoot() {
        return root;
    }

    public void setRoot(JPanel root) {
        this.root = root;
    }

    public JCheckBox getPout() {
        return pout;
    }

    public void setPout(JCheckBox pout) {
        this.pout = pout;
    }

    public JTextField getMakeUrl() {
        return makeUrl;
    }

    public void setMakeUrl(JTextField makeUrl) {
        this.makeUrl = makeUrl;
    }

    public JTextField getPackageName() {
        return packageName;
    }

    public void setPackageName(JTextField packageName) {
        this.packageName = packageName;
    }

    public JTextField getGroupId() {
        return groupId;
    }

    public void setGroupId(JTextField groupId) {
        this.groupId = groupId;
    }

    public JTextField getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(JTextField artifactId) {
        this.artifactId = artifactId;
    }

    public JTextField getFinalName() {
        return finalName;
    }

    public void setFinalName(JTextField finalName) {
        this.finalName = finalName;
    }

    public JComboBox getVersion() {
        return version;
    }

    public void setVersion(JComboBox version) {
        this.version = version;
    }
}
