package com.sgaop.project.module;

import com.google.gson.Gson;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.ide.wizard.CommitStepException;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.Messages;
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

    private boolean loadingCompleted = false;

    private Gson gson = new Gson();

    public NutzBootMakerChooseStep(WizardContext wizardContext) {
        this.wizardContext = wizardContext;
    }

    @Override
    public void onWizardFinished() throws CommitStepException {
        if (isNutzBootModuleBuilder()) {
            try {
                HttpClient httpclient = HttpClients.createDefault();
                HttpGet httppost = new HttpGet(moduleWizardStepUI.getMakerUrl().getText() + "/maker/download/" + downLoadKey);
                String path = this.wizardContext.getProjectFileDirectory();
                if (this.wizardContext.getProject() != null) {
                    path = ((NutzBootModuleBuilder) this.wizardContext.getProjectBuilder()).getContentEntryPath();
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
                HttpClient httpclient = HttpClients.createDefault();
                HttpGet httppost = new HttpGet(moduleWizardStepUI.getMakerUrl().getText() + "/maker.json");
                HttpResponse response = httpclient.execute(httppost);
                String json = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
                moduleWizardStepUI.refresh(json);
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
        return moduleWizardStepUI.getRoot();
    }

    private boolean isNutzBootModuleBuilder() {
        return this.wizardContext.getProjectBuilder() instanceof NutzBootModuleBuilder;
    }
}