package com.sgaop.util;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;

import java.util.Properties;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/2
 */
public class TemplateFileUtil {
    /**
     * 按照idea默认模版引擎生成文件
     * @param fileTemplateName
     * @param fileName
     * @param properties
     * @param directory
     * @return
     * @throws Exception
     */
    public static PsiElement createFromTemplate(String fileTemplateName, String fileName, Properties properties, PsiDirectory directory) throws Exception {
        FileTemplateManager fileTemplateManager = FileTemplateManager.getDefaultInstance();
        FileTemplate fileTemplate = fileTemplateManager.getJ2eeTemplate(fileTemplateName);
        return FileTemplateUtil.createFromTemplate(fileTemplate, fileName, properties, directory);
    }
}
