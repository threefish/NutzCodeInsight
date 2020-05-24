package com.sgaop.idea.restful.ui;

import com.intellij.openapi.ui.SimpleToolWindowPanel;

import javax.swing.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/21
 */
public class RestfulTreePanel extends SimpleToolWindowPanel {

    private JTree apiTree;
    private JPanel rootPanel;

    public RestfulTreePanel() {
        super(true, true);
        setContent(rootPanel);
    }

    public JTree getApiTree() {
        return apiTree;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
