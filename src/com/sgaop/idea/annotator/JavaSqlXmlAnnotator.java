package com.sgaop.idea.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.sgaop.idea.codeinsight.util.SqlsXmlLineUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/2
 */
public class JavaSqlXmlAnnotator implements Annotator {

    static final String errMsg = "模版文件未找到";

    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (SqlsXmlLineUtil.isSqlsXml(psiElement)) {
            List<VirtualFile> virtualFileList = SqlsXmlLineUtil.findTemplteFileList(psiElement);
            if (virtualFileList.size() == 0) {
                annotationHolder.createErrorAnnotation(psiElement.getTextRange(), errMsg);
            }
        }
    }
}
