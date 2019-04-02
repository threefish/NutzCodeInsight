package com.sgaop.idea.codeinsight.linemarker.navigation;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.sgaop.idea.codeinsight.util.SqlsXmlLineUtil;

import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/2
 */
public class SqlsXmlNavigationHandler extends AbstractNavigationHandler {

    @Override
    public boolean canNavigate(PsiElement psiElement) {
        return SqlsXmlLineUtil.isSqlsXml(psiElement);
    }

    @Override
    public List<VirtualFile> findTemplteFileList(PsiElement psiElement) {
        return SqlsXmlLineUtil.findTemplteFileList(psiElement.getNextSibling());
    }

}
