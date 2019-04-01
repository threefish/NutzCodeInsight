package com.sgaop.util;

import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/1
 */
public class DomUtils {

    public static boolean isNutzSqlFile(PsiFile file) {
        if (!isXmlFile(file)) {
            return false;
        }
        XmlTag rootTag = ((XmlFile) file).getRootTag();
        return null != rootTag && rootTag.getName().equals("Sqls");
    }

    static boolean isXmlFile(@NotNull PsiFile file) {
        return file instanceof XmlFile;
    }
}
