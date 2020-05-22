package com.sgaop.idea.restful;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/21
 */
public class ApiMutableTreeNode extends DefaultMutableTreeNode {

    public ApiMutableTreeNode(String name) {
        super(name);
    }

    @Override
    public String toString() {
        if (userObject == null) {
            return "";
        } else {
            return userObject.toString() + "xxxx";
        }
    }

}
