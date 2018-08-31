package com.sgaop.project.ui;

import javax.swing.*;

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
