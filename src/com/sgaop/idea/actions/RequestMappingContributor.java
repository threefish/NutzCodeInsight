package com.sgaop.idea.actions;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * @date 2017/12/30 0030 23:50
 */
public class RequestMappingContributor implements ChooseByNameContributor {

    private List<RequestMappingItem> navigationItems = new ArrayList<>();

    @NotNull
    @Override
    public String[] getNames(Project project, boolean b) {
        List<String> annotations = new ArrayList<>();
        annotations.add("POST");
        annotations.add("GET");
        annotations.forEach(s -> {
            navigationItems.addAll(findRequestMappingItems(project, s));
        });
        return navigationItems.stream().map(requestMappingItem -> requestMappingItem.getName()).distinct().toArray(String[]::new);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean b) {
        return navigationItems.stream().filter(it -> !it.getName().equals(pattern)).toArray(NavigationItem[]::new);
    }

    private List<RequestMappingItem> findRequestMappingItems(Project project, String annotationName) {
        Collection<PsiAnnotation> psiAnnotations = JavaAnnotationIndex.getInstance().get(annotationName, project, GlobalSearchScope.allScope(project));
        List<RequestMappingItem> mappingAnnotation = new ArrayList<>();
        psiAnnotations.forEach(annotation -> {
//            if (annotation.getContext() instanceof PsiMethod) {
                mappingAnnotation.add(new RequestMappingItem(annotation, annotationName, "xx"));
//            }
        });
        return mappingAnnotation;
    }
}
