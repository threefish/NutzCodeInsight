package com.sgaop.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.impl.source.tree.java.PsiAnnotationParamListImpl;
import com.intellij.psi.search.GlobalSearchScope;
import io.github.threefish.idea.NutzCons;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * <p>
 * 创建时间: 2018/1/3  14:26
 */
public class NutzLocalUtil {

    private static final List<String> LOCAL_QUALIFIED_NAMES = Arrays.asList(
            "Mvcs.getMessage",
            "org.nutz.mvc.Mvcs.getMessage",
            "MvcI18n.message",
            "MvcI18n.messageOrDefaultFormat",
            "MvcI18n.messageOrDefault",
            "org.nutz.mvc.MvcI18n.message",
            "org.nutz.mvc.MvcI18n.messageOrDefaultFormat",
            "org.nutz.mvc.MvcI18n.messageOrDefault");
    /**
     * Nutz默认语言包
     */
    private static String localizationPackage;
    /**
     * 是否初始化
     */
    private static boolean isInit = false;

    public static List<String> findProperties(Project project, Collection<VirtualFile> virtualFiles, String localizationPackage, String key) {
        List<String> result = PsiFileUtil.findProperties(project, virtualFiles, key);
        for (VirtualFile virtualFile : virtualFiles) {
            if (virtualFile.toString().indexOf(localizationPackage) == -1) {
                continue;
            }
            PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
            Properties properties = new Properties();
            try (StringReader reader = new StringReader(psiFile.getText())) {
                properties.load(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
            properties.forEach((name, val) -> {
                if (name.equals(key)) {
                    result.add(String.valueOf(val));
                }
            });
        }
        return result;
    }


    public static boolean isLocal(PsiLiteralExpression literalExpression) {
        if (null == literalExpression.getParent()) {
            return false;
        }
        PsiElement psiMethodCallExpression = literalExpression.getParent().getParent();
        if (psiMethodCallExpression instanceof PsiMethodCallExpression) {
            PsiMethodCallExpression methodCallExpression = (PsiMethodCallExpression) psiMethodCallExpression;
            if (methodCallExpression.getMethodExpression() == null) {
                return false;
            }
            String qualifiedName = methodCallExpression.getMethodExpression().getQualifiedName();
            return LOCAL_QUALIFIED_NAMES.contains(qualifiedName);
        }
        return false;
    }


    public static String getLocalizationPackage(Project project) {
        if (!isInit) {
            Collection<PsiAnnotation> psiAnnotations = JavaAnnotationIndex.getInstance().get("Localization", project, GlobalSearchScope.projectScope(project));
            for (PsiAnnotation annotation : psiAnnotations) {
                if (!NutzCons.LOCALIZATION.equals(annotation.getQualifiedName())) {
                    continue;
                }
                PsiElement[] list = annotation.getChildren();
                for (PsiElement element : list) {
                    if (element instanceof PsiAnnotationParamListImpl) {
                        PsiNameValuePair[] valuePairs = ((PsiAnnotationParamListImpl) element).getAttributes();
                        for (PsiNameValuePair nv : valuePairs) {
                            if ("value".equals(nv.getName())) {
                                if (nv.getLiteralValue() != null) {
                                    localizationPackage = nv.getLiteralValue();
                                }
                            }
                        }
                        for (PsiNameValuePair nv : valuePairs) {
                            if ("defaultLocalizationKey".equals(nv.getName())) {
                                String lang = nv.getLiteralValue();
                                if (lang != null) {
                                    localizationPackage = localizationPackage.replace("//", "/");
                                    if (localizationPackage.endsWith("/")) {
                                        localizationPackage = localizationPackage + lang;
                                    } else {
                                        localizationPackage = localizationPackage + "/" + lang;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (localizationPackage == null) {
            return null;
        } else {
            isInit = true;
            return localizationPackage;
        }
    }
}
