package com.sgaop.idea.restful.tree;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;
import com.sgaop.util.AtMappingIconUtil;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/22
 */
public class TreeRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        //执行父类默认的节点绘制操作
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        ApiMutableTreeNode node = (ApiMutableTreeNode) value;
        //根据数据节点里的nodeType数据决定节点图标
        this.setIcon(AtMappingIconUtil.getIcon(node.getTreeObjectType()));
        return this;
    }
}
