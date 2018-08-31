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

    public JTextField makerUrlText = new JTextField("https://get.nutz.io");

    @Override
    public JComponent getComponent() {
        JPanel jPanel = new JPanel(new GridBagLayout()) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(-1, 200);
            }
        };
        makerUrlText.setPreferredSize(new Dimension(500, 30));
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT, FlowLayout.CENTER, FlowLayout.LEFT));
        JLabel jLabel = new JLabel("NutzBoot 构筑中心 ");
        jPanel.add(jLabel);
        jPanel.add(makerUrlText);
        jPanel.add(new JLabel(" 可改为私有构筑中心"));
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
