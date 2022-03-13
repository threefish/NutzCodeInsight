package io.github.threefish.idea.plugin.gotosymbol;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import io.github.threefish.idea.plugin.util.FindRequestMappingItemsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huchuc@vip.qq.com
 * date: 2019/8/12
 */
public class FindAtMappingCache {

    private static boolean lock = false;
    /**
     * 按项目进行缓存
     */
    private static final ConcurrentHashMap<String, List<AtMappingNavigationItem>> data = new ConcurrentHashMap<>();

    private synchronized void findAllAt(Project project) {
        synchronized (this) {
            if (lock == false) {
                //加锁
                lock = true;
                List<AtMappingNavigationItem> itemList = FindRequestMappingItemsUtil.findRequestMappingItems(project);
                if (itemList != null) {
                    data.put(project.getLocationHash(), itemList);
                }
                //解锁
                lock = false;
            }
        }
    }

    public String[] getNames(Project project) {
        if (data.getOrDefault(project.getLocationHash(), new ArrayList<>()).size() == 0) {
            findAllAt(project);
        } else if (lock == false) {
            ApplicationManager.getApplication().invokeLater(() -> findAllAt(project));
        }
        return data.getOrDefault(project.getLocationHash(), new ArrayList<>()).stream().map(atMappingItem -> atMappingItem.getName()).distinct().toArray(String[]::new);
    }

    public AtMappingNavigationItem[] getItemsByName(String name, String pattern, Project project) {
        List<AtMappingNavigationItem> atMappingNavigationItems = new ArrayList<>();
        data.getOrDefault(project.getLocationHash(), new ArrayList<>()).stream().filter(it -> matche(it.getText(), pattern)).forEach(atMappingItem -> atMappingNavigationItems.add(atMappingItem));
        return atMappingNavigationItems.toArray(new AtMappingNavigationItem[0]);
    }

    public boolean matche(String url, String pattern) {
        return url.toLowerCase().indexOf(pattern.toLowerCase()) > -1;
    }
}
