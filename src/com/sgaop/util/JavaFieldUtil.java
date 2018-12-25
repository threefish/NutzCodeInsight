package com.sgaop.util;

import com.intellij.lang.jvm.annotation.JvmAnnotationAttribute;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiField;
import com.intellij.psi.impl.source.tree.java.PsiNameValuePairImpl;

import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/12/24
 */
public class JavaFieldUtil {

    public static final String[] IS_FIELD = {"org.nutz.dao.entity.annotation.Name", "org.nutz.dao.entity.annotation.Id", "org.nutz.dao.entity.annotation.Column"};

    private static final String COMMENT = "org.nutz.dao.entity.annotation.Comment";

    private static final String DICT = "com.nutzfw.annotation.Dict";

    public static boolean isDate(PsiField field) {
        String type = field.getType().getPresentableText();
        return "Date".equals(type) || "Timestamp".equals(type);
    }

    public static boolean isPrimaryKey(PsiField field) {
        PsiAnnotation annotation0 = field.getAnnotation(IS_FIELD[0]);
        PsiAnnotation annotation1 = field.getAnnotation(IS_FIELD[1]);
        return annotation0 != null || annotation1 != null;
    }


    public static String getDbNameAndIsColumn(PsiField field) {
        PsiAnnotation annotation0 = field.getAnnotation(IS_FIELD[0]);
        PsiAnnotation annotation1 = field.getAnnotation(IS_FIELD[1]);
        PsiAnnotation annotation2 = field.getAnnotation(IS_FIELD[2]);
        boolean isField = annotation0 != null || annotation1 != null || annotation2 != null;
        if (isField) {
            List<JvmAnnotationAttribute> attributes = annotation2.getAttributes();
            if (attributes.size() == 0) {
                return field.getName();
            } else {
                return ((PsiNameValuePairImpl) attributes.get(0)).getLiteralValue();
            }
        }
        return null;
    }

    public static String getComment(PsiField field) {
        PsiAnnotation annotation = field.getAnnotation(COMMENT);
        if (annotation != null) {
            List<JvmAnnotationAttribute> attributes = annotation.getAttributes();
            if (attributes.size() > 0) {
                return ((PsiNameValuePairImpl) attributes.get(0)).getLiteralValue();
            }
        }
        return "";
    }

    public static String getDictCode(PsiField field) {
        PsiAnnotation annotation = field.getAnnotation(DICT);
        if (annotation != null) {
            List<JvmAnnotationAttribute> attributes = annotation.getAttributes();
            if (attributes.size() > 0) {
                return ((PsiNameValuePairImpl) attributes.get(0)).getLiteralValue();
            }
        }
        return null;
    }


}
