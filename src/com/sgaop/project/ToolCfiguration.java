package com.sgaop.project;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2017/12/11  14:09
 * 描述此类：
 */
@State(name = "ToolCfiguration", storages = {@Storage("ToolCfiguration.xml")})
public class ToolCfiguration implements PersistentStateComponent<Element> {

    HashMap<String, String> data = new HashMap<>();

    public String getConfigString() {
        return "";
    }

    private final static String ALIAS = "ALIAS_";

    @Override
    @Nullable
    public Element getState() {
        Element element = new Element("NutzCodeInsightDataTables");
        int i = 0;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            element.setAttribute(ALIAS + i, entry.getKey() + "$$" + entry.getValue());
        }
        return element;
    }

    @Override
    public void loadState(Element element) {
        element.getAttributes().forEach(attribute -> {
            String[] data=attribute.getValue().split("\\$\\$");
            this.data.put(data[0], data[1]);
        });
    }

    @Nullable
    public static ToolCfiguration getInstance() {
        return ServiceManager.getService(ToolCfiguration.class);
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public HashMap<String, String> getData() {
        return data;
    }
}
