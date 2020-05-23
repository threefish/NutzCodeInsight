package com.sgaop.idea.restful;

import com.intellij.ui.treeStructure.CachingSimpleNode;
import com.intellij.ui.treeStructure.SimpleNode;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

public abstract class BaseSimpleNode extends CachingSimpleNode {

    protected BaseSimpleNode(SimpleNode aParent) {
        super(aParent);
    }

    @Nullable
    @NonNls
    String getActionId() {
        return null;
    }

    @Nullable
    @NonNls
    String getMenuId() {
        return null;
    }

    @Override
    public void cleanUpCache() {
        super.cleanUpCache();
    }



}
