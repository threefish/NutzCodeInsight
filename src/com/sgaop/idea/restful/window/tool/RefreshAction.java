package com.sgaop.idea.restful.window.tool;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import com.sgaop.idea.gotosymbol.AtMappingNavigationItem;
import com.sgaop.idea.restful.tree.ApiMutableTreeNode;
import com.sgaop.idea.restful.tree.TreeObjectType;
import com.sgaop.idea.restful.tree.TreeRenderer;
import com.sgaop.util.FindRequestMappingItemsUtil;
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
        this.toolWindowEx = toolWindowEx;
        this.apiTree = apiTree;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        DumbService.getInstance(toolWindowEx.getProject()).smartInvokeLater(() -> {
            Module[] modules = ModuleManager.getInstance(toolWindowEx.getProject()).getModules();
            //定义tree 的根目录
            int size = 0;
            ApiMutableTreeNode root = new ApiMutableTreeNode(TreeObjectType.ROOT, "Found 0 api");
            for (Module module : modules) {
                ApiMutableTreeNode apiMutableTreeNode = new ApiMutableTreeNode(TreeObjectType.MODULE, module.getName());
                List<AtMappingNavigationItem> requestMappingItems = FindRequestMappingItemsUtil.findRequestMappingItems(module);
                size = size + requestMappingItems.size();
                for (AtMappingNavigationItem requestMappingItem : requestMappingItems) {
                    apiMutableTreeNode.add(new ApiMutableTreeNode(requestMappingItem));
                }
                root.add(apiMutableTreeNode);
            }
            root.setName("Found " + size + " api");
            //设置该JTree使用自定义的节点绘制器
            apiTree.setCellRenderer(new TreeRenderer());
            apiTree.setModel(new DefaultTreeModel(root));
        });
    }

}
