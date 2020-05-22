package com.sgaop.idea.restful;

import com.intellij.icons.AllIcons;
import com.intellij.ide.util.treeView.AbstractTreeStructure;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.tree.AsyncTreeModel;
import com.intellij.ui.tree.StructureTreeModel;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.sgaop.idea.restful.ui.RestfulTreePanel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/16
 */
public class RestfulWindowToolWindowFactory implements ToolWindowFactory, DumbAware {

    private static final String TOOL_WINDOW_ID = "RestfulWindow";

    private Project project;

    private ToolWindow toolWindow;

    RestfulTreePanel restfulTreePanel=new RestfulTreePanel();
    //定义tree 的根目录
    ApiMutableTreeNode root = new ApiMutableTreeNode("京东集团");
    //定义根节点下面的子节点
    ApiMutableTreeNode n1 = new ApiMutableTreeNode("京东研发");
    ApiMutableTreeNode n2 = new ApiMutableTreeNode("京东行政");
    ApiMutableTreeNode n3 = new ApiMutableTreeNode("京东物流");
    ApiMutableTreeNode n4 = new ApiMutableTreeNode("京东金融");
    ApiMutableTreeNode n5 = new ApiMutableTreeNode("京东商城");
    ApiMutableTreeNode n6 = new ApiMutableTreeNode("京东财务");


    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        JTree apiTree = restfulTreePanel.getApiTree();
        this.project = project;
        this.toolWindow = toolWindow;
        toolWindow.setTitle("Nutz 帮助工具");
        //构造一个treeModel 对象，进行刷新树操作
        root.add(n1);
        root.add(n2);
        root.add(n3);
        root.add(n4);
        root.add(n5);
        root.add(n6);
        apiTree.setModel(new DefaultTreeModel(root));
        apiTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) { //BUTTON3是鼠标右键
                    //获取点击的tree节点
                    DefaultMutableTreeNode note = (DefaultMutableTreeNode) apiTree.getLastSelectedPathComponent();
                    if (note != null) {
                        System.out.println(note);
                    }
                }
            }
        });
        ContentManager contentManager = toolWindow.getContentManager();
        contentManager.getComponent().add(restfulTreePanel.getRootPanel());
    }


    @Override
    public void init(@NotNull ToolWindow toolWindow) {
        toolWindow.setTitle("Nutz 帮助工具");
        ((ToolWindowEx) toolWindow).setTitleActions(new RefreshAction("刷新", "重新加载URL", AllIcons.Actions.Refresh));
    }

    private abstract class Action extends DumbAwareAction {

        public Action(@Nls(capitalization = Nls.Capitalization.Title) @Nullable String text, @Nls(capitalization = Nls.Capitalization.Sentence) @Nullable String description, @Nullable Icon icon) {
            super(text, description, icon);
        }


        @Override
        public void actionPerformed(@NotNull AnActionEvent e) {
            System.out.println("xxx");
        }
    }

    class RefreshAction extends Action {

        public RefreshAction(@Nls(capitalization = Nls.Capitalization.Title) @Nullable String text, @Nls(capitalization = Nls.Capitalization.Sentence) @Nullable String description, @Nullable Icon icon) {
            super(text, description, icon);
        }


    }
}
