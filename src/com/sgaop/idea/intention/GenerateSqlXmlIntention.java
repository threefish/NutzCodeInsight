package com.sgaop.idea.intention;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.navigation.NavigationUtil;
import com.intellij.lang.Language;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.sgaop.util.SqlsXmlLineUtil;
import com.sgaop.idea.template.SqlTplFileTemplateGroupFactory;
import com.sgaop.util.TemplateFileUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/2
 */
public class GenerateSqlXmlIntention implements IntentionAction {

    static final Language JAVA = Language.findLanguageByID("JAVA");
    private String templateFileName;
    private String clazz;

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getText() {
        return "创建 " + templateFileName;
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getFamilyName() {
        return "自动创建SqlTpl xml文件";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile psiFile) {
        if (psiFile.getLanguage() == JAVA) {
            PsiElement psiElement = psiFile.findElementAt(editor.getCaretModel().getOffset());
            PsiClass psiClass = PsiTreeUtil.getParentOfType(psiElement, PsiClass.class);
            if (psiClass != null) {
                PsiAnnotation[] psiAnnotations = psiClass.getAnnotations();
                if (psiAnnotations != null) {
                    for (PsiAnnotation annotation : psiAnnotations) {
                        PsiJavaCodeReferenceElement element = annotation.getNameReferenceElement();
                        if (SqlsXmlLineUtil.isSqlsXml(element) && SqlsXmlLineUtil.findTemplteFileList(element).size() == 0) {
                            templateFileName = String.valueOf(SqlsXmlLineUtil.getTemplteFileName(element));
                            clazz = psiClass.getQualifiedName();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile psiFile) throws IncorrectOperationException {
        PsiDirectory psiDirectory = psiFile.getContainingDirectory();
        WriteCommandAction.runWriteCommandAction(project, () -> {
            try {
                Properties properties = new Properties();
                properties.setProperty("CLASSNAME", clazz);
                PsiElement element = TemplateFileUtil.createFromTemplate(SqlTplFileTemplateGroupFactory.NUTZ_SQL_TPL_XML, templateFileName, properties, psiDirectory);
                NavigationUtil.activateFileWithPsiElement(element, true);
            } catch (Exception e) {
                HintManager.getInstance().showErrorHint(editor, "Failed: " + e.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }
}
