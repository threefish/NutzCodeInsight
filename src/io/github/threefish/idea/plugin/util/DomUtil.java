package io.github.threefish.idea.plugin.util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlText;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2019/4/1
 */
public class DomUtil {

    public static boolean isNutzSqlFile(PsiFile file) {
        if (!isXmlFile(file)) {
            return false;
        }
        XmlTag rootTag = ((XmlFile) file).getRootTag();
        return null != rootTag && "Sqls".equals(rootTag.getName());
    }

    static boolean isXmlFile(@NotNull PsiFile file) {
        return file instanceof XmlFile;
    }

    public static List<PsiElement> findXmlTexts(PsiElement[] psiElements) {
        List<PsiElement> xmlTexts = new ArrayList<>();
        for (PsiElement psiElement : psiElements) {
            if (psiElement instanceof XmlText) {
                xmlTexts.add(psiElement);
            }
        }
        return xmlTexts;
    }
}
