package com.sgaop.idea.restful;

import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.SimpleTree;

import javax.swing.*;
import java.awt.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/16
 */
public class MySimpleTree extends SimpleTree {

    Project project;
    Graphics graphics;

    public MySimpleTree(Project project) {
        this.project = project;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.graphics = g;
        this.init();
    }

    public void init() {
        final JLabel myLabel = new JLabel("测试");
        myLabel.setFont(getFont());
        myLabel.setBackground(getBackground());
        myLabel.setForeground(getForeground());
        Rectangle bounds = getBounds();
        Dimension size = myLabel.getPreferredSize();
        myLabel.setBounds(0, 0, size.width, size.height);
        int x = (bounds.width - size.width) / 2;
        Graphics g2 = graphics.create(bounds.x + x, bounds.y + 20, bounds.width, bounds.height);
        try {
            myLabel.paint(g2);
        } finally {
            g2.dispose();
        }
    }

}
