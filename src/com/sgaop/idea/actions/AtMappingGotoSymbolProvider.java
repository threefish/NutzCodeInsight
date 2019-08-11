package com.sgaop.idea.actions;

import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.file.impl.JavaFileManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.FindClassUtil;
import com.intellij.util.xml.model.gotosymbol.GoToSymbolProvider;
import com.sgaop.idea.NutzCons;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author huchuc@vip.qq.com
 * @date: 2019/8/11
 */
public class AtMappingGotoSymbolProvider extends GoToSymbolProvider {

    @Override
    protected void addNames(@NotNull Module module, Set<String> result) {
        List<String> temp = Arrays.asList("apm", "huang");
        result.addAll(temp);
    }

    @Override
    protected void addItems(@NotNull Module module, String name, List<NavigationItem> result) {
        PsiClass aClass = JavaFileManager.getInstance(module.getProject()).findClass("com.nutzfw.modules.common.action.FrontAction", GlobalSearchScope.allScope(module.getProject()));
        PsiClass aClass2 = JavaFileManager.getInstance(module.getProject()).findClass("com.nutzfw.core.WebAdminInitSetup", GlobalSearchScope.allScope(module.getProject()));
        List<PsiClass> classes = Arrays.asList(aClass, aClass2);
        classes.forEach(psiClass -> {
            PsiElement navigationElement = psiClass.getNavigationElement();
            NavigationItem navigationItem = new AtMappingNavigationItem(navigationElement, "/apm/huang/" + navigationElement.getContainingFile().getName());
            if (!result.contains(navigationItem)) {
                result.add(navigationItem);
            }
        });
    }

    @NotNull
    @Override
    protected Collection<Module> calcAcceptableModules(@NotNull Project project) {
        return FindClassUtil.findModulesWithClass(project, NutzCons.AT);
    }

    @Override
    protected boolean acceptModule(Module module) {
        return true;
    }
}
