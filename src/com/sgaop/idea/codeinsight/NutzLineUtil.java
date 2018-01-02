package com.sgaop.idea.codeinsight;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.sgaop.project.ToolCfiguration;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
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
        if (!"org.nutz.mvc.annotation.Ok".equals(((PsiJavaCodeReferenceElement) psiJavaCodeReferenceElement).getQualifiedName())) {
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
        TemplateVO nutzTemplate = getTemplateFilePathAndName(bindingElement);
        Collection<VirtualFile> virtualFiles = FilenameIndex.getAllFilesByExt(bindingElement.getProject(), nutzTemplate.getFileExtension().replaceAll("\\.", ""), GlobalSearchScope.allScope(bindingElement.getProject()));
        List<VirtualFile> fileList = new ArrayList<>();
        String tempNutzFileName = nutzTemplate.getTemplatePath();
        //兼容绝对路径模式
        if (tempNutzFileName.endsWith(nutzTemplate.getFileExtension())&&tempNutzFileName.indexOf("/")>-1) {
            tempNutzFileName = tempNutzFileName.substring(0, tempNutzFileName.length() - nutzTemplate.getFileExtension().length());
        }
        //去除前缀
        tempNutzFileName = tempNutzFileName.substring(nutzTemplate.getName().length());
        //补上文件后缀
        tempNutzFileName = tempNutzFileName.replace(".", "/") + nutzTemplate.getFileExtension();
        String finalTempNutzFileName = tempNutzFileName;
        virtualFiles.stream().filter(virtualFile -> virtualFile.getCanonicalPath().endsWith(finalTempNutzFileName))
                .forEach(virtualFile -> fileList.add(virtualFile));
        return fileList;
    }

    /**
     * 取得模版文件相对路径
     *
     * @param bindingElement
     * @return
     */
    public static TemplateVO getTemplateFilePathAndName(PsiElement bindingElement) {
        PsiLiteralExpression literalExpression = (PsiLiteralExpression) bindingElement;
        String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
        if (value != null) {
            TemplateVO templateVO = configuration.getTemplate(value);
            if (templateVO != null) {
                templateVO.setTemplatePath(value);
            }
            return templateVO;
        }
        return null;
    }


    /**
     * 设置错误信息
     *
     * @param psiElement
     * @return
     */
    public static void checkError(PsiElement psiElement, List<VirtualFile> fileList) {
        if (fileList == null || fileList.size() <= 0) {
            JOptionPane.showMessageDialog(null, "没有找到这个模版文件，请检查！", "错误提示", JOptionPane.ERROR_MESSAGE, null);
        }
    }

}
