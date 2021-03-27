package com.sgaop.idea.restful.ui;

import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.Splitter;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.treeStructure.SimpleTree;

import javax.swing.*;
import java.awt.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2020/6/4
 */
public class RestServicesNavigatorPanel extends SimpleToolWindowPanel {


    private final SimpleTree apiTree;

    private final Splitter servicesContentPaneSplitter;

    public RestServicesNavigatorPanel(SimpleTree tree) {
        super(true, true);
        this.apiTree = tree;
        JScrollPane scrollPane = ScrollPaneFactory.createScrollPane(this.apiTree);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.RED));
        servicesContentPaneSplitter = new Splitter(true, .5f);
        servicesContentPaneSplitter.setShowDividerControls(true);
        servicesContentPaneSplitter.setDividerWidth(10);
        servicesContentPaneSplitter.setBorder(BorderFactory.createLineBorder(Color.RED));
        servicesContentPaneSplitter.setFirstComponent(scrollPane);
        setContent(servicesContentPaneSplitter);
    }


}
