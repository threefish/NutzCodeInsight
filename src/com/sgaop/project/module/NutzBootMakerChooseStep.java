package com.sgaop.project.module;

import com.google.gson.Gson;
import com.intellij.ide.util.projectWizard.ProjectJdkStep;
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
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * @author 黄川 huchuc@vip.qq.com
 */
public class NutzBootMakerChooseStep extends ProjectJdkStep {

    protected final WizardContext wizardContext;

    private ModuleWizardStepUI moduleWizardStepUI;

    private String downLoadKey;

    private Gson gson = new Gson();

    private String makeUrl = "http://127.0.0.1:8080";

    public String getMakeUrl() {
        return makeUrl;
    }

    public void setMakeUrl(String makeUrl) {
        this.makeUrl = makeUrl;
    }

    public NutzBootMakerChooseStep(WizardContext wizardContext) {
        super(wizardContext);
        this.wizardContext = wizardContext;
        this.moduleWizardStepUI = new ModuleWizardStepUI();
    }

    @Override
    public JComponent getComponent() {
        return moduleWizardStepUI.getRoot();
    }

    @Override
    public void onWizardFinished() throws CommitStepException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpGet httppost = new HttpGet(makeUrl + "/maker/download/" + downLoadKey);
        try {
            HttpResponse response = httpclient.execute(httppost);
            File zipFile = Paths.get(this.wizardContext.getProjectFileDirectory(), "temp.zip").toFile();
            File dir = Paths.get(this.wizardContext.getProjectFileDirectory()).toFile();
            FileUtil.writeFile(zipFile, response.getEntity().getContent());
            FileUtil.extractZipFile(zipFile, dir);
            zipFile.delete();
        } catch (IOException e) {
            throw new CommitStepException(e.getMessage());
        }
    }

    @Override
    public boolean validate() throws ConfigurationException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(makeUrl + "/maker/make");
        try {
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
    }

    @Override
    public void onStepLeaving() {
    }

    @Override
    public boolean isStepVisible() {
        return true;
    }

    @Override
    public void disposeUIResources() {
    }

    @Override
    public void updateStep() {
        HttpClient httpclient = HttpClients.createDefault();
        HttpGet httppost = new HttpGet(makeUrl + "/maker.json");
        try {
            HttpResponse response = httpclient.execute(httppost);
            String json = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            moduleWizardStepUI.refresh(json);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "网络异常！请稍候尝试", "错误提示", JOptionPane.ERROR_MESSAGE, null);
        }
    }
}