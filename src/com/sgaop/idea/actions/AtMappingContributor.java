package com.sgaop.idea.actions;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.DumbAware;
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
public class AtMappingContributor implements ChooseByNameContributor, DumbAware {


    private List<AtMappingItem> navigationItems = new ArrayList<>();

    private Project project;

    /**
     * 无参数构筑函数满足在插件中使用
     */
    public AtMappingContributor() {
    }

    /**
     * 有参满足在action中调用
     *
     * @param project
     */
    public AtMappingContributor(Project project) {
        this.project = project;
        ApplicationManager.getApplication().invokeLater(() -> findAllAt(project));
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
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        if (this.project == null) {
            //无参调用方式
            ApplicationManager.getApplication().invokeLater(() -> findAllAt(project));
        }
        return navigationItems.stream().map(atMappingItem -> atMappingItem.getName()).distinct().toArray(String[]::new);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        return navigationItems.stream().filter(it -> it.getUrlPath().indexOf(pattern) > -1).toArray(NavigationItem[]::new);
    }

}
