package io.github.threefish.idea.plugin.project.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import io.github.threefish.idea.plugin.project.ui.SwingUtils;

import javax.swing.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2018/9/3
 */
public class IntroductionWizardStep extends ModuleWizardStep {
    private JPanel root;
    private JLabel authorLink;
    private JLabel nbIndex;
    private JLabel history;
    private JLabel doc;
    private JLabel doc2;
    private JLabel questionAndAnswer;
    private JLabel nutzWk;
    private JLabel nutzFw;

    public IntroductionWizardStep() {
        SwingUtils.addMouseListenerAndSetUrl(authorLink, "https://github.com/threefish/NutzCodeInsight/");
        SwingUtils.addMouseListenerAndSetUrl(nbIndex, "https://nutz.io/");
        SwingUtils.addMouseListenerAndSetUrl(history, "https://gitee.com/nutz/nutzboot/blob/dev/ChangeLog.md");
        SwingUtils.addMouseListenerAndSetUrl(doc, "https://gitee.com/nutz/nutzboot/tree/dev/doc");
        SwingUtils.addMouseListenerAndSetUrl(doc2, "http://nutzam.com/core/boot/overview.html");
        SwingUtils.addMouseListenerAndSetUrl(questionAndAnswer, "https://nutz.cn");
        SwingUtils.addMouseListenerAndSetUrl(nutzWk, "https://gitee.com/wizzer/NutzWk");
        SwingUtils.addMouseListenerAndSetUrl(nutzFw, "https://gitee.com/threefish/NutzFw");
    }

    @Override
    public JComponent getComponent() {
        return getRoot();
    }

    @Override
    public void updateDataModel() {

    }


    public JPanel getRoot() {
        return root;
    }

}
