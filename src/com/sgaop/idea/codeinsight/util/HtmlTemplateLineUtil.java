package com.sgaop.idea.codeinsight.util;

import com.google.common.base.Strings;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlAttribute;

import javax.swing.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * @date 2018/01/03  11:37
 */
public class HtmlTemplateLineUtil {

    private static List<String> resNames = Arrays.asList(".js", ".gzjs",".json", ".css", ".png", ".jpg", ".gif");
    private static List<String> resTag = Arrays.asList("src", "href", "img");

    /**
     * 判断是否是资源文件
     *
     * @param bindingElement
     * @return
     */
    public static boolean isRes(PsiElement bindingElement) {
        if (!(bindingElement instanceof XmlAttribute)) {
            return false;
        }
        XmlAttribute attribute = (XmlAttribute) bindingElement;
        String name = Strings.nullToEmpty(attribute.getName());
        String path = Strings.nullToEmpty(attribute.getValue());
        int sp = path.indexOf("?");
        if (sp > -1) {
            path = path.substring(0, sp);
        }
        if (resTag.contains(name) && path.startsWith("${") && endWithRes(path)) {
            return true;
        }
        return false;
    }

    private static boolean endWithRes(String path) {
        Iterator<String> iterable = resNames.iterator();
        while (iterable.hasNext()) {
            if (path.endsWith(iterable.next())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 取得文件
     *
     * @param bindingElement
     * @return
     */
    public static List<VirtualFile> findTemplteFileList(PsiElement bindingElement) {
        String path = getTemplateFilePathAndName(bindingElement);
        Collection<VirtualFile> virtualFiles = FilenameIndex.getAllFilesByExt(bindingElement.getProject(), getFileExtension(path).replaceAll("\\.", ""), GlobalSearchScope.projectScope(bindingElement.getProject()));
        List<VirtualFile> fileList = new ArrayList<>();
        virtualFiles.stream().filter(virtualFile -> virtualFile.getCanonicalPath().endsWith(path))
                .forEach(virtualFile -> fileList.add(virtualFile));
        return fileList;
    }

    /**
     * 取得模版文件相对路径
     *
     * @param bindingElement
     * @return
     */
    public static String getTemplateFilePathAndName(PsiElement bindingElement) {
        XmlAttribute attribute = (XmlAttribute) bindingElement;
        String path = Strings.nullToEmpty(attribute.getValue());
        int sp = path.indexOf("?");
        if (sp > -1) {
            path = path.substring(0, sp);
        }
        int sp2 = path.lastIndexOf("}");
        if (sp2 > -1) {
            path = path.substring(sp2 + 1, path.length());
        }
        return path;
    }


    /**
     * 取得模版图标
     *
     * @param bindingElement
     * @return
     */
    public static Icon getTemplateIcon(PsiElement bindingElement) {
        String path = getTemplateFilePathAndName(bindingElement);
        Iterator<String> iterable = resNames.iterator();
        while (iterable.hasNext()) {
            String fileExtension = iterable.next();
            if (path.endsWith(fileExtension)) {
                return IconUtil.getTemplateIcon(fileExtension);
            }
        }
        return AllIcons.FileTypes.Any_type;
    }


    /**
     * 取得文件类型
     *
     * @param path
     * @return
     */
    public static String getFileExtension(String path) {
        Iterator<String> iterable = resNames.iterator();
        while (iterable.hasNext()) {
            String fileExtension = iterable.next();
            if (path.endsWith(fileExtension)) {
                return fileExtension;
            }
        }
        throw new IllegalStateException("未知的错误！没找到匹配的文件类型！");
    }

}
