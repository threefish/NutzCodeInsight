package com.sgaop.idea.restful.ui;

import com.intellij.ide.util.treeView.AbstractTreeStructure;
import com.intellij.ide.util.treeView.NodeDescriptor;
import com.sgaop.idea.restful.ApiMutableTreeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/21
 */
public class ApiTreeModel extends AbstractTreeStructure {

    private ApiMutableTreeNode root;

    public ApiTreeModel(ApiMutableTreeNode root) {
        this.root = root;
    }

    @NotNull
    @Override
    public Object getRootElement() {
        return root;
    }

    @NotNull
    @Override
    public Object[] getChildElements(@NotNull Object element) {
        if (element == root) {
            Enumeration children = root.children();
            List list = new ArrayList<>();
            while (children.hasMoreElements()) {
                list.add(children.nextElement());
            }
            return list.toArray();
        }
        return new Object[0];
    }

    @Nullable
    @Override
    public Object getParentElement(@NotNull Object o) {
        return null;
    }

    @NotNull
    @Override
    public NodeDescriptor createDescriptor(@NotNull Object o, @Nullable NodeDescriptor nodeDescriptor) {
        return null;
    }

    @Override
    public void commit() {

    }

    @Override
    public boolean hasSomethingToCommit() {
        return false;
    }
}
