package com.sgaop.idea.linemarker.navigation;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.java.stubs.index.JavaShortClassNameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.sgaop.util.SqlsXmlUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/30
 */
public class Sqls2XmlNavigationHandler extends AbstractPsiElementNavigationHandler {
    @Override
    public boolean canNavigate(PsiElement psiElement) {
        return true;
    }

    @Override
    public List<PsiElement> findReferences(PsiElement psiElement) {
        XmlTag xmlTag = (XmlTag) psiElement;
        XmlAttribute xmlAttribute = xmlTag.getAttribute("id");
        if (xmlTag.getName().equals("sql") && Objects.nonNull(xmlAttribute)) {
            String id = xmlAttribute.getValue();
            PsiClass javaPsiClass = findJavaPsiClass(psiElement);
            if (Objects.nonNull(javaPsiClass)) {
                return SqlsXmlUtil.findJavaPsiElement(javaPsiClass, id);
            }
        }
        return Arrays.asList();
    }

    private PsiClass findJavaPsiClass(PsiElement psiElement) {
        XmlTag parentOfType = PsiTreeUtil.getParentOfType(psiElement, XmlTag.class);
        if (parentOfType.getName().equals("Sqls")) {
            XmlAttribute aClass = parentOfType.getAttribute("class");
            if (Objects.nonNull(aClass)) {
                String value = aClass.getValue();
                String[] split = value.split("\\.");
                Collection<PsiClass> roleBizImpl = JavaShortClassNameIndex.getInstance().get(split[split.length - 1], psiElement.getProject(), GlobalSearchScope.projectScope(psiElement.getProject()));
                for (PsiClass psiClass : roleBizImpl) {
                    if (psiClass.getQualifiedName().equals(value)) {
                        return psiClass;
                    }
                }
            }
        }
        return null;
    }
}
