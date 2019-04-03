package com.sgaop.idea.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.sgaop.util.SqlsXmlLineUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/2
 */
public class JavaSqlXmlAnnotator extends AbstractSqlXmlAnnotator {

    static final String errMsg = "模版文件未找到";

    @Override
    public boolean isSqlsXml(@NotNull PsiElement psiElement) {
        return SqlsXmlLineUtil.isSqlsXml(psiElement);
    }

    /**
     * 没有找到模板文件报错
     *
     * @param psiElement
     * @param annotationHolder
     */
    @Override
    public void process(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        List<VirtualFile> virtualFileList = SqlsXmlLineUtil.findTemplteFileList(psiElement);
        if (virtualFileList.size() == 0) {
            annotationHolder.createErrorAnnotation(psiElement.getTextRange(), errMsg);
        }
    }
}
