package com.sgaop.project.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        addMouseListenerAndSetUrl(authorLink, "https://github.com/threefish/NutzCodeInsight/");
        addMouseListenerAndSetUrl(nbIndex, "https://nutz.io/");
        addMouseListenerAndSetUrl(history, "https://gitee.com/nutz/nutzboot/blob/dev/ChangeLog.md");
        addMouseListenerAndSetUrl(doc, "https://gitee.com/nutz/nutzboot/tree/dev/doc");
        addMouseListenerAndSetUrl(doc2, "http://nutzam.com/core/boot/overview.html");
        addMouseListenerAndSetUrl(questionAndAnswer, "https://nutz.cn");
        addMouseListenerAndSetUrl(nutzWk, "https://gitee.com/wizzer/NutzWk");
    }


    private void addMouseListenerAndSetUrl(JLabel label, String linkUrl) {
        label.setText("<HTML><U>" + linkUrl + "</U></HTML>");
        label.setForeground(Color.blue);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.setPreferredSize(new Dimension(-1, 30));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new java.net.URI(linkUrl));
                } catch (Exception e1) {
                }
            }
        });
    }

    public JPanel getRoot() {
        return root;
    }

}
