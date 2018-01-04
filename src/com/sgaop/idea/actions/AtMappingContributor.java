package com.sgaop.idea.actions;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
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
public class AtMappingContributor implements ChooseByNameContributor {

    private List<AtMappingItem> navigationItems = new ArrayList<>();

    @NotNull
    @Override
    public String[] getNames(Project project, boolean b) {
        navigationItems.clear();
        navigationItems.addAll(findRequestMappingItems(project, "At"));
        return navigationItems.stream().map(atMappingItem -> atMappingItem.getName()).distinct().toArray(String[]::new);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean b) {
        return navigationItems.stream().filter(it -> !it.getName().equals(pattern)).toArray(NavigationItem[]::new);
    }

    private List<AtMappingItem> findRequestMappingItems(Project project, String annotationName) {
        try {
            Collection<PsiAnnotation> psiAnnotations = JavaAnnotationIndex.getInstance().get(annotationName, project, GlobalSearchScope.allScope(project));
            List<AtMappingItem> mappingAnnotation = new ArrayList<>();
            for (PsiAnnotation annotation : psiAnnotations) {
                if (!"org.nutz.mvc.annotation.At".equals(annotation.getQualifiedName())) {
                    continue;
                }
                PsiElement psiElement = annotation.getParent();
                if (psiElement instanceof PsiModifierList && psiElement.getParent() instanceof PsiMethod && psiElement.getText() != null && psiElement.getText().indexOf("@IocBean") == -1) {
                    PsiElement[] psiElements = annotation.getChildren();
                    if (psiElements.length == 3) {
                        PsiElement at = psiElements[2];
                        PsiAnnotation[] methodAanotations = ((PsiModifierList) at.getParent().getParent()).getAnnotations();
                        PsiNameValuePair[] nameValuePairs = ((PsiAnnotationParameterList) at).getAttributes();
                        if (nameValuePairs.length == 1) {
                            PsiNameValuePair nameValuePair = nameValuePairs[0];
                            if (nameValuePair != null) {
                                String[] topReqs = this.getTopAt(at);
                                PsiAnnotationMemberValue va = nameValuePair.getValue();
                                if (va instanceof PsiLiteralExpression) {
                                    //单个映射值
                                    String requestPath = nameValuePair.getLiteralValue();
                                    this.addMappingItem(mappingAnnotation, topReqs, requestPath, annotation, getMethodType(methodAanotations));
                                } else if (va instanceof PsiAnnotationMemberValue) {
                                    //多个映射值
                                    PsiArrayInitializerMemberValue memberValue = (PsiArrayInitializerMemberValue) nameValuePair.getValue();
                                    PsiAnnotationMemberValue[] values = memberValue.getInitializers();
                                    for (PsiAnnotationMemberValue value : values) {
                                        PsiLiteralExpression val = (PsiLiteralExpression) value;
                                        String requestPath = (String) val.getValue();
                                        this.addMappingItem(mappingAnnotation, topReqs, requestPath, annotation, getMethodType(methodAanotations));
                                    }
                                }
                            }
                        } else {
                            PsiElement psiMethod = at.getParent().getParent().getParent();
                            if (psiMethod instanceof PsiMethod) {
                                String[] topReqs = this.getTopAt(at);
                                String requestPath = "/" + ((PsiMethod) psiMethod).getName().toLowerCase();
                                this.addMappingItem(mappingAnnotation, topReqs, requestPath, annotation, getMethodType(methodAanotations));
                            }
                        }
                    }
                }
            }
            return mappingAnnotation;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    private String getMethodType(PsiAnnotation[] psiAnnotations) {
        StringBuilder sb = new StringBuilder();
        for (PsiAnnotation annotation : psiAnnotations) {
            String qualifiedName = annotation.getQualifiedName();
            if ("org.nutz.mvc.annotation.GET".equals(qualifiedName)) {
                sb.append(" GET");
            }
            if ("org.nutz.mvc.annotation.POST".equals(qualifiedName)) {
                sb.append(" POST");
            }
            if ("org.nutz.mvc.annotation.DELETE".equals(qualifiedName)) {
                sb.append(" DELETE");
            }
            if ("org.nutz.mvc.annotation.PUT".equals(qualifiedName)) {
                sb.append(" PUT");
            }
        }
        if (sb.length() == 0) {
            sb.append(" GET");
        }
        return sb.toString();
    }

    private static final int max = 200;

    private String[] getTopAt(PsiElement at) {
        String[] topReqs = new String[1];
        topReqs[0] = "";   //避免出现Null
        PsiElement psiMethod = at.getParent().getParent().getParent().getParent();
        if (psiMethod instanceof PsiClass) {
            String moduleName = ((PsiClass) psiMethod).getName().toLowerCase();
            PsiElement psiElement = psiMethod.getFirstChild();
            int tryNum = 0;
            while (!(psiElement instanceof PsiModifierList) && tryNum < max) {
                psiElement = psiElement.getNextSibling();
                tryNum++;
            }
            PsiModifierList psiModifierList = (PsiModifierList) psiElement;
            PsiAnnotation[] classAnnotations = psiModifierList.getAnnotations();
            for (PsiAnnotation classAnnotation : classAnnotations) {
                if (classAnnotation.getQualifiedName().equals("org.nutz.mvc.annotation.At")) {
                    PsiNameValuePair[] pairs = classAnnotation.getParameterList().getAttributes();
                    if (pairs.length == 0) {
                        topReqs[0] = "/" + moduleName;
                    } else {
                        topReqs = new String[pairs.length];
                        for (int i = 0; i < pairs.length; i++) {
                            topReqs[i] = pairs[i].getLiteralValue();
                        }
                    }
                }
            }
        }
        return topReqs;
    }


    private void addMappingItem(List<AtMappingItem> mappingAnnotation, String[] topReqs, String requestPath, PsiElement annotation, String requestMethod) {
        for (String topReq : topReqs) {
            if (topReq != null) {
                mappingAnnotation.add(new AtMappingItem(annotation, topReq + requestPath, requestMethod));
            }
        }
    }
}
