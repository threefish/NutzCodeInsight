package com.sgaop.idea.inspection;

import com.intellij.codeInspection.*;
import com.intellij.psi.PsiFile;
import com.siyeh.ig.jdk.AnnotationInspection;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/3
 */
public class GenerateSqlXmlInspection extends AnnotationInspection {

    @Override
    public String getShortName() {
        return "create Sql Xml";
    }

    @Override
    public ProblemDescriptor[] checkFile(PsiFile file, InspectionManager manager, boolean isOnTheFly) {
        String message = "Some Message";
        SqlXmlLocalQuickFix quickFix = new SqlXmlLocalQuickFix();
        LocalQuickFix[] quickFixes = new LocalQuickFix[]{quickFix};
        return new ProblemDescriptor[]{
                manager.createProblemDescriptor(
                        file, message, true, quickFixes, ProblemHighlightType.GENERIC_ERROR_OR_WARNING
                )
        };
    }
}
