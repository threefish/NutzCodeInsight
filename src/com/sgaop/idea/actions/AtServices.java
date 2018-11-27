package com.sgaop.idea.actions;

import com.intellij.openapi.project.Project;
import com.sgaop.idea.codeinsight.util.FindRequestMappingItemsUtil;
import com.sgaop.project.ToolCfiguration;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/11/27
 */
public class AtServices {

    /**
     * 定时任务
     */
    private ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("Cache-urlMapping-schedule-pool-%d").daemon(true).build());

    private ToolCfiguration configuration = ToolCfiguration.getInstance();

    private Project project;

    private List<AtMappingItem> navigationItems = new ArrayList<>();

    /**
     * 初始化
     *
     * @param project
     */
    public void init(Project project) {
        if (this.project == null) {
            executorService.scheduleAtFixedRate(() -> findAllAt(project), 0, configuration.getSettingVO().getCachePeriod(), TimeUnit.SECONDS);
            this.project = project;
        }
    }

    private void findAllAt(Project project) {
        List<AtMappingItem> itemList = FindRequestMappingItemsUtil.findRequestMappingItems(project, "At");
        if (itemList != null) {
            navigationItems.clear();
            navigationItems.addAll(itemList);
        }
    }

    public List<AtMappingItem> getNavigationItems() {
        return navigationItems;
    }
}
