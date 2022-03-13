package io.github.threefish.idea.plugin.util;

import com.intellij.lang.properties.IProperty;
import com.intellij.lang.properties.psi.impl.PropertiesFileImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.java.PsiAnnotationImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * <p>
 * 创建时间: 2018/1/3  14:26
 */
public class NutzInjectConfUtil {

    public static final Pattern PATTERN = Pattern.compile("java\\:\\$conf\\.get\\('(.*?)'\\)");
    private static final List<String> QUALIFIED_NAMES = Arrays.asList("org.nutz.ioc.loader.annotation.Inject");

    public static List<String> findProperties(Project project, Collection<VirtualFile> virtualFiles, String key) {
        return PsiFileUtil.findProperties(project, virtualFiles, key);
    }


    public static List<PsiElement> findPropertiesPsiElement(Project project, Collection<VirtualFile> virtualFiles, String key) {
        List<PsiElement> result = new ArrayList<>();
        for (VirtualFile virtualFile : virtualFiles) {
            PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
            List<IProperty> iProperties = ((PropertiesFileImpl) psiFile).getProperties();
            iProperties.forEach(iProperty -> {
                if (iProperty.getKey().equals(key)) {
                    result.add(iProperty.getPsiElement());
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
        return QUALIFIED_NAMES.contains(name);
    }

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
