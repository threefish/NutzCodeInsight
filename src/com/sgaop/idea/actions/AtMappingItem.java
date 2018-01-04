package com.sgaop.idea.actions;

import com.intellij.icons.AllIcons;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * @date 2017/12/30 0030 23:53
 */
public class AtMappingItem implements NavigationItem {

    private PsiElement psiElement;
    private Navigatable navigatable;
    private String urlPath;
    private String requestMethod;

    public AtMappingItem(PsiElement psiElement, String urlPath, String requestMethod) {
        this.psiElement = psiElement;
        this.requestMethod = requestMethod;
        this.urlPath = urlPath.replaceAll("//","/");
        this.navigatable = (Navigatable) psiElement;
    }

    @Nullable
    @Override
    public String getName() {
        return this.urlPath + " " + this.requestMethod;
    }


    @Override
    public void navigate(boolean b) {
        navigatable.navigate(b);
    }

    @Override
    public boolean canNavigate() {
        return navigatable.canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return true;
    }

    @Nullable
    @Override
    public ItemPresentation getPresentation() {

        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return urlPath + " " + requestMethod;
            }

            @Nullable
            @Override
            public String getLocationString() {
                return psiElement.getContainingFile().getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean b) {
                return AllIcons.FileTypes.JavaClass;
            }
        };
    }

}
