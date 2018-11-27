package com.sgaop.idea.actions;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.sgaop.idea.codeinsight.util.FindRequestMappingItemsUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/30 0030 23:50
 */
public class AtMappingContributor implements ChooseByNameContributor {

    private List<AtMappingItem> navigationItems = new ArrayList<>();


    public AtMappingContributor(Project project) {
        ApplicationManager.getApplication().runReadAction(() -> findAllAt(project));
    }

    private void findAllAt(Project project) {
        List<AtMappingItem> itemList = FindRequestMappingItemsUtil.findRequestMappingItems(project, "At");
        if (itemList != null) {
            navigationItems.clear();
            navigationItems.addAll(itemList);
        }
    }

    @NotNull
    @Override
    public String[] getNames(Project project, boolean b) {
        return navigationItems.stream().map(atMappingItem -> atMappingItem.getName()).distinct().toArray(String[]::new);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean b) {
        return navigationItems.stream().filter(it -> it.getUrlPath().indexOf(pattern) > -1).toArray(NavigationItem[]::new);
    }

}
