package com.sgaop.idea.reference;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.sgaop.util.SqlsXmlUtil;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2020/5/29
 */
public class Java2XmlReferenceContributor extends PsiReferenceContributor {
    private static final Logger LOG = Logger.getInstance(Java2XmlReferenceContributor.class);

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar psiReferenceRegistrar) {
        psiReferenceRegistrar.registerReferenceProvider(PlatformPatterns.psiElement(PsiLiteralExpression.class), new JavaInjectPsiReferenceProvider());
    }

    class JavaInjectPsiReferenceProvider extends PsiReferenceProvider {

        @NotNull
        @Override
        public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext processingContext) {
            try {
                PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
                Object literalExpressionValue = literalExpression.getValue();
                String value = literalExpressionValue instanceof String ? (String) literalExpressionValue : null;
                if (Objects.nonNull(value) && !value.contains(" ")) {
                    List<String> fieldStrings = new ArrayList<>();
                    PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
                    if (SqlsXmlUtil.hasExecuteService(psiClass)) {
                        //如果是父类实现了这个service暂时不支持跳转
                        fieldStrings.addAll(SqlsXmlUtil.addExecuteService(element.getProject()));
                    }
                    PsiField[] fields = psiClass.getFields();
                    List<PsiField> extendsClassFields = SqlsXmlUtil.getExtendsClassFields(psiClass);
                    extendsClassFields.addAll(Arrays.asList(fields));
                    for (PsiField field : extendsClassFields) {
                        if (SqlsXmlUtil.isSqlTplField(field)) {
                            fieldStrings.add(field.getName() + ".");
                        }
                    }
                    if (CollectionUtils.isNotEmpty(fieldStrings)) {
                        boolean a = SqlsXmlUtil.isInjectXml(literalExpression, fieldStrings);
                        if (a) {
                            Project project = element.getProject();
                            //查询文件名，减少匹配范围
                            String xmlFileName = SqlsXmlUtil.findXmlFileName(psiClass);
                            Collection<VirtualFile> virtualFiles = FilenameIndex.getVirtualFilesByName(project, xmlFileName, SqlsXmlUtil.getSearchScope(project, element));
                            if (CollectionUtils.isEmpty(virtualFiles)) {
                                //扩大查找范围
                                virtualFiles = FilenameIndex.getAllFilesByExt(project, "xml", SqlsXmlUtil.getSearchScope(project, element));
                            }
                            final List<PsiElement> elements = SqlsXmlUtil.findXmlPsiElement(project, virtualFiles, value);
                            return elements.stream().map(psiElement -> new PsiJavaInjectReference(element, psiElement)).toArray(PsiReference[]::new);
                        }
                    }
                }
            } catch (Exception e) {
                LOG.warn(e);
            }
            return PsiReference.EMPTY_ARRAY;
        }


    }
}
