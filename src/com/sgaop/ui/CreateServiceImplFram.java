package com.sgaop.ui;

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

public class CreateServiceImplFram extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField servicePackageText;
    private JTextField serviceImplPackageText;

    ProjectPluginConfig pluginrInfo;

    String packageName;

    String fileName;

    String serviceFileName;

    String serviceImplFileName;

    public CreateServiceImplFram(ProjectPluginConfig pluginEditorInfo, String packageName, String filename) {
        this.pluginrInfo = pluginEditorInfo;
        this.packageName = packageName;
        this.fileName = filename;
        this.serviceFileName = filename + "Service";
        this.serviceImplFileName = filename + "ServiceImpl";
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
        servicePackageText.setText(packageName + "." + serviceFileName);
        serviceImplPackageText.setText(packageName + "." + serviceImplFileName);
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
            String temp = packageName.replaceAll("\\.", "/");
            moduleBasePath = moduleBasePath.replace(temp, "");
            moduleBasePath = moduleBasePath.replace("/" + fileName + ".java", "");
            HashMap bindData = new HashMap();
            renderTemplte.renderToFile("service.ft", bindData, getPath(moduleBasePath, this.servicePackageText.getText()));
            renderTemplte.renderToFile("serviceImpl.ft", bindData, getPath(moduleBasePath, this.serviceImplPackageText.getText()));
            pluginrInfo.getProject().getProjectFile().refresh(true, true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE, null);
        }
        dispose();
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
