package com.sgaop.idea.restful.window.tool;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/22
 */
public class ApiTreeMouseAdapter extends MouseAdapter {

    private JTree apiTree;

    public ApiTreeMouseAdapter(JTree apiTree) {
        this.apiTree = apiTree;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            //获取点击的tree节点
            DefaultMutableTreeNode note = (DefaultMutableTreeNode) apiTree.getLastSelectedPathComponent();
            if (note != null) {
                System.out.println(note);
            }
        }
    }
}
