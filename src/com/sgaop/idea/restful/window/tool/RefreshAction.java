package com.sgaop.idea.restful.window.tool;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import com.sgaop.idea.restful.ApiMutableTreeNode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/22
 */
public class RefreshAction extends DumbAwareAction {


    private ToolWindowEx toolWindowEx;

    private JTree apiTree;

    public RefreshAction(String text, String description, Icon icon, ToolWindowEx toolWindowEx, JTree apiTree) {
        super(text, description, icon);
        this.toolWindowEx=toolWindowEx;
        this.apiTree=apiTree;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        DumbService.getInstance(toolWindowEx.getProject()).smartInvokeLater(() -> {
            Module[] modules = ModuleManager.getInstance(toolWindowEx.getProject()).getModules();
            //定义tree 的根目录
            ApiMutableTreeNode root = new ApiMutableTreeNode("Found 0 api");
            for (Module module : modules) {
                root.add(new ApiMutableTreeNode(module.getName()));
            }
            apiTree.setModel(new DefaultTreeModel(root));
        });

    }






}
