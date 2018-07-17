package com.sgaop.ui;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.sgaop.idea.ProjectPluginConfig;
import com.sgaop.templte.BeetlTemplteEngine;
import com.sgaop.templte.ITemplteEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * 自动生成接口和实现类
 */
public class CreateServiceImplFram extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField servicePackageText;
    private JTextField serviceImplPackageText;

    ProjectPluginConfig pluginrInfo;

    String entityPackage;

    String entityName;

    String serviceFileName;

    String serviceImplFileName;

    String servicePackage;

    String serviceImplPackage;

    public CreateServiceImplFram(ProjectPluginConfig pluginEditorInfo, String entityPackage, String entityName) {
        this.pluginrInfo = pluginEditorInfo;
        this.entityPackage = entityPackage;
        this.entityName = entityName;
        this.serviceFileName = entityName + "Service";
        this.serviceImplFileName = entityName + "ServiceImpl";
        this.servicePackage = entityPackage.replace("entity", "service");
        this.serviceImplPackage = entityPackage.replace("entity", "service") + ".impl";
        int w = 500, h = 400;
        int x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (w / 2));
        int y = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (h / 2));
        setContentPane(contentPane);
        setTitle("快速创建接口及实现类");
        setModal(true);
        setBounds(x, y, w, h);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener((e) -> onOK());
        buttonCancel.addActionListener((e) -> onCancel());
        servicePackageText.setText(this.servicePackage + "." + serviceFileName);
        serviceImplPackageText.setText(this.serviceImplPackage + "." + serviceImplFileName);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(((e) -> onCancel()), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        ITemplteEngine renderTemplte = new BeetlTemplteEngine();
        try {
            String moduleBasePath = pluginrInfo.getPsiFile().getVirtualFile().getCanonicalPath();
            String temp = entityPackage.replaceAll("\\.", "/");
            moduleBasePath = moduleBasePath.replace(temp, "");
            moduleBasePath = moduleBasePath.replace("/" + entityName + ".java", "");
            HashMap bindData = getBindData();
            FileTemplateManager fileTemplateManager = FileTemplateManager.getInstance(pluginrInfo.getProject());
            FileTemplate service = fileTemplateManager.getTemplate("service");
            FileTemplate serviceImpl = fileTemplateManager.getTemplate("serviceImpl");
            final String finalmoduleBasePath = moduleBasePath;
            VirtualFile value = VirtualFileManager.getInstance().findFileByUrl(Paths.get(moduleBasePath).toUri().toString());
            renderTemplte.renderToFile(service.getText(), bindData, getPath(finalmoduleBasePath, this.servicePackageText.getText()));
            renderTemplte.renderToFile(serviceImpl.getText(), bindData, getPath(finalmoduleBasePath, this.serviceImplPackageText.getText()));
            value.refresh(true, true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE, null);
        }
        dispose();
    }


    private HashMap getBindData() {
        Properties sys = System.getProperties();
        HashMap bindData = new HashMap(7);
        bindData.put("entityName", this.entityName);
        bindData.put("entityPackage", this.entityPackage);

        bindData.put("serviceFileName", this.serviceFileName);
        bindData.put("servicePackage", this.servicePackage);

        bindData.put("serviceImplFileName", this.serviceImplFileName);
        bindData.put("serviceImplPackage", this.serviceImplPackage);
        bindData.put("user", sys.getProperty("user.name"));
        return bindData;
    }

    private Path getPath(String basePath, String packages) {
        String[] s1 = packages.split("\\.");
        ArrayList<String> list = new ArrayList<>();
        int last = s1.length - 1;
        for (int i = 0; i < s1.length; i++) {
            if (i == last) {
                list.add(s1[i] + ".java");
            } else {
                list.add(s1[i]);
            }
        }
        return Paths.get(basePath, list.toArray(new String[0]));
    }

    private void onCancel() {
        dispose();
    }

}
