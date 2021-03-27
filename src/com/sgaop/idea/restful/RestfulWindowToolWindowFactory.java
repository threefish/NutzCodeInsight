package com.sgaop.idea.restful;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.treeStructure.SimpleTree;
import com.sgaop.idea.NutzCons;
import com.sgaop.idea.restful.ui.RestServicesNavigatorPanel;
import com.sgaop.idea.restful.window.tool.ApiTreeMouseAdapter;
import com.sgaop.idea.restful.window.tool.RefreshAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.TreeSelectionModel;
import java.util.Arrays;

/**
 * ActionManager actionManager = ActionManager.getInstance();
 * ((ToolWindowEx) toolWindow).setTitleActions(actionManager.getAction("MyAction"));
 *
 * @author 黄川 huchuc@vip.qq.com
 * date: 2020/5/16
 */
public class RestfulWindowToolWindowFactory implements ToolWindowFactory, DumbAware {

    private static final String TITLE = "Nutz Api Tool";
    private final ActionManager actionManager = ActionManager.getInstance();
    private ToolWindowEx toolWindowEx;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        final SimpleTree apiTree = new SimpleTree();
        apiTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.toolWindowEx = (ToolWindowEx) toolWindow;
        RefreshAction refreshAction = new RefreshAction("刷新", "重新加载URL", AllIcons.Actions.Refresh, toolWindowEx, apiTree);
        toolWindowEx.setTitleActions(Arrays.asList(refreshAction, actionManager.getAction("GoToRequestMapping")));
        apiTree.addMouseListener(new ApiTreeMouseAdapter(apiTree));
        ContentManager contentManager = toolWindow.getContentManager();
        Content content = contentManager.getFactory().createContent(new RestServicesNavigatorPanel(apiTree), null, false);
        contentManager.addContent(content);
        contentManager.setSelectedContent(content);
        if (project.isInitialized()) {
            refreshAction.loadTree(project);
        }
    }


    @Override
    public void init(@NotNull ToolWindow toolWindow) {
        this.toolWindowEx = (ToolWindowEx) toolWindow;
        toolWindowEx.setStripeTitle(TITLE);
        toolWindowEx.setIcon(NutzCons.NUTZ);
        toolWindowEx.setTitle(TITLE);
    }

}
