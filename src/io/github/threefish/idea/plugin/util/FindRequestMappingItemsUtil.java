package io.github.threefish.idea.plugin.util;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.search.GlobalSearchScope;
import io.github.threefish.idea.plugin.NutzCons;
import io.github.threefish.idea.plugin.enums.NutzApiMethodType;
import io.github.threefish.idea.plugin.gotosymbol.AtMappingNavigationItem;
import io.github.threefish.idea.plugin.gotosymbol.FindAtMappingCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2018/11/27
 * <p>
 * 此类可以优化
 */
public class FindRequestMappingItemsUtil {

    /**
     * 最大尝试次数
     */
    private static final int TRY_NUM_MAX = 20;

    public static FindAtMappingCache findAtMappingCache = new FindAtMappingCache();


    public static List<AtMappingNavigationItem> findRequestMappingItems(Module module) {
        return findRequestMappingItems(module.getProject(), module.getModuleScope());
    }

    public static List<AtMappingNavigationItem> findRequestMappingItems(Project project) {

        return findRequestMappingItems(project, GlobalSearchScope.projectScope(project));
    }


    private static List<AtMappingNavigationItem> findRequestMappingItems(Project project, GlobalSearchScope searchScope) {
        try {
            Collection<PsiAnnotation> psiAnnotations = JavaAnnotationIndex.getInstance().get("At", project, searchScope);
            List<AtMappingNavigationItem> mappingAnnotation = new ArrayList<>();
            for (PsiAnnotation annotation : psiAnnotations) {
                if (!NutzCons.AT.equals(annotation.getQualifiedName())) {
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
                                String[] topReqs = getTopAt(at);
                                PsiAnnotationMemberValue va = nameValuePair.getValue();
                                if (va instanceof PsiLiteralExpression) {
                                    //单个映射值
                                    String requestPath = nameValuePair.getLiteralValue();
                                    for (String topReq : topReqs) {
                                        if (topReq != null) {
                                            mappingAnnotation.add(new AtMappingNavigationItem(annotation, topReq, requestPath, getMethodType(methodAanotations)));
                                        }
                                    }
                                } else if (va instanceof PsiAnnotationMemberValue) {
                                    //多个映射值
                                    PsiArrayInitializerMemberValue memberValue = (PsiArrayInitializerMemberValue) nameValuePair.getValue();
                                    PsiAnnotationMemberValue[] values = memberValue.getInitializers();
                                    for (PsiAnnotationMemberValue value : values) {
                                        PsiLiteralExpression val = (PsiLiteralExpression) value;
                                        String requestPath = (String) val.getValue();
                                        for (String topReq : topReqs) {
                                            if (topReq != null) {
                                                mappingAnnotation.add(new AtMappingNavigationItem(annotation, topReq, requestPath, getMethodType(methodAanotations)));
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            PsiElement psiMethod = at.getParent().getParent().getParent();
                            if (psiMethod instanceof PsiMethod) {
                                String[] topReqs = getTopAt(at);
                                String requestPath = "/" + ((PsiMethod) psiMethod).getName().toLowerCase();
                                for (String topReq : topReqs) {
                                    if (topReq != null) {
                                        mappingAnnotation.add(new AtMappingNavigationItem(annotation, topReq, requestPath, getMethodType(methodAanotations)));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return mappingAnnotation;
        } catch (Exception ex) {
            ex.printStackTrace();
            //过滤异常不提示
        }
        return null;
    }

    private static NutzApiMethodType getMethodType(PsiAnnotation[] psiAnnotations) {
        for (PsiAnnotation annotation : psiAnnotations) {
            String qualifiedName = annotation.getQualifiedName();
            if (NutzCons.GET.equals(qualifiedName)) {
                return NutzApiMethodType.GET;
            }
            if (NutzCons.POST.equals(qualifiedName)) {
                return NutzApiMethodType.POST;
            }
            if (NutzCons.DELETE.equals(qualifiedName)) {
                return NutzApiMethodType.DELETE;
            }
            if (NutzCons.PUT.equals(qualifiedName)) {
                return NutzApiMethodType.PUT;
            }
        }
        return NutzApiMethodType.GET;
    }

    private static String[] getTopAt(PsiElement at) {
        String[] topReqs = new String[1];
        //提前赋值避免出现Null
        topReqs[0] = "";
        try {
            PsiElement psiMethod = at.getParent().getParent().getParent().getParent();
            if (psiMethod instanceof PsiClass) {
                String moduleName = ((PsiClass) psiMethod).getName().toLowerCase();
                PsiElement psiElement = psiMethod.getFirstChild();
                int tryNum = 0;
                while (!(psiElement instanceof PsiModifierList) && tryNum < TRY_NUM_MAX) {
                    psiElement = psiElement.getNextSibling();
                    tryNum++;
                }
                PsiModifierList psiModifierList = (PsiModifierList) psiElement;
                PsiAnnotation[] classAnnotations = psiModifierList.getAnnotations();
                for (PsiAnnotation classAnnotation : classAnnotations) {
                    if (NutzCons.AT.equals(classAnnotation.getQualifiedName())) {
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
        } catch (Exception ex) {
            //过滤异常不提示
        }
        return topReqs;
    }
}
