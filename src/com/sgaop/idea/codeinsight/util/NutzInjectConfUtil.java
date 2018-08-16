package com.sgaop.idea.codeinsight.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.java.PsiAnnotationImpl;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2018/1/3  14:26
 * 描述此类：
 */
public class NutzInjectConfUtil {

    private static final List<String> qualifiedNames = Arrays.asList("org.nutz.ioc.loader.annotation.Inject");


    public static List<String> findProperties(Project project, Collection<VirtualFile> virtualFiles, String key) {
        List<String> result = new ArrayList<>();
        for (VirtualFile virtualFile : virtualFiles) {
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


    public static boolean isInjectConf(PsiLiteralExpression literalExpression) {
        PsiElement p1 = literalExpression.getParent();
        if (null == p1 || !(p1 instanceof PsiNameValuePair)) {
            return false;
        }
        PsiElement p2 = p1.getParent();
        if (null == p2 || !(p2 instanceof PsiAnnotationParameterList)) {
            return false;
        }
        PsiElement p3 = p2.getParent();
        if (null == p2 || !(p3 instanceof PsiAnnotation)) {
            return false;
        }
        String name = ((PsiAnnotationImpl) p3).getNameReferenceElement().getQualifiedName();
        if (qualifiedNames.contains(name)) {
            return true;
        }
        return false;
    }

    public static final Pattern PATTERN = Pattern.compile("java\\:\\$conf\\.get\\(\'(.*?)\'\\)");

    public static String getKey(String value) {
        if (value == null) {
            return null;
        }
        Matcher matcher = PATTERN.matcher(value);
        while (matcher.find()) {
            String key = value.substring(matcher.start() + 16, matcher.end() - 2);
            return key;
        }
        return null;
    }

}