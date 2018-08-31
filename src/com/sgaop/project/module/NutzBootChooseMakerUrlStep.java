package com.sgaop.project.module;

import com.intellij.ide.util.projectWizard.ProjectJdkStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.options.ConfigurationException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import javax.swing.*;
import java.awt.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/8/31
 */
public class NutzBootChooseMakerUrlStep extends ProjectJdkStep {

    private NutzBootMakerChooseStep makerChooseStep;

    public NutzBootChooseMakerUrlStep(WizardContext wizardContext, NutzBootMakerChooseStep makerChooseStep) {
        super(wizardContext);
        this.makerChooseStep = makerChooseStep;
    }

    public JTextField makerUrlText = new JTextField("http://127.0.0.1:8080");

    @Override
    public JComponent getComponent() {
        JPanel jPanel = new JPanel();
        jPanel.setMaximumSize(new Dimension(0, 40));
        jPanel.setMinimumSize(new Dimension(0, 40));
        jPanel.setPreferredSize(new Dimension(0, 40));
        makerUrlText.setPreferredSize(new Dimension(300, 30));
//        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT, FlowLayout.CENTER, FlowLayout.LEFT));
        JLabel jLabel = new JLabel("NutzBoot 构筑中心");
        jPanel.add(jLabel);
        jPanel.add(makerUrlText);
        jPanel.revalidate();
        jPanel.repaint();
        return jPanel;
    }


    @Override
    public boolean validate() throws ConfigurationException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpGet httppost = new HttpGet(makerUrlText.getText() + "/maker.json");
        try {
            httpclient.execute(httppost);
            return true;
        } catch (Exception e) {
            throw new ConfigurationException("构筑中心不可用！" + e.getMessage());
        }
    }

    @Override
    public void onStepLeaving() {
        makerChooseStep.setMakeUrl(makerUrlText.getText());
    }
}
