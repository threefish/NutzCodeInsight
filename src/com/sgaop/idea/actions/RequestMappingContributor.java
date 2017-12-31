package com.sgaop.idea.actions;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.impl.source.PsiModifierListImpl;
import com.intellij.psi.impl.source.tree.java.PsiNameValuePairImpl;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        navigationItems.clear();
        navigationItems.addAll(findRequestMappingItems(project, "At"));
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
        for (PsiAnnotation annotation : psiAnnotations) {
            PsiElement[] psiElements = annotation.getChildren();
            if (psiElements.length == 3) {
                PsiElement at = psiElements[2];
                if (at instanceof PsiAnnotationParameterList) {
                    String topReq="";
                    PsiAnnotation[] classAnnotations = ((PsiModifierListImpl) at.getParent().getParent()).getAnnotations();
                    if(isIocBean(classAnnotations)){
                        for (PsiAnnotation classAnnotation : classAnnotations) {
                            if (classAnnotation.getQualifiedName().equals("org.nutz.mvc.annotation.At")) {
                                System.out.println(classAnnotation.getText());
                                System.out.println(classAnnotation.getParameterList().getAttributes()[0].getLiteralValue());
                            }
                        }
                    }
                    PsiNameValuePair[] nameValuePairs = ((PsiAnnotationParameterList) at).getAttributes();
                    if (nameValuePairs.length == 1) {
                        PsiNameValuePair nameValuePair = nameValuePairs[0];
                        String requestPath = nameValuePair.getLiteralValue();
                        if (requestPath == null && nameValuePair != null && nameValuePair.getValue() instanceof PsiArrayInitializerMemberValue) {
                            PsiArrayInitializerMemberValue memberValue = (PsiArrayInitializerMemberValue) nameValuePair.getValue();
                            PsiAnnotationMemberValue[] values = memberValue.getInitializers();
                            for (PsiAnnotationMemberValue value : values) {
                                PsiLiteralExpression val = (PsiLiteralExpression) value;
                                requestPath = (String) val.getValue();
                                mappingAnnotation.add(new RequestMappingItem(annotation, annotationName, requestPath));
                            }
                        } else {
                            mappingAnnotation.add(new RequestMappingItem(annotation, annotationName, requestPath));
                        }
                    }
                } else {
                    System.out.println(at.getText());
                }
            }
        }
        return mappingAnnotation;
    }


    private boolean isIocBean(PsiAnnotation[] classAnnotations){
        for (PsiAnnotation classAnnotation : classAnnotations) {
            if ("@IocBean".equals(classAnnotation.getText())) {
               return true;
            }
        }
        return false;
    }
}
