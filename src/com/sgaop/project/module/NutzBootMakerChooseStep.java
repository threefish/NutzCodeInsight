package com.sgaop.project.module;

import com.google.gson.Gson;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.ide.wizard.CommitStepException;
import com.intellij.openapi.options.ConfigurationException;
import com.sgaop.project.FileUtil;
import com.sgaop.project.ui.ModuleWizardStepUI;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.HttpClients;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * @author 黄川 huchuc@vip.qq.com
 */
public class NutzBootMakerChooseStep extends ModuleWizardStep {


    protected final WizardContext wizardContext;

    private ModuleWizardStepUI moduleWizardStepUI = new ModuleWizardStepUI();

    private String downLoadKey;

    private Gson gson = new Gson();

    public NutzBootMakerChooseStep(WizardContext wizardContext) {
        this.wizardContext = wizardContext;
    }

    @Override
    public JComponent getComponent() {
        return moduleWizardStepUI.getRoot();
    }

    @Override
    public void onWizardFinished() throws CommitStepException {
        try {
            HttpClient httpclient = HttpClients.createDefault();

            HttpGet httppost = new HttpGet(moduleWizardStepUI.getMakerUrl().getText() + "/maker/download/" + downLoadKey);
            String path = this.wizardContext.getProjectFileDirectory();
            if (this.wizardContext.getProject() != null) {
                NutzBootModuleBuilder moduleBuilder = (NutzBootModuleBuilder) this.wizardContext.getProjectBuilder();
                path = moduleBuilder.getPath();
            }
            HttpResponse response = httpclient.execute(httppost);
            File zipFile = Paths.get(path, "temp.zip").toFile();
            File dir = Paths.get(path).toFile();
            FileUtil.writeFile(zipFile, response.getEntity().getContent());
            FileUtil.extractZipFile(zipFile, dir);
            zipFile.delete();
        } catch (Exception e) {
            throw new CommitStepException(e.getMessage());
        }
    }

    @Override
    public boolean validate() throws ConfigurationException {
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(moduleWizardStepUI.getMakerUrl().getText() + "/maker/make");
            BasicHttpEntity entity = new BasicHttpEntity();
            entity.setContent(new ByteArrayInputStream(gson.toJson(moduleWizardStepUI.getPostData()).getBytes()));
            httppost.setEntity(entity);
            HttpResponse response = httpclient.execute(httppost);
            String json = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            HashMap redata = gson.fromJson(json, HashMap.class);
            boolean ok = Boolean.parseBoolean(String.valueOf(redata.getOrDefault("ok", "false")));
            if (ok) {
                downLoadKey = String.valueOf(redata.get("key"));
                return true;
            } else {
                downLoadKey = null;
                JOptionPane.showMessageDialog(null, redata.getOrDefault("msg", "服务暂不可用！请稍后再试！"), "错误提示", JOptionPane.ERROR_MESSAGE, null);
                return false;
            }
        } catch (Exception e) {
            throw new ConfigurationException("构筑中心发生未知异常! " + e.getMessage());
        }
    }

    @Override
    public void updateDataModel() {
        System.out.println("updateDataModel");
    }

    @Override
    public void onStepLeaving() {
        System.out.println("onStepLeaving");
    }

    @Override
    public boolean isStepVisible() {
        System.out.println("isStepVisible");
        return true;
    }

    @Override
    public void disposeUIResources() {
        System.out.println("disposeUIResources");
    }

    @Override
    public void updateStep() {
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpGet httppost = new HttpGet(moduleWizardStepUI.getMakerUrl().getText() + "/maker.json");
            HttpResponse response = httpclient.execute(httppost);
            String json = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            moduleWizardStepUI.refresh(json);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "网络异常！请稍候尝试", "错误提示", JOptionPane.ERROR_MESSAGE, null);
        }
    }
}