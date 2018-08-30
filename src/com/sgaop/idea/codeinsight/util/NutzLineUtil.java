package com.sgaop.idea.codeinsight.util;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.sgaop.idea.codeinsight.JavaNutzTemplateVO;
import com.sgaop.idea.codeinsight.NutzCons;
import com.sgaop.project.ToolCfiguration;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/29  15:51
 */
public class NutzLineUtil {
    private static ToolCfiguration configuration = ToolCfiguration.getInstance();


    /**
     * 判断是否是Nutz的@Ok
     *
     * @param bindingElement
     * @return
     */
    public static boolean isAtOk(PsiElement bindingElement) {
        if (!(bindingElement instanceof PsiLiteralExpression)) {
            return false;
        }
        //PsiNameValuePair
        PsiElement psiNameValuePair = bindingElement.getParent();
        if (!(psiNameValuePair instanceof PsiNameValuePair)) {
            return false;
        }
        PsiElement psiAnnotationParameterList = psiNameValuePair.getParent();
        if (!(psiAnnotationParameterList instanceof PsiAnnotationParameterList)) {
            return false;
        }
        //PsiJavaCodeReferenceElement:Ok
        PsiElement psiJavaCodeReferenceElement = psiAnnotationParameterList.getPrevSibling();
        if (!(psiJavaCodeReferenceElement instanceof PsiJavaCodeReferenceElement)) {
            return false;
        }
        if (!NutzCons.OK.equals(((PsiJavaCodeReferenceElement) psiJavaCodeReferenceElement).getQualifiedName())) {
            return false;
        }
        if (getTemplateFilePathAndName(bindingElement) == null) {
            return false;
        }
        return true;
    }

    /**
     * 取得文件
     *
     * @param bindingElement
     * @return
     */
    public static List<VirtualFile> findTemplteFileList(PsiElement bindingElement) {
        JavaNutzTemplateVO nutzTemplate = getTemplateFilePathAndName(bindingElement);
        List<VirtualFile> fileList = new ArrayList<>();
        String[] exts = nutzTemplate.getFileExtension().split(";");
        for (String ext : exts) {
            if (ext.trim() != "") {
                //优化定位至当前Module 跨模块资源无法取得还是返回
                //GlobalSearchScope globalSearchScope = ((ModuleWithDependenciesScope) bindingElement.getResolveScope()).getModule().getModuleScope();
                Collection<VirtualFile> tempVirtualFiles = FilenameIndex.getAllFilesByExt(bindingElement.getProject(), ext.replaceAll("\\.", ""), GlobalSearchScope.projectScope(bindingElement.getProject()));
                if (tempVirtualFiles != null && tempVirtualFiles.size() > 0) {
                    String tempNutzFileName = nutzTemplate.getTemplatePath();
                    //兼容绝对路径模式
                    if (tempNutzFileName.endsWith(ext) && tempNutzFileName.indexOf("/") > -1) {
                        tempNutzFileName = tempNutzFileName.substring(0, tempNutzFileName.length() - ext.length());
                    }
                    //去除前缀
                    tempNutzFileName = tempNutzFileName.substring(nutzTemplate.getName().length());
                    //补上文件后缀
                    tempNutzFileName = tempNutzFileName.replace(".", "/") + ext;
                    String finalTempNutzFileName = tempNutzFileName;
                    tempVirtualFiles.stream().filter(virtualFile -> virtualFile.getCanonicalPath().endsWith(finalTempNutzFileName))
                            .forEach(virtualFile -> fileList.add(virtualFile));
                }
            }
        }
        return fileList;
    }

    /**
     * 取得模版文件相对路径
     *
     * @param bindingElement
     * @return
     */
    public static JavaNutzTemplateVO getTemplateFilePathAndName(PsiElement bindingElement) {
        PsiLiteralExpression literalExpression = (PsiLiteralExpression) bindingElement;
        String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
        if (value != null) {
            JavaNutzTemplateVO templateVO = configuration.getTemplate(value);
            if (templateVO != null) {
                templateVO.setTemplatePath(value);
            }
            return templateVO;
        }
        return null;
    }


    /**
     * 取得模版图标
     *
     * @param fileExtension
     * @return
     */
    public static Icon getTemplateIcon(String fileExtension) {
        return IconUtil.getTemplateIcon(fileExtension);
    }


}
