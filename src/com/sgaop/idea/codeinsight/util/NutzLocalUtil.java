package com.sgaop.idea.codeinsight.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class NutzLocalUtil {

    private static final List<String> localQualifiedNames = Arrays.asList(
            "Mvcs.getMessage",
            "org.nutz.mvc.Mvcs.getMessage",
            "MvcI18n.message",
            "MvcI18n.messageOrDefaultFormat",
            "MvcI18n.messageOrDefault",
            "org.nutz.mvc.MvcI18n.message",
            "org.nutz.mvc.MvcI18n.messageOrDefaultFormat",
            "org.nutz.mvc.MvcI18n.messageOrDefault");

    public static List<String> findProperties(Project project, Collection<VirtualFile> virtualFiles, String localizationPackage, String key) {
        List<String> result = new ArrayList<>();
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
            if (localQualifiedNames.contains(qualifiedName)) {
                return true;
            }
        }
        return false;
    }
}