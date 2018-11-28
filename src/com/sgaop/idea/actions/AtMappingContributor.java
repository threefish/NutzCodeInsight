package com.sgaop.idea.actions;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.sgaop.idea.codeinsight.util.FindRequestMappingItemsUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/30 0030 23:50
 */
public class AtMappingContributor implements ChooseByNameContributor, DumbAware {

    private HashMap<String, List<AtMappingItem>> data = new HashMap<>();

    public static AtMappingContributor getInstance(Project project) {
        AtMappingContributor atMappingContributor = ServiceManager.getService(AtMappingContributor.class);
        if (atMappingContributor == null) {
            atMappingContributor = ServiceManager.createLazyKey(AtMappingContributor.class).getValue(project);
        }
        return atMappingContributor;
    }

    private void findAllAt(Project project) {
        List<AtMappingItem> itemList = FindRequestMappingItemsUtil.findRequestMappingItems(project, "At");
        if (itemList != null) {
            data.put(project.getLocationHash(), itemList);
        }
    }

    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        ApplicationManager.getApplication().invokeLater(() -> findAllAt(project));
        return data.getOrDefault(project.getLocationHash(), new ArrayList<>()).stream().map(atMappingItem -> atMappingItem.getName()).distinct().toArray(String[]::new);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        return data.getOrDefault(project.getLocationHash(), new ArrayList<>()).stream().filter(it -> it.getUrlPath().indexOf(pattern) > -1).toArray(NavigationItem[]::new);
    }

}
