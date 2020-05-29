package com.sgaop.idea.project.module;

import com.google.gson.Gson;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.ide.wizard.CommitStepException;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.Messages;
import com.sgaop.idea.project.module.vo.NutzBootGroupVO;
import com.sgaop.idea.project.module.vo.NutzBootItemVO;
import com.sgaop.idea.project.module.vo.NutzBootProsVO;
import com.sgaop.idea.project.module.vo.NutzBootVO;
import com.sgaop.idea.project.ui.action.EnableGroupAction;
import com.sgaop.idea.project.ui.gui.DataCheckBox;
import com.sgaop.util.FileUtil;
import com.sgaop.util.HttpUtil;
import com.sgaop.util.Strings;
import org.apache.http.HttpResponse;
import org.apache.http.entity.BasicHttpEntity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/8/30
 */
public class NutzBootMakerChooseWizardStep extends ModuleWizardStep {

    protected WizardContext wizardContext;
    private JPanel root;
    private JTextField packageName;
    private JTextField groupId;
    private JTextField artifactId;
    private JTextField finalName;
    private JComboBox version;
    private JPanel groupsPanel;
    private JScrollPane scrollPanel;
    private JTextField makerUrl;
    private JButton reloadButton;
    private final Vector<UiCatch> uiCatches = new Vector<>();
    private String downLoadKey;

    private final NutzBootModuleBuilder moduleBuilder;

    private boolean loadingCompleted = false;

    private final Gson gson = new Gson();

    public NutzBootMakerChooseWizardStep(NutzBootModuleBuilder moduleBuilder, WizardContext wizardContext) {
        this.moduleBuilder = moduleBuilder;
        this.wizardContext = wizardContext;
        makerUrl.setText("https://get.nutz.io");
        reloadButton.addActionListener((event -> {
            try {
                refresh(HttpUtil.get(makerUrl.getText() + "/maker.json"));
            } catch (Exception e) {
                Messages.showErrorDialog("网络异常！请稍候尝试!" + e.getMessage(), "错误提示");
            }
        }));
    }

    @Override
    public void onWizardFinished() throws CommitStepException {
        if (isNutzBootModuleBuilder()) {
            try {
                HttpResponse response = HttpUtil.getResponse(makerUrl.getText() + "/maker/download/" + downLoadKey);
                String path = this.wizardContext.getProjectFileDirectory();
                if (this.wizardContext.getProject() != null) {
                    path = ((NutzBootModuleBuilder) this.wizardContext.getProjectBuilder()).getContentEntryPath();
                }
                File zipFile = Paths.get(path, System.currentTimeMillis() + ".zip").toFile();
                File dir = Paths.get(path).toFile();
                FileUtil.writeFile(zipFile, response.getEntity().getContent());
                FileUtil.extractZipFile(zipFile, dir);
                zipFile.delete();
            } catch (Exception e) {
                throw new CommitStepException(e.getMessage());
            }
        }
    }


    @Override
    public boolean validate() throws ConfigurationException {
        try {
            if (Strings.isNullOrEmpty(packageName.getText())) {
                Messages.showErrorDialog("顶层包名不能为空", "错误提示");
                return false;
            }
            if (Strings.isNullOrEmpty(groupId.getText())) {
                Messages.showErrorDialog("项目组织名不能为空", "错误提示");
                return false;
            }
            if (Strings.isNullOrEmpty(artifactId.getText())) {
                Messages.showErrorDialog("项目ID不能为空", "错误提示");
                return false;
            }
            if (Strings.isNullOrEmpty(finalName.getText())) {
                Messages.showErrorDialog("发布文件名不能为空", "错误提示");
                return false;
            }
            BasicHttpEntity entity = new BasicHttpEntity();
            entity.setContent(new ByteArrayInputStream(gson.toJson(getPostData()).getBytes()));
            String json = HttpUtil.post(makerUrl.getText() + "/maker/make", entity);
            HashMap redata = gson.fromJson(json, HashMap.class);
            boolean ok = Boolean.parseBoolean(String.valueOf(redata.getOrDefault("ok", "false")));
            if (ok) {
                downLoadKey = String.valueOf(redata.get("key"));
                moduleBuilder.setModuleName(finalName.getText());
                return true;
            } else {
                downLoadKey = null;
                Messages.showErrorDialog(redata.getOrDefault("msg", "服务暂不可用！请稍后再试！").toString(), "错误提示");
                return false;
            }
        } catch (Exception e) {
            throw new ConfigurationException("构筑中心发生未知异常! " + e.getMessage());
        }
    }


    @Override
    public void updateStep() {
        if (!loadingCompleted) {
            try {
                refresh(HttpUtil.get(makerUrl.getText() + "/maker.json"));
                loadingCompleted = true;
            } catch (Exception e) {
                Messages.showErrorDialog("网络异常！请稍候尝试!" + e.getMessage(), "错误提示");
            }
        }
    }

    @Override
    public void updateDataModel() {
    }

    @Override
    public JComponent getComponent() {
        return getRoot();
    }

    private boolean isNutzBootModuleBuilder() {
        return this.wizardContext.getProjectBuilder() instanceof NutzBootModuleBuilder;
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

    public JPanel getRoot() {
        return root;
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

}
