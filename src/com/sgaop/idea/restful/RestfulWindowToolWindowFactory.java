package com.sgaop.idea.restful;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
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
 * ActionManager actionManager = ActionManager.getInstance();
 * ((ToolWindowEx) toolWindow).setTitleActions(actionManager.getAction("MyAction"));
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/16
 */
public class RestfulWindowToolWindowFactory implements ToolWindowFactory, DumbAware {

    private static final String TITLE = "Nutz Api Tool";
    private RestfulTreePanel restfulTreePanel = new RestfulTreePanel();
    private ToolWindowEx toolWindowEx;
    ActionManager actionManager = ActionManager.getInstance();

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        JTree apiTree = restfulTreePanel.getApiTree();
        apiTree.setModel(null);
        this.toolWindowEx = (ToolWindowEx) toolWindow;
        toolWindowEx.setStripeTitle(TITLE);
        toolWindowEx.setIcon(NutzCons.NUTZ);
        toolWindowEx.setTitle(TITLE);
        toolWindowEx.setTitleActions(
                new RefreshAction("刷新", "重新加载URL", AllIcons.Actions.Refresh, toolWindowEx, restfulTreePanel.getApiTree()),
                actionManager.getAction("GoToRequestMapping"));
        apiTree.addMouseListener(new ApiTreeMouseAdapter(apiTree));
        ContentManager contentManager = toolWindow.getContentManager();
        Content content = contentManager.getFactory().createContent(restfulTreePanel.getRootPanel(), null, false);
        contentManager.addContent(content);
        contentManager.setSelectedContent(content);
    }
}
