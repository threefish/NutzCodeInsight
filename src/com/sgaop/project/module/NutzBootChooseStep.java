package com.sgaop.project.module;

import com.google.gson.Gson;
import com.intellij.ide.util.projectWizard.ProjectJdkStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.ide.wizard.CommitStepException;
import com.intellij.openapi.options.ConfigurationException;
import com.sgaop.project.FileUtil;
import com.sgaop.project.module.vo.NutzBootGroupVO;
import com.sgaop.project.module.vo.NutzBootVO;
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
public class NutzBootChooseStep extends ProjectJdkStep {

    protected final WizardContext wizardContext;

    private ModuleWizardStepUI moduleWizardStepUI;

    private String downLoadKey;
    private Gson gson = new Gson();

    private String makeUrl = "https://get.nutz.io";

    public NutzBootChooseStep(WizardContext wizardContext) {
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
        String s = new String("{\"packageName\":\"io.nutz.demo\",\"groupId\":\"io.nutz\",\"artifactId\":\"demo\",\"finalName\":\"demo\",\"version\":\"2.3-SNAPSHOT\",\"nutzmvc\":{\"enable\":true},\"nutzdao\":{\"enable\":false,\"cache\":false},\"jetty\":{\"enable\":false,\"port\":8080,\"host\":\"127.0.0.1\"},\"jdbc\":{\"enable\":false,\"url\":\"jdbc:h2:mem:~\",\"username\":\"root\",\"password\":\"root\"},\"redis\":{\"enable\":false},\"quartz\":{\"enable\":false},\"weixin\":{\"enable\":false},\"tomcat\":{\"enable\":true,\"port\":8080,\"host\":\"127.0.0.1\"},\"undertow\":{\"enable\":false,\"port\":8080,\"host\":\"127.0.0.1\"},\"dubbo\":{\"enable\":false},\"zbus\":{\"enable\":false},\"ngrok\":{\"enable\":false,\"client\":{\"auth_token\":\"4kg9lckq5og4ip02j736e3i7ku\"}},\"shardingjdbc\":{\"enable\":false},\"beetlsql\":{\"enable\":false},\"mongo\":{\"enable\":false,\"dbname\":\"nutzboot\"},\"shiro\":{\"enable\":false},\"beetl\":{\"enable\":false},\"thymeleaf\":{\"enable\":false},\"disque\":{\"enable\":false},\"uflo2\":{\"enable\":false},\"urule\":{\"enable\":false},\"ureport\":{\"enable\":false},\"wkcache\":{\"enable\":false},\"feign\":{\"enable\":false},\"tio\":{\"enable\":false},\"j2cache\":{\"enable\":false},\"apolloClient\":{\"enable\":false},\"configClient\":{\"enable\":false},\"email\":{\"enable\":false},\"pout\":{\"enable\":false}}");
        try {
            BasicHttpEntity entity = new BasicHttpEntity();
            entity.setContent(new ByteArrayInputStream(s.getBytes()));
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
        } catch (IOException e) {
            throw new ConfigurationException(e.getMessage());
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
        String json = "{\"version\":[\"2.2.4\", \"2.3.1-SNAPSHOT\"],\"groups\":[{\"lable\":\"添加Web容器支持\",\"enable\":true,\"items\":[{\"lable\":\"jetty容器\",\"name\":\"jetty\",\"enable\":true,\"pros\":[{\"name\":\"host\",\"val\":\"127.0.0.1\"}]}]}, {\"lable\":\"添加模板引擎支持\",\"enable\":true,\"items\":[{\"lable\":\"beetl模版引擎\",\"name\":\"beetl\",\"enable\":true,\"pros\":[]}]}]}\n";
        NutzBootVO nutzBootVO = gson.fromJson(json, NutzBootVO.class);
        this.moduleWizardStepUI.getVersion().setModel(new DefaultComboBoxModel<>(nutzBootVO.getVersion()));
        for (NutzBootGroupVO bootGroupVO : nutzBootVO.getGroups()) {

        }
    }
}