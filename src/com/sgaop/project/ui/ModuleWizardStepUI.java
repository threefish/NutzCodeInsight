package com.sgaop.project.ui;

import com.google.gson.Gson;
import com.sgaop.project.module.vo.NutzBootGroupVO;
import com.sgaop.project.module.vo.NutzBootItemVO;
import com.sgaop.project.module.vo.NutzBootProsVO;
import com.sgaop.project.module.vo.NutzBootVO;
import com.sgaop.project.ui.action.EnableGroupAction;
import com.sgaop.project.ui.gui.DataCheckBox;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
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
        String json = "{\"version\":[\"2.2.4\",\"2.3-SNAPSHOT\"],\"groups\":[{\"lable\":\"添加Web容器支持\",\"enable\":false,\"items\":[{\"lable\":\"Jetty容器,推荐\",\"name\":\"jetty\",\"enable\":false,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"},{\"name\":\"port\",\"val\":8080}]},{\"lable\":\"Tomcat容器\",\"name\":\"tomcat\",\"enable\":false,\"pros\":[{\"name\":\"port\",\"val\":8080},{\"name\":\"host\",\"val\":\"127.0.0.1\"}]},{\"lable\":\"Undertow容器\",\"name\":\"undertow\",\"enable\":false,\"pros\":[{\"name\":\"port\",\"val\":8080},{\"name\":\"host\",\"val\":\"127.0.0.1\"}]},{\"lable\":\"Nutz.Mvc\",\"name\":\"nutzmvc\",\"enable\":false}]},{\"lable\":\"添加关系型数据库\",\"enable\":false,\"items\":[{\"lable\":\"Jdbc 传统数据源\",\"name\":\"jdbc\",\"enable\":false,\"pros\":[{\"name\":\"url\",\"val\":\"jdbc:h2:mem:~\"},{\"name\":\"username\",\"val\":\"root\"},{\"name\":\"password\",\"val\":\"root\"}]},{\"lable\":\"ShardingJdbc 分库分表\",\"name\":\"shardingjdbc\",\"enable\":false},{\"lable\":\"BeetlSql\",\"name\":\"beetlsql\",\"enable\":false},{\"lable\":\"Nutz.Dao\",\"name\":\"nutzdao\",\"enable\":false,\"pros\":[{\"name\":\"cache\",\"val\":false}]}]},{\"lable\":\"添加NoSQL数据库\",\"enable\":false,\"items\":[{\"lable\":\"Redis\",\"name\":\"redis\",\"enable\":false},{\"lable\":\"MongoDB\",\"name\":\"mongo\",\"enable\":false,\"pros\":[{\"name\":\"dbname\",\"val\":\"nutzboot\"}]}]},{\"lable\":\"添加模板引擎支持\",\"enable\":false,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":false},{\"lable\":\"Thymeleaf\",\"name\":\"beetl\",\"enable\":false}]},{\"lable\":\"添加其他杂项\",\"enable\":false,\"items\":[{\"lable\":\"Quartz 计划任务,定时任务\",\"name\":\"beetl\",\"enable\":false},{\"lable\":\"WeChat 微信支持\",\"name\":\"beetl\",\"enable\":false},{\"lable\":\"Dubbo\",\"name\":\"beetl\",\"enable\":false},{\"lable\":\"Zbus 队列/RPC\",\"name\":\"beetl\",\"enable\":false},{\"lable\":\"Ngrok 内网映射\",\"name\":\"beetl\",\"enable\":false},{\"lable\":\"Shiro 权限\",\"name\":\"beetl\",\"enable\":false},{\"lable\":\"Jedisque 队列\",\"name\":\"beetl\",\"enable\":false},{\"lable\":\"uflo2 工作流\",\"name\":\"beetl\",\"enable\":false},{\"lable\":\"urule 规则引擎\",\"name\":\"beetl\",\"enable\":false},{\"lable\":\"ureport 中式报表\",\"name\":\"beetl\",\"enable\":false},{\"lable\":\"wkcache 方法级缓存\",\"name\":\"beetl\",\"enable\":false},{\"lable\":\"feign 远程调用\",\"name\":\"beetl\",\"enable\":false},{\"lable\":\"email客户端\",\"name\":\"beetl\",\"enable\":false}]},{\"lable\":\"高级配置\",\"enable\":false,\"items\":[{\"lable\":\"资源、配置文件放jar包外面\",\"name\":\"pout\",\"enable\":false}]}]}\n\n\n";
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
                System.out.println(gson.toJson(getPostData()));
            }
        });
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
