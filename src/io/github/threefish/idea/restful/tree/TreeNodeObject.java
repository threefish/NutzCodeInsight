package io.github.threefish.idea.restful.tree;

import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.openapi.project.Project;
import io.github.threefish.idea.gotosymbol.AtMappingNavigationItem;
import com.sgaop.util.AtMappingIconUtil;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2020/6/5
 */
public class TreeNodeObject extends NodeDescriptor {


    private final TreeObjectTypeEnum treeObjectType;
    private final String name;
    private AtMappingNavigationItem urlMappingPsiBasedElement;

    public TreeNodeObject(Project project, TreeObjectTypeEnum treeObjectType, String name) {
        super(project, null);
        this.treeObjectType = treeObjectType;
        this.name = name;
        this.init();
    }

    public TreeNodeObject(Project project, AtMappingNavigationItem urlMappingPsiBasedElement) {
        super(project, null);
        this.name = urlMappingPsiBasedElement.getText();
        this.urlMappingPsiBasedElement = urlMappingPsiBasedElement;
        this.treeObjectType = TreeObjectTypeEnum.valueOf(urlMappingPsiBasedElement.getApiType().toString());
        this.init();
    }

    private void init() {
        this.setIcon(AtMappingIconUtil.getIcon(treeObjectType));
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public Object getElement() {
        return null;
    }

    public TreeObjectTypeEnum getTreeObjectType() {
        return treeObjectType;
    }

    public AtMappingNavigationItem getUrlMappingPsiBasedElement() {
        return urlMappingPsiBasedElement;
    }
}
