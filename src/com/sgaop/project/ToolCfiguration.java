package com.sgaop.project;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.sgaop.idea.codeinsight.JavaNutzTemplateVO;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com

 * 创建时间: 2017/12/11  14:09

 */
@State(name = "ToolCfiguration", storages = {@Storage("ToolCfiguration.xml")})
public class ToolCfiguration implements PersistentStateComponent<Element> {

    /**
     * 取别名防止以特殊字符起始的key
     */
    private final static String ALIAS = "ALIAS_";
    HashMap<String, String> data = new HashMap<>();

    @Nullable
    public static ToolCfiguration getInstance() {
        ToolCfiguration cfiguration = ServiceManager.getService(ToolCfiguration.class);
        //初始化数据
        HashMap<String, String> initData = new HashMap<>(3);
        initData.put("jsp:", ".jsp");
        initData.put("btl:", ".html");
        initData.put("beetl:", ".html");
        initData.put("->:", ".html");
        for (Map.Entry<String, String> entry : cfiguration.getData().entrySet()) {
            initData.put(entry.getKey(), entry.getValue());
        }
        cfiguration.setData(initData);
        return cfiguration;
    }

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
            String[] data = attribute.getValue().split("\\$\\$");
            this.data.put(data[0], data[1]);
        });
    }

    /**
     * 取得模版
     *
     * @param urlReg
     * @return
     */
    public JavaNutzTemplateVO getTemplate(String urlReg) {
        Iterator<String> iterator = data.keySet().iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            if (urlReg.startsWith(name)) {
                return new JavaNutzTemplateVO(name, data.get(name));
            }
        }
        return null;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }
}
