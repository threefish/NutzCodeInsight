package com.sgaop.idea.project;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/9/4
 */
public class GlobalSettingVO {

    private HashMap<String, String> tableData;
    /**
     * beetl模版中匹配layout
     */
    private String beetlLayoutRegular;
    /**
     * beetl模版中匹配include得正则表达式
     */
    private String beetlIncludeRegular;
    /**
     * beetl模版中匹配语言国际化
     */
    private String i18nRegular;
    /**
     * beetl模版中国际化取KEY
     */
    private String i18nKeyRegular;

    public static GlobalSettingVO loadDefualt() {
        GlobalSettingVO globalSettingVO = new GlobalSettingVO();
        globalSettingVO.setTableData(new HashMap<>(0));
        globalSettingVO.getTableData().put("jsp:", ".jsp");
        globalSettingVO.getTableData().put("btl:", ".html");
        globalSettingVO.getTableData().put("beetl:", ".html");
        globalSettingVO.getTableData().put("->:", ".html");
        globalSettingVO.setBeetlLayoutRegular("layout\\((\"|')(.*?)\\.html(\"|')");
        globalSettingVO.setBeetlIncludeRegular("include\\((\"|')(.*?)\\.html(\"|')");
        globalSettingVO.setI18nRegular("\\$\\{i18n\\((\"|')(.*?)(\"|')\\)}");
        globalSettingVO.setI18nKeyRegular("\\((\"|')(.*?)(\"|')\\)");
        return globalSettingVO;
    }

    public String getI18nKeyRegular() {
        return i18nKeyRegular;
    }

    public void setI18nKeyRegular(String i18nKeyRegular) {
        this.i18nKeyRegular = i18nKeyRegular;
    }

    public String getI18nRegular() {
        return i18nRegular;
    }

    public void setI18nRegular(String i18nRegular) {
        this.i18nRegular = i18nRegular;
    }

    public HashMap<String, String> getTableData() {
        return tableData;
    }

    public void setTableData(HashMap<String, String> tableData) {
        this.tableData = tableData;
    }

    public String getBeetlLayoutRegular() {
        return beetlLayoutRegular;
    }

    public void setBeetlLayoutRegular(String beetlLayoutRegular) {
        this.beetlLayoutRegular = beetlLayoutRegular;
    }

    public String getBeetlIncludeRegular() {
        return beetlIncludeRegular;
    }

    public void setBeetlIncludeRegular(String beetlIncludeRegular) {
        this.beetlIncludeRegular = beetlIncludeRegular;
    }

    @Override
    public String toString() {
        return String.format("/*%s*/%s", super.toString(), new Gson().toJson(this));
    }
}
