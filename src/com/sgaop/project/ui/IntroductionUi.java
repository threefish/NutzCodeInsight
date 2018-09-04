package com.sgaop.project.ui;

import javax.swing.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/9/3
 */
public class IntroductionUi {
    private JPanel root;
    private JLabel authorLink;
    private JLabel nbIndex;
    private JLabel history;
    private JLabel doc;
    private JLabel doc2;
    private JLabel questionAndAnswer;
    private JLabel nutzWk;

    public IntroductionUi() {
        SwingUtils.addMouseListenerAndSetUrl(authorLink, "https://github.com/threefish/NutzCodeInsight/");
        SwingUtils.addMouseListenerAndSetUrl(nbIndex, "https://nutz.io/");
        SwingUtils.addMouseListenerAndSetUrl(history, "https://gitee.com/nutz/nutzboot/blob/dev/ChangeLog.md");
        SwingUtils.addMouseListenerAndSetUrl(doc, "https://gitee.com/nutz/nutzboot/tree/dev/doc");
        SwingUtils.addMouseListenerAndSetUrl(doc2, "http://nutzam.com/core/boot/overview.html");
        SwingUtils.addMouseListenerAndSetUrl(questionAndAnswer, "https://nutz.cn");
        SwingUtils.addMouseListenerAndSetUrl(nutzWk, "https://gitee.com/wizzer/NutzWk");
    }


    public JPanel getRoot() {
        return root;
    }

}
