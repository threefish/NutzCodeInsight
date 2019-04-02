package com.sgaop.idea.codeinsight.util;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.impl.source.tree.java.PsiAnnotationImpl;
import com.intellij.psi.impl.source.tree.java.PsiAnnotationParamListImpl;
import com.intellij.psi.impl.source.tree.java.PsiLiteralExpressionImpl;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.sgaop.idea.codeinsight.NutzCons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/29  15:51
 */
public class SqlsXmlLineUtil {


    /**
     * 判断是否是@SqlsXml
     *
     * @param bindingElement
     * @return
     */
    public static boolean isSqlsXml(PsiElement bindingElement) {
        if (bindingElement instanceof PsiJavaCodeReferenceElement && bindingElement.getParent() instanceof PsiAnnotationImpl) {
            PsiJavaCodeReferenceElement psiJavaCodeReferenceElement = (PsiJavaCodeReferenceElement) bindingElement;
            if (NutzCons.SQLS_XML.equals(psiJavaCodeReferenceElement.getQualifiedName())) {
                return true;
            }
        }
        return false;
    }

    public static List<VirtualFile> findTemplteFileList(PsiElement psiAnnotation) {
        Object value = null;
        PsiElement psiAnnotationParamList = psiAnnotation.getNextSibling();
        if (psiAnnotationParamList instanceof PsiAnnotationParamListImpl) {
            Collection<PsiLiteralExpression> literalExpressions = PsiTreeUtil.findChildrenOfType(psiAnnotation.getNextSibling(), PsiLiteralExpressionImpl.class);
            if (literalExpressions.size() == 1) {
                value = literalExpressions.iterator().next().getValue();
            }
        }
        if (value == null) {
            value = psiAnnotation.getContainingFile().getName().replace(".java", ".xml");
        }
        ArrayList<VirtualFile> arrayList = new ArrayList<>();
        String xmlPath = psiAnnotation.getContainingFile().getParent().getVirtualFile().getPath() + "/" + value;
        Collection<VirtualFile> virtualFiles = FilenameIndex.getAllFilesByExt(psiAnnotation.getProject(), "xml", GlobalSearchScope.projectScope(psiAnnotation.getProject()));
        Optional<VirtualFile> optional = virtualFiles.stream().filter(virtualFile -> virtualFile.getPath().equals(xmlPath)).findFirst();
        if (optional.isPresent()) {
            arrayList.add(optional.get());
        }
        return arrayList;
    }
}
