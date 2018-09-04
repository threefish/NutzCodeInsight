package com.sgaop.project.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/9/4
 */
public class SwingUtils {

    public static void addMouseListenerAndSetUrl(JLabel label, String linkUrl) {
        Color color = new Color(34, 168, 241);
        label.setText("<HTML><U>" + linkUrl + "</U></HTML>");
        label.setForeground(color);
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
}
