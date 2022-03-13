package io.github.threefish.idea.plugin.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2019/4/2
 */
public abstract class AbstractSqlXmlAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (isSqlsXml(psiElement)) {
            process(psiElement, annotationHolder);
        }
    }

    /**
     * @param psiElement
     * @return
     */
    public abstract boolean isSqlsXml(@NotNull PsiElement psiElement);

    /**
     * @param psiElement
     * @param annotationHolder
     */
    public abstract void process(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder);
}
