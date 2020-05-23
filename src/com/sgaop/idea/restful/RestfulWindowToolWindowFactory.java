package com.sgaop.idea.restful;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.sgaop.idea.NutzCons;
import com.sgaop.idea.restful.ui.RestfulTreePanel;
import com.sgaop.idea.restful.window.tool.ApiTreeMouseAdapter;
import com.sgaop.idea.restful.window.tool.RefreshAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/16
 */
public class RestfulWindowToolWindowFactory implements ToolWindowFactory, DumbAware {

    private static final String TITLE = "Nutz Api 工具";
    private RestfulTreePanel restfulTreePanel = new RestfulTreePanel();
    private Project project;
    private ToolWindowEx toolWindowEx;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        JTree apiTree = restfulTreePanel.getApiTree();
        apiTree.setModel(null);
        this.project = project;
        this.toolWindowEx = (ToolWindowEx) toolWindow;
        toolWindowEx.setStripeTitle(TITLE);
        toolWindowEx.setIcon(NutzCons.NUTZ);
        toolWindowEx.setTitleActions(new RefreshAction("刷新", "重新加载URL", AllIcons.Actions.Refresh, toolWindowEx, restfulTreePanel.getApiTree()));
        apiTree.addMouseListener(new ApiTreeMouseAdapter(apiTree));
        this.toolWindowEx.getComponent().add(restfulTreePanel.getRootPanel());
    }


    @Override
    public void init(@NotNull ToolWindow toolWindow) {

    }


}
