package com.sgaop.idea.restful.tree;


import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/21
 */
public class ApiMutableTreeNode extends DefaultMutableTreeNode {

    public ApiMutableTreeNode() {
    }

    public ApiMutableTreeNode(TreeNodeObject nodeObjectNodeDescriptor) {
        this.userObject = nodeObjectNodeDescriptor;
    }


    @Override
    public Object getUserObject() {
        return this.userObject;
    }

    @Override
    public String toString() {
        return this.userObject.toString();
    }
}
