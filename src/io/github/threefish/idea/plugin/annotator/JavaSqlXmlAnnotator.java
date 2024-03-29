package io.github.threefish.idea.plugin.annotator;

import com.intellij.lang.annotation.AnnotationBuilder;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import io.github.threefish.idea.plugin.intention.GenerateSqlXmlIntention;
import io.github.threefish.idea.plugin.util.SqlsXmlUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2019/4/2
 */
public class JavaSqlXmlAnnotator extends AbstractSqlXmlAnnotator {

    static final String ERR_MSG = "模版文件未找到";

    @Override
    public boolean isSqlsXml(@NotNull PsiElement psiElement) {
        return SqlsXmlUtil.isSqlsXml(psiElement);
    }

    /**
     * 没有找到模板文件报错
     *
     * @param psiElement
     * @param annotationHolder
     */
    @Override
    public void process(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        try {
            List<VirtualFile> virtualFileList = SqlsXmlUtil.findTemplteFileList(psiElement);
            if (virtualFileList.size() == 0) {
                TextRange textRange;
                if (psiElement.getNextSibling().getChildren().length == 0) {
                    textRange = psiElement.getTextRange();
                } else {
                    textRange = new TextRange(psiElement.getTextRange().getStartOffset(), psiElement.getNextSibling().getTextRange().getEndOffset());
                }
                AnnotationBuilder annotationBuilder = annotationHolder.newAnnotation(HighlightSeverity.ERROR, ERR_MSG);
                annotationBuilder.range(textRange);
                AnnotationBuilder.FixBuilder fixBuilder = annotationBuilder.newFix(new GenerateSqlXmlIntention());
                fixBuilder.registerFix();
                annotationBuilder.create();
            }
        } catch (Exception e) {
        }
    }
}
