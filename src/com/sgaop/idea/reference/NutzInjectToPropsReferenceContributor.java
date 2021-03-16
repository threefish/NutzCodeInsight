package com.sgaop.idea.reference;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.ProcessingContext;
import com.sgaop.util.NutzInjectConfUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/9/30
 */
public class NutzInjectToPropsReferenceContributor extends PsiReferenceContributor {

    private static final Logger LOG = Logger.getInstance(NutzInjectToPropsReferenceContributor.class);

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar psiReferenceRegistrar) {
        psiReferenceRegistrar.registerReferenceProvider(PlatformPatterns.psiElement(PsiLiteralExpression.class), new NutzInjectPsiReferenceProvider());
    }

    class NutzInjectPsiReferenceProvider extends PsiReferenceProvider {
        @NotNull
        @Override
        public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext processingContext) {
            try {
                PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
                String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
                String key = NutzInjectConfUtil.getKey(value);
                if (NutzInjectConfUtil.isInjectConf(literalExpression) && key != null) {
                    Project project = element.getProject();
                    final Collection<VirtualFile> propertiesFiles = FilenameIndex.getAllFilesByExt(project, "properties", getSearchScope(project, element));
                    final List<PsiElement> properties = NutzInjectConfUtil.findPropertiesPsiElement(project, propertiesFiles, key);
                    List<PsiReference> psiReferences = new ArrayList<>();
                    properties.forEach(psiElement -> psiReferences.add(new PsiJavaInjectReference(element, psiElement)));
                    return psiReferences.toArray(new PsiReference[0]);
                }
            } catch (Exception e) {
                LOG.warn(e);
            }
            return PsiReference.EMPTY_ARRAY;
        }

        private GlobalSearchScope getSearchScope(Project project, @NotNull PsiElement element) {
            GlobalSearchScope searchScope = GlobalSearchScope.projectScope(project);
            Module module = ProjectRootManager.getInstance(project).getFileIndex().getModuleForFile(element.getContainingFile().getVirtualFile());
            if (module != null) {
                searchScope = GlobalSearchScope.moduleWithDependenciesScope(module);
            }
            return searchScope;
        }
    }
}
