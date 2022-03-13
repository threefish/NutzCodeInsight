package io.github.threefish.idea.linemarker.navigation;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.sgaop.util.HtmlTemplateLineUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/29  11:54
 */
public class HtmlTemplateNavigationHandler extends AbstractNavigationHandler {

    @Override
    public boolean canNavigate(PsiElement psiElement) {
        return HtmlTemplateLineUtil.isRes(psiElement);
    }

    @Override
    public List<VirtualFile> findTemplteFileList(PsiElement psiElement) {
        return HtmlTemplateLineUtil.findTemplteFileList(psiElement);
    }

}
