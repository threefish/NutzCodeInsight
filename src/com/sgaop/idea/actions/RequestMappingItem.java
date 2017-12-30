package com.sgaop.idea.actions;

import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import org.jetbrains.annotations.Nullable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * @date 2017/12/30 0030 23:53
 */
public class RequestMappingItem implements NavigationItem {
    @Nullable
    @Override
    public String getName() {
        return null;
    }

    @Nullable
    @Override
    public ItemPresentation getPresentation() {
        return null;
    }

    @Override
    public void navigate(boolean b) {

    }

    @Override
    public boolean canNavigate() {
        return false;
    }

    @Override
    public boolean canNavigateToSource() {
        return false;
    }
}
