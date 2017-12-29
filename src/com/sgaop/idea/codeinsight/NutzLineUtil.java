package com.sgaop.idea.codeinsight;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiUtilBase;

import javax.swing.*;
import java.awt.*;
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
        if (!"Ok".equals(psiJavaCodeReferenceElement.getText())) {
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
        String nutzFileName = getTemplateFilePathAndName(bindingElement);
        Collection<VirtualFile> virtualFiles = FilenameIndex.getAllFilesByExt(bindingElement.getProject(), "jsp", GlobalSearchScope.allScope(bindingElement.getProject()));
        List<VirtualFile> fileList = new ArrayList<>();
        virtualFiles.stream().filter(virtualFile -> {
            String path = virtualFile.getCanonicalPath();
            String tempNutzFileName = nutzFileName.replace(".", "/") + ".jsp";
            if (path.endsWith(tempNutzFileName)) {
                return true;
            } else {
                return false;
            }
        }).forEach(virtualFile -> fileList.add(virtualFile));
        return fileList;
    }

    /**
     * 取得模版文件相对路径
     *
     * @param bindingElement
     * @return
     */
    private static String getTemplateFilePathAndName(PsiElement bindingElement) {
        PsiLiteralExpression literalExpression = (PsiLiteralExpression) bindingElement;
        String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
        if (value != null && value.startsWith("jsp:")) {
            value = value.substring(4);
            return value;
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
