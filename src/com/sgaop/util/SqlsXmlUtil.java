package com.sgaop.util;

import com.intellij.lang.jvm.annotation.JvmAnnotationAttribute;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.index.JavaShortClassNameIndex;
import com.intellij.psi.impl.source.tree.java.PsiAnnotationImpl;
import com.intellij.psi.impl.source.tree.java.PsiAnnotationParamListImpl;
import com.intellij.psi.impl.source.tree.java.PsiLiteralExpressionImpl;
import com.intellij.psi.impl.source.tree.java.PsiNameValuePairImpl;
import com.intellij.psi.impl.source.xml.XmlFileImpl;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.sgaop.idea.NutzCons;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/29  15:51
 */
public class SqlsXmlUtil {


    private static final String TPL_HOLDER = "com.github.threefish.nutz.sqltpl.SqlsTplHolder";
    private static final String ANNOTATION_SQLS_XML = NutzCons.SQLS_XML;
    private static final String SERVICE_ISQL_DAO_EXECUTE_SERVICE = "com.github.threefish.nutz.sqltpl.service.ISqlDaoExecuteService";
    private static List<String> SERVICE_METHOD_List = new ArrayList<>();

    /**
     * 判断是否是@SqlsXml
     *
     * @param bindingElement
     * @return
     */
    public static boolean isSqlsXml(PsiElement bindingElement) {
        if (bindingElement instanceof PsiJavaCodeReferenceElement && bindingElement.getParent() instanceof PsiAnnotationImpl) {
            PsiJavaCodeReferenceElement psiJavaCodeReferenceElement = (PsiJavaCodeReferenceElement) bindingElement;
            return NutzCons.SQLS_XML.equals(psiJavaCodeReferenceElement.getQualifiedName());
        }
        return false;
    }

    /**
     * 判断是sqltoyxml
     *
     * @param bindingElement
     * @return
     */
    public static boolean isSqsXmlFile(PsiElement bindingElement) {
        if (bindingElement.getContainingFile().getName().endsWith(".xml") && bindingElement instanceof XmlTag) {
            XmlTag xmlTag = (XmlTag) bindingElement;
            XmlAttribute id = xmlTag.getAttribute("id");
            if (xmlTag.getName().equals("sql") && Objects.nonNull(id)) {
                return true;
            }
        }
        return false;
    }


    public static Object getTemplteFileName(PsiElement psiAnnotationParamList) {
        Object value = null;
        if (psiAnnotationParamList instanceof PsiAnnotationParamListImpl) {
            value = getAnnotationValue((PsiAnnotationParamListImpl) psiAnnotationParamList);
        } else if (psiAnnotationParamList instanceof PsiJavaCodeReferenceElement && psiAnnotationParamList.getNextSibling() instanceof PsiAnnotationParamListImpl) {
            value = getAnnotationValue((PsiAnnotationParamListImpl) psiAnnotationParamList.getNextSibling());
        }
        if (value == null) {
            value = psiAnnotationParamList.getContainingFile().getName().replace(".java", ".xml");
        }
        return value;
    }

    private static Object getAnnotationValue(PsiAnnotationParamListImpl psiAnnotationParamList) {
        Object value = null;
        Collection<PsiLiteralExpression> literalExpressions = PsiTreeUtil.findChildrenOfType(psiAnnotationParamList, PsiLiteralExpressionImpl.class);
        if (literalExpressions.size() == 1) {
            value = literalExpressions.iterator().next().getValue();
        }
        return value;
    }

    public static List<VirtualFile> findTemplteFileList(PsiElement psiAnnotationParamList) {
        Object value = getTemplteFileName(psiAnnotationParamList);
        ArrayList<VirtualFile> arrayList = new ArrayList<>();
        String xmlPath = psiAnnotationParamList.getContainingFile().getParent().getVirtualFile().getPath() + "/" + value;
        Collection<VirtualFile> virtualFiles = FilenameIndex.getAllFilesByExt(psiAnnotationParamList.getProject(), "xml", GlobalSearchScope.projectScope(psiAnnotationParamList.getProject()));
        Optional<VirtualFile> optional = virtualFiles.stream().filter(virtualFile -> virtualFile.getPath().equals(xmlPath)).findFirst();
        if (optional.isPresent()) {
            arrayList.add(optional.get());
        }
        return arrayList;
    }

    public static GlobalSearchScope getSearchScope(Project project, @NotNull PsiElement element) {
        GlobalSearchScope searchScope = GlobalSearchScope.projectScope(project);
        Module module = ProjectRootManager.getInstance(project).getFileIndex().getModuleForFile(element.getContainingFile().getVirtualFile());
        if (module != null) {
            searchScope = GlobalSearchScope.moduleScope(module);
        }
        return searchScope;
    }

    public static List<PsiField> getExtendsClassFields(PsiClass psiClass) {
        List<PsiField> psiFields = new ArrayList<>();
        List<PsiReferenceList> childrenOfAnyType = PsiTreeUtil.getChildrenOfAnyType(psiClass, PsiReferenceList.class);
        for (PsiReferenceList psiReferenceList : childrenOfAnyType) {
            PsiJavaCodeReferenceElement[] referenceElements = psiReferenceList.getReferenceElements();
            for (PsiJavaCodeReferenceElement referenceElement : referenceElements) {
                String qualifiedName = referenceElement.getQualifiedName();
                GlobalSearchScope globalSearchScope = GlobalSearchScope.projectScope(psiClass.getProject());
                Collection<PsiClass> psiClasses = JavaShortClassNameIndex.getInstance().get(referenceElement.getReferenceName(), psiClass.getProject(), globalSearchScope);
                for (PsiClass aClass : psiClasses) {
                    if (aClass.getQualifiedName().equals(qualifiedName)) {
                        psiFields.addAll(Arrays.asList(aClass.getFields()));
                        psiFields.addAll(getExtendsClassFields(aClass));
                    }
                }
            }
        }
        return psiFields;
    }

    public static boolean isSqlTplField(PsiField field) {
        PsiTypeElement typeElement = field.getTypeElement();
        if (Objects.nonNull(typeElement)) {
            PsiJavaCodeReferenceElement innermostComponentReferenceElement = typeElement.getInnermostComponentReferenceElement();
            if (Objects.nonNull(innermostComponentReferenceElement)) {
                String canonicalText = innermostComponentReferenceElement.getCanonicalText();
                if (TPL_HOLDER.equals(canonicalText)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 可以跳转到xml
     *
     * @param literalExpression
     * @param fields
     * @return
     */
    public static boolean isInjectXml(PsiElement literalExpression, List<String> fields) {
        //添加一下通过实现方法的调用
        fields.add("getSqlsTplHolder()");
        PsiElement p1 = literalExpression.getParent();
        if (!(p1 instanceof PsiExpressionList)) {
            return false;
        }
        PsiElement p2 = p1.getParent();
        if (!(p2 instanceof PsiMethodCallExpression)) {
            return false;
        }
        String text = p2.getText();
        return fields.stream().filter(s -> (text.startsWith(s) || text.startsWith("super." + s) || text.startsWith("this." + s))).findAny().isPresent();
    }

    public static boolean hasExecuteService(PsiClass psiClass) {
        List<PsiReferenceList> childrenOfAnyType = PsiTreeUtil.getChildrenOfAnyType(psiClass, PsiReferenceList.class);
        for (PsiReferenceList psiReferenceList : childrenOfAnyType) {
            List<PsiJavaCodeReferenceElement> referenceElements = PsiTreeUtil.getChildrenOfAnyType(psiReferenceList, PsiJavaCodeReferenceElement.class);
            for (PsiJavaCodeReferenceElement referenceElement : referenceElements) {
                if (SERVICE_ISQL_DAO_EXECUTE_SERVICE.equals(referenceElement.getQualifiedName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<PsiElement> findXmlPsiElement(Project project, Collection<VirtualFile> virtualFiles, String key) {
        List<PsiElement> result = new ArrayList<>();
        for (VirtualFile virtualFile : virtualFiles) {
            PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
            if (psiFile instanceof XmlFileImpl) {
                XmlFileImpl xmlFile = (XmlFileImpl) psiFile;
                PsiElement navigationElement = xmlFile.getNavigationElement();
                if (Objects.nonNull(navigationElement)) {
                    if (Objects.nonNull(navigationElement.getChildren())) {
                        PsiElement child = navigationElement.getChildren()[0];
                        if (Objects.nonNull(child)) {
                            if (Objects.nonNull(child.getChildren()) && child.getChildren().length > 0) {
                                PsiElement child1 = child.getChildren()[1];
                                if (Objects.nonNull(child1)) {
                                    PsiElement[] psiElements = child1.getChildren();
                                    if (Objects.nonNull(psiElements) && psiElements.length > 0) {
                                        List<XmlTag> xmlTags = PsiTreeUtil.getChildrenOfAnyType(child1, XmlTag.class);
                                        for (XmlTag xmlTag : xmlTags) {
                                            if ("sql".equals(xmlTag.getName())) {
                                                XmlAttribute xmlAttribute = xmlTag.getAttribute("id");
                                                String id = xmlAttribute.getValue();
                                                if (key.equals(id)) {
                                                    result.add(xmlAttribute.getValueElement());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * JavaSuperClassNameOccurenceIndex.getInstance().get("ISqlDaoExecuteService", project, moduleWithDependenciesScope)
     *
     * @param project
     * @return
     */
    public static List<String> addExecuteService(Project project) {
        if (CollectionUtils.isEmpty(SERVICE_METHOD_List)) {
            List<String> arry = new ArrayList<>();
            Collection<PsiClass> iSqlDaoExecuteService = JavaShortClassNameIndex.getInstance().get("ISqlDaoExecuteService", project, GlobalSearchScope.everythingScope(project));
            for (PsiClass psiClass : iSqlDaoExecuteService) {
                if (SERVICE_ISQL_DAO_EXECUTE_SERVICE.equals(psiClass.getQualifiedName())) {
                    List<PsiMethod> methods = PsiTreeUtil.getChildrenOfAnyType(psiClass, PsiMethod.class);
                    for (PsiMethod method : methods) {
                        arry.add(method.getName() + "(");
                    }
                }
            }
            SERVICE_METHOD_List = arry;
        }
        return SERVICE_METHOD_List;
    }

    public static String findXmlFileName(PsiClass psiClass) {
        List<PsiModifierList> modifierLists = PsiTreeUtil.getChildrenOfAnyType(psiClass, PsiModifierList.class);
        if (CollectionUtils.isNotEmpty(modifierLists)) {
            List<PsiAnnotation> annotations = PsiTreeUtil.getChildrenOfAnyType(modifierLists.get(0), PsiAnnotation.class);
            for (PsiAnnotation annotation : annotations) {
                if (ANNOTATION_SQLS_XML.equals(annotation.getQualifiedName())) {
                    List<JvmAnnotationAttribute> attributes = annotation.getAttributes();
                    for (JvmAnnotationAttribute attribute : attributes) {
                        String attributeName = attribute.getAttributeName();
                        if ("value".equals(attributeName)) {
                            return ((PsiNameValuePairImpl) attribute).getLiteralValue();
                        }
                    }
                    if (CollectionUtils.isEmpty(attributes)) {
                        String name = psiClass.getContainingFile().getName();
                        name = name.substring(0, name.length() - 4);
                        return name + "xml";
                    }
                }
            }
        }
        return "xml";
    }

    public static List<PsiElement> findJavaPsiElement(PsiClass psiClass, String id) {
        List<PsiElement> result = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        List<PsiField> childrenOfAnyType = PsiTreeUtil.getChildrenOfAnyType(psiClass, PsiField.class);
        childrenOfAnyType.addAll(getExtendsClassFields(psiClass));
        for (PsiField field : childrenOfAnyType) {
            if (isSqlTplField(field)) {
                keys.add(field.getName() + ".");
            }
        }
        if (CollectionUtils.isNotEmpty(keys)) {
            List<PsiMethod> psiMethods = PsiTreeUtil.getChildrenOfAnyType(psiClass, PsiMethod.class);
            List<PsiMethodCallExpression> psiMethodCallExpressions = new ArrayList<>();
            for (PsiMethod psiMethod : psiMethods) {
                List<PsiCodeBlock> psiCodeBlocks = PsiTreeUtil.getChildrenOfAnyType(psiMethod, PsiCodeBlock.class);
                for (PsiCodeBlock psiCodeBlock : psiCodeBlocks) {
                    List<PsiDeclarationStatement> psiDeclarationStatements = PsiTreeUtil.getChildrenOfAnyType(psiCodeBlock, PsiDeclarationStatement.class);
                    for (PsiDeclarationStatement psiDeclarationStatement : psiDeclarationStatements) {
                        List<PsiLocalVariable> psiLocalVariables = PsiTreeUtil.getChildrenOfAnyType(psiDeclarationStatement, PsiLocalVariable.class);
                        for (PsiLocalVariable psiLocalVariable : psiLocalVariables) {
                            psiMethodCallExpressions.addAll(PsiTreeUtil.getChildrenOfAnyType(psiLocalVariable, PsiMethodCallExpression.class));
                        }
                    }
                    List<PsiExpressionStatement> psiExpressionStatements = PsiTreeUtil.getChildrenOfAnyType(psiCodeBlock, PsiExpressionStatement.class);
                    for (PsiExpressionStatement psiExpressionStatement : psiExpressionStatements) {
                        psiMethodCallExpressions.addAll(PsiTreeUtil.getChildrenOfAnyType(psiExpressionStatement, PsiMethodCallExpression.class));
                    }
                    List<PsiReturnStatement> psiReturnStatements = PsiTreeUtil.getChildrenOfAnyType(psiCodeBlock, PsiReturnStatement.class);
                    for (PsiReturnStatement psiExpressionStatement : psiReturnStatements) {
                        psiMethodCallExpressions.addAll(PsiTreeUtil.getChildrenOfAnyType(psiExpressionStatement, PsiMethodCallExpression.class));
                    }
                }
            }
            if (SqlsXmlUtil.hasExecuteService(psiClass)) {
                //如果是父类实现了这个service暂时不支持跳转
                keys.addAll(SqlsXmlUtil.addExecuteService(psiClass.getProject()));
            }
            keys.add("getSqlsTplHolder()");
            for (PsiMethodCallExpression psiMethodCallExpression : psiMethodCallExpressions) {
                PsiElement xmlIdBindPsiElement = getXmlIdBindPsiElement(psiMethodCallExpression, keys, id);
                if (Objects.nonNull(xmlIdBindPsiElement)) {
                    result.add(xmlIdBindPsiElement);
                }
            }
            if (CollectionUtils.isEmpty(result)) {
                //简单保底处理
                for (PsiMethod psiMethod : psiMethods) {
                    if (psiMethod.getText().indexOf(id) > -1) {
                        result.add(psiMethod);
                    }
                }

            }
        }
        return result;
    }


    public static PsiElement getXmlIdBindPsiElement(PsiMethodCallExpression psiMethodCallExpression, List<String> keys, String id) {
        for (String key : keys) {
            String text = psiMethodCallExpression.getText();
            if (text.startsWith(key)
                    || text.startsWith("super." + key)
                    || text.startsWith("this." + key)) {
                PsiExpressionList psiExpressionList = PsiTreeUtil.getChildOfAnyType(psiMethodCallExpression, PsiExpressionList.class);
                if (Objects.nonNull(psiExpressionList)) {
                    List<PsiLiteralExpression> psiLiteralExpressions = PsiTreeUtil.getChildrenOfAnyType(psiExpressionList, PsiLiteralExpression.class);
                    for (PsiLiteralExpression psiLiteralExpression : psiLiteralExpressions) {
                        if (!psiLiteralExpression.getText().contains(" ") && id.equals(((PsiLiteralExpressionImpl) psiLiteralExpression).getInnerText())) {
                            return psiLiteralExpression;
                        }
                    }
                }
            }
        }
        return null;
    }
}
