package com.sgaop.idea.linemarker.vo;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2018/1/2  10:27
 */
public class JavaNutzTemplateVO {
    private String name;
    private String fileExtension;
    /**
     * 实际用户Module中书写的模版文件路径
     * jsp:demo.index
     */
    private String templatePath;

    public JavaNutzTemplateVO(String name, String fileExtension) {
        this.name = name.trim();
        this.fileExtension = fileExtension.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    @Override
    public String toString() {
        return "JavaNutzTemplateVO{" +
                "name='" + name + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                ", templatePath='" + templatePath + '\'' +
                '}';
    }
}
