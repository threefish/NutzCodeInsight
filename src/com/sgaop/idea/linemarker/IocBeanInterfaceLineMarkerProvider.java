package com.sgaop.idea.linemarker;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.module.impl.scopes.ModuleWithDependenciesScope;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.impl.java.stubs.index.JavaSuperClassNameOccurenceIndex;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.impl.source.PsiFieldImpl;
import com.intellij.psi.impl.source.PsiTypeElementImpl;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTypesUtil;
import com.sgaop.idea.linemarker.navigation.IocBeanInterfaceNavigationHandler;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author huchuc@vip.qq.com
 * @date: 2019/5/23
 */
public class IocBeanInterfaceLineMarkerProvider extends LineMarkerProviderDescriptor {

    static final String INJECT_QUALI_FIED_NAME = "org.nutz.ioc.loader.annotation.Inject";

    static final String IOCBEAN_QUALI_FIED_NAME = "org.nutz.ioc.loader.annotation.IocBean";
    static final int SENSITIVE_MAX = 10;
    static int sensitive = 0;
    static HashMap<String, List<PsiElement>> methodIocBeans = new HashMap<>();
    Icon icon = AllIcons.Javaee.InterceptorClass;

    @Nullable
    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement psiElement) {
        PsiAnnotation psiAnnotation = this.getPsiAnnotation(psiElement);
        if (psiAnnotation != null) {
            GlobalSearchScope moduleScope = ((ModuleWithDependenciesScope) psiElement.getResolveScope()).getModule().getModuleScope();
            this.init(psiElement, moduleScope);
            PsiTypeElementImpl psiTypeElement = this.getPsiTypeElement(psiElement);
            PsiClass psiClass = PsiTypesUtil.getPsiClass(psiTypeElement.getType());
            String name = psiClass.getName();
            List<PsiElement> list = this.getImplListElements(name, psiClass.getQualifiedName(), psiElement, moduleScope);
            if (list.size() > 0) {
                return new LineMarkerInfo<>(psiTypeElement, psiTypeElement.getTextRange(), icon,
                        new FunctionTooltip(MessageFormat.format("快速跳转至 {0} 的 @IocBean 实现类", name)),
                        new IocBeanInterfaceNavigationHandler(name, list),
                        GutterIconRenderer.Alignment.LEFT);
            }
        }
        return null;
    }

    private void init(@NotNull PsiElement psiElement, GlobalSearchScope moduleScope) {
        if (methodIocBeans.size() == 0 && sensitive == 0) {
            methodIocBeans = this.getMethodIocBeans(psiElement.getProject(), moduleScope);
        } else {
            sensitive++;
            if (sensitive >= SENSITIVE_MAX) {
                sensitive = 0;
            }
        }
    }

    /**
     * 取得实现类
     *
     * @param psiElement
     * @return
     */
    private List<PsiElement> getImplListElements(String name, String qualifiedName, PsiElement psiElement, GlobalSearchScope moduleScope) {
        Project project = psiElement.getProject();
        Collection<PsiReferenceList> psiReferenceListCollection = JavaSuperClassNameOccurenceIndex.getInstance().get(name, project, moduleScope);
        List<PsiElement> elements = new ArrayList<>();
        for (PsiReferenceList psiReferenceList : psiReferenceListCollection) {
            PsiClassImpl psiClassImpl = (PsiClassImpl) psiReferenceList.getContext();
            if (psiClassImpl.getAnnotation(IOCBEAN_QUALI_FIED_NAME) != null) {
                elements.add(psiClassImpl);
            }
        }
        methodIocBeans.forEach((returnQualifiedName, psiElements) -> {
            if (Objects.equals(qualifiedName, returnQualifiedName)) {
                elements.addAll(psiElements);
            }
        });
        return elements;
    }

    private HashMap<String, List<PsiElement>> getMethodIocBeans(Project project, GlobalSearchScope moduleScope) {
        HashMap<String, List<PsiElement>> elementHashMap = new HashMap<>(1);
        Collection<PsiAnnotation> psiAnnotationCollection = JavaAnnotationIndex.getInstance().get("IocBean", project, moduleScope);
        for (PsiAnnotation psiAnnotation : psiAnnotationCollection) {
            if (psiAnnotation.getParent() instanceof PsiModifierList) {
                if (psiAnnotation.getParent().getParent() instanceof PsiMethod) {
                    PsiMethod method = (PsiMethod) psiAnnotation.getParent().getParent();
                    String returnQualifiedName = method.getReturnType().getCanonicalText();
                    if (elementHashMap.containsKey(returnQualifiedName)) {
                        elementHashMap.get(returnQualifiedName).add(method);
                    } else {
                        List<PsiElement> arr = new ArrayList<>();
                        arr.add(method);
                        elementHashMap.put(returnQualifiedName, arr);
                    }
                }
            }
        }
        return elementHashMap;
    }

    private PsiTypeElementImpl getPsiTypeElement(PsiElement psiElement) {
        PsiElement[] psiElements = psiElement.getChildren();
        for (PsiElement element : psiElements) {
            if (element instanceof PsiTypeElement) {
                return (PsiTypeElementImpl) element;
            }
        }
        return null;
    }

    private PsiAnnotation getPsiAnnotation(@NotNull PsiElement psiElement) {
        if (psiElement instanceof PsiField) {
            PsiFieldImpl field = ((PsiFieldImpl) psiElement);
            PsiAnnotation psiAnnotation = field.getAnnotation(INJECT_QUALI_FIED_NAME);
            if (psiAnnotation != null) {
                return psiAnnotation;
            }
        }
        return null;
    }

    @Override
    public void collectSlowLineMarkers(@NotNull List<PsiElement> elements, @NotNull Collection<LineMarkerInfo> result) {
//        LineMarkerInfo lineMarkerInfo = new LineMarkerInfo(elements.get(0), elements.get(0).getTextRange(), AllIcons.Javaee.Remote,
//                new FunctionTooltip("快速跳转至 {0} 的 @IocBean 实现类"),
//                new IocBeanInterfaceNavigationHandler("xx", new ArrayList<>()),
//                GutterIconRenderer.Alignment.RIGHT);
//        result.add(lineMarkerInfo);
//        System.out.println(elements.size() + ">>" + result.size());
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @Nullable("null means disabled")
    @Override
    public String getName() {
        return "navigate to Implementation class";
    }
}
