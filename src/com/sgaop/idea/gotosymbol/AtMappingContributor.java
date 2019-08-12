package com.sgaop.idea.gotosymbol;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.sgaop.util.FindRequestMappingItemsUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/30 0030 23:50
 */
public class AtMappingContributor implements ChooseByNameContributor, DumbAware {

    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        return FindRequestMappingItemsUtil.findAtMappingCache.getNames(project);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        return FindRequestMappingItemsUtil.findAtMappingCache.getItemsByName(name, pattern, project);
    }


}
