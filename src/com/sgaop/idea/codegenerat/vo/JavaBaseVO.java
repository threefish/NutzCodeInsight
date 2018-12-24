package com.sgaop.idea.codegenerat.vo;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/12/24
 */
public class JavaBaseVO {

    String entityName;
    String entityPackage;

    String servicePackage;
    String serviceFileName;

    String serviceImplFileName;
    String serviceImplPackage;

    String actionFileName;
    String actionPackage;

    String funName;
    String templatePath;

    String user;

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getActionFileName() {
        return actionFileName;
    }

    public void setActionFileName(String actionFileName) {
        this.actionFileName = actionFileName;
    }

    public String getActionPackage() {
        return actionPackage;
    }

    public void setActionPackage(String actionPackage) {
        this.actionPackage = actionPackage;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getServiceFileName() {
        return serviceFileName;
    }

    public void setServiceFileName(String serviceFileName) {
        this.serviceFileName = serviceFileName;
    }

    public String getServiceImplFileName() {
        return serviceImplFileName;
    }

    public void setServiceImplFileName(String serviceImplFileName) {
        this.serviceImplFileName = serviceImplFileName;
    }

    public String getServiceImplPackage() {
        return serviceImplPackage;
    }

    public void setServiceImplPackage(String serviceImplPackage) {
        this.serviceImplPackage = serviceImplPackage;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
