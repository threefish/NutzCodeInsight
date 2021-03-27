package com.sgaop.idea.annotator;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.sgaop.idea.intention.GenerateSqlXmlIntention;
import com.sgaop.util.SqlsXmlUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2019/4/2
 */
public class JavaSqlXmlAnnotator extends AbstractSqlXmlAnnotator {

    static final String ERR_MSG = "模版文件未找到";

    @Override
    public boolean isSqlsXml(@NotNull PsiElement psiElement) {
        return SqlsXmlUtil.isSqlsXml(psiElement);
    }

    /**
     * 没有找到模板文件报错
     *
     * @param psiElement
     * @param annotationHolder
     */
    @Override
    public void process(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        List<VirtualFile> virtualFileList = SqlsXmlUtil.findTemplteFileList(psiElement);
        if (virtualFileList.size() == 0) {
            TextRange textRange;
            if (psiElement.getNextSibling().getChildren().length == 0) {
                textRange = psiElement.getTextRange();
            } else {
                textRange = new TextRange(psiElement.getTextRange().getStartOffset(), psiElement.getNextSibling().getTextRange().getEndOffset());
            }
            Annotation annotation = new Annotation(textRange.getStartOffset(), textRange.getEndOffset(), HighlightSeverity.ERROR, ERR_MSG, null);
            annotation.registerFix(new GenerateSqlXmlIntention());
        }
    }
}
