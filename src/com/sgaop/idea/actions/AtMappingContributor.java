package com.sgaop.idea.actions;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.sgaop.idea.gotosymbol.AtMappingNavigationItem;
import com.sgaop.util.FindRequestMappingItemsUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/30 0030 23:50
 */
public class AtMappingContributor implements ChooseByNameContributor, DumbAware {

    private static boolean lock = false;
    /**
     * 按项目进行缓存
     */
    private ConcurrentHashMap<String, List<AtMappingItem>> data = new ConcurrentHashMap<>();

    public AtMappingContributor() {
    }

    private synchronized void findAllAt(Project project) {
        synchronized (this) {
            if (lock == false) {
                //加锁
                lock = true;
                List<AtMappingItem> itemList = FindRequestMappingItemsUtil.findRequestMappingItems(project);
                if (itemList != null) {
                    data.put(project.getLocationHash(), itemList);
                }
                //解锁
                lock = false;
            }
        }
    }

    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        if (data.getOrDefault(project.getLocationHash(), new ArrayList<>()).size() == 0) {
            findAllAt(project);
        } else if (lock == false) {
            ApplicationManager.getApplication().invokeLater(() -> findAllAt(project));
        }
        return data.getOrDefault(project.getLocationHash(), new ArrayList<>()).stream().map(atMappingItem -> atMappingItem.getName()).distinct().toArray(String[]::new);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        List<AtMappingNavigationItem> atMappingNavigationItems = new ArrayList<>();
        data.getOrDefault(project.getLocationHash(), new ArrayList<>()).stream().filter(it -> matche(it.getUrlPath(), pattern)).forEach(atMappingItem -> {
            atMappingNavigationItems.add(new AtMappingNavigationItem(atMappingItem.getTargetElement(), atMappingItem.getPresentableText()));
        });
        return atMappingNavigationItems.toArray(new NavigationItem[0]);
    }

    public boolean matche(String url, String pattern) {
        return url.toLowerCase().indexOf(pattern.toLowerCase()) > -1;
    }
}
