package com.sgaop.idea.codeinsight.util;

import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlToken;
import com.sgaop.idea.codeinsight.folding.NutzLocalizationFoldingDescriptor;
import com.sgaop.project.ToolCfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/3/26
 */
public class BeetlHtmlLineUtil {

    private static ToolCfiguration cfiguration = ToolCfiguration.getInstance();

    private static final Pattern FUN_PATTERN = Pattern.compile("\\((\"|')(.*?)\\.html(\"|')\\)");

    public static List<FoldingDescriptor> showNutzLocalization(Project project, PsiElement root) {
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        String localizationPackage = NutzLocalUtil.getLocalizationPackage(project);
        if (null == localizationPackage) {
            return new ArrayList<>();
        }
        Collection<VirtualFile> propertiesFiles = FilenameIndex.getAllFilesByExt(project, "properties", GlobalSearchScope.projectScope(project));
        Collection<XmlToken> xmlTokens = PsiTreeUtil.findChildrenOfType(root, XmlToken.class);
        Collection<LeafPsiElement> LeafPsiElements = PsiTreeUtil.findChildrenOfType(root, LeafPsiElement.class);
        for (final LeafPsiElement leafPsiElement : LeafPsiElements) {
            if ("Language: JavaScript".equals(leafPsiElement.getLanguage().toString())) {
                String text = leafPsiElement.getText();
                Matcher m = Pattern.compile(cfiguration.getSettingVO().getI18nRegular()).matcher(text);
                descriptors.addAll(getFoldingDescriptor(m, text, project, propertiesFiles, localizationPackage, leafPsiElement));
            }
        }
        for (final XmlToken xmlToken : xmlTokens) {
            String text = xmlToken.getText();
            Matcher m = Pattern.compile(cfiguration.getSettingVO().getI18nRegular()).matcher(text);
            descriptors.addAll(getFoldingDescriptor(m, text, project, propertiesFiles, localizationPackage, xmlToken));
        }
        return descriptors;
    }

    private static List<FoldingDescriptor> getFoldingDescriptor(Matcher matcher, String text, Project project, Collection<VirtualFile> propertiesFiles, String localizationPackage, PsiElement psiElement) {
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        while (matcher.find()) {
            String key = text.substring(matcher.start() + 8, matcher.end() - 3);
            final List<String> properties = NutzLocalUtil.findProperties(project, propertiesFiles, localizationPackage, key);
            if (properties.size() == 1) {
                int start = psiElement.getTextRange().getStartOffset() + matcher.start() + 8;
                int end = start + key.length();
                descriptors.add(new NutzLocalizationFoldingDescriptor(psiElement.getNode(), new TextRange(start, end), properties.get(0)));
            } else if (properties.size() > 1) {
                int start = psiElement.getTextRange().getStartOffset() + matcher.start() + 8;
                int end = start + key.length();
                descriptors.add(new NutzLocalizationFoldingDescriptor(psiElement.getNode(), new TextRange(start, end), "NutzCodeInsight:当前键值【" + key + "】在国际化信息中存在重复KEY请检查！"));
            }
        }
        return descriptors;
    }


    public static List<String> showBeetlLayout(XmlToken xmlToken) {
        return getMatchers(xmlToken, cfiguration.getSettingVO().getBeetlLayoutRegular());
    }

    public static List<String> showBeetlInclude(XmlToken xmlToken) {
        return getMatchers(xmlToken, cfiguration.getSettingVO().getBeetlIncludeRegular());
    }

    private static List<String> getMatchers(XmlToken xmlToken, String regRegular) {
        List<String> descriptors = new ArrayList<>();
        String text = xmlToken.getText().replace("\r", "").replace("\n", "");
        Pattern layoutPattern = Pattern.compile(regRegular);
        Matcher m = layoutPattern.matcher(text);
        Matcher m2 = FUN_PATTERN.matcher(text);
        while (m.find() && m2.find()) {
            int start = m2.start();
            int end = m2.end();
            String value = text.substring(start + 2, end - 2);
            descriptors.add(value);
        }
        return descriptors;
    }

}
