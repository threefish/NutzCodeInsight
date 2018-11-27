package com.sgaop.idea.actions;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/30 0030 23:50
 */
public class AtMappingContributor implements ChooseByNameContributor {

    private AtServices getAtServices(Project project) {
        AtServices atServices = ServiceManager.getServiceIfCreated(project,AtServices.class);
        if (atServices == null) {
            atServices = ServiceManager.createLazyKey(AtServices.class).getValue(project);
        }
        atServices.init(project);
        return atServices;
    }

    @NotNull
    @Override
    public String[] getNames(Project project, boolean b) {
        AtServices atServices = getAtServices(project);
        return atServices.getNavigationItems().stream().map(atMappingItem -> atMappingItem.getName()).distinct().toArray(String[]::new);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean b) {
        AtServices atServices = getAtServices(project);
        return atServices.getNavigationItems().stream().filter(it -> it.getUrlPath().indexOf(pattern) > -1).toArray(NavigationItem[]::new);
    }

}
