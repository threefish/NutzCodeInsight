package com.sgaop.idea.restful.tree;


import com.sgaop.idea.gotosymbol.AtMappingNavigationItem;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/21
 */
public class ApiMutableTreeNode extends DefaultMutableTreeNode {

    private TreeObjectType treeObjectType;

    private String name;

    AtMappingNavigationItem atMappingNavigationItem;

    public ApiMutableTreeNode(TreeObjectType treeObjectType, String name) {
        super(name);
        this.treeObjectType = treeObjectType;
        this.name = name;
    }

    public ApiMutableTreeNode(AtMappingNavigationItem atMappingNavigationItem) {
        super(atMappingNavigationItem.getText());
        this.name = atMappingNavigationItem.getText();
        this.atMappingNavigationItem = atMappingNavigationItem;
        switch (atMappingNavigationItem.getApiType()) {
            case POST:
                this.treeObjectType = TreeObjectType.POST;
                break;
            case DELETE:
                this.treeObjectType = TreeObjectType.DELETE;
                break;
            case PUT:
                this.treeObjectType = TreeObjectType.PUT;
                break;
            default:
                this.treeObjectType = TreeObjectType.GET;
                break;
        }
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public AtMappingNavigationItem getAtMappingNavigationItem() {
        return atMappingNavigationItem;
    }

    public TreeObjectType getTreeObjectType() {
        return treeObjectType;
    }

    public String getName() {
        return name;
    }

}
