package com.sgaop.idea.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlTag;
import com.sgaop.util.DomUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/3
 */
public class XmlExpTagAnnotator extends AbstractSqlXmlAnnotator {
    static final String EXP_TAG = "exp";

    @Override
    public boolean isSqlsXml(@NotNull PsiElement psiElement) {
        return DomUtil.isNutzSqlFile(psiElement.getContainingFile());
    }

    @Override
    public void process(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
//        if (psiElement instanceof XmlTag) {
//            XmlTag tag = (XmlTag) psiElement;
//            if (EXP_TAG.equals(tag.getName())) {
//              List<PsiElement> els=  DomUtil.findXmlTexts(psiElement.getChildren());
//                els.forEach(element -> {
//                    annotationHolder.createInfoAnnotation(element.getTextRange(),"呵呵");
//                });
//            }
//        }
    }
}
