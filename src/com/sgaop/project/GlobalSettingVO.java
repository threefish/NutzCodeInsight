package com.sgaop.project;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/9/4
 */
public class GlobalSettingVO {

    public static GlobalSettingVO loadDefualt() {
        GlobalSettingVO globalSettingVO = new GlobalSettingVO();
        globalSettingVO.setTableData(new HashMap<>(0));
        globalSettingVO.getTableData().put("jsp:", ".jsp");
        globalSettingVO.getTableData().put("btl:", ".html");
        globalSettingVO.getTableData().put("beetl:", ".html");
        globalSettingVO.getTableData().put("->:", ".html");
        globalSettingVO.setBeetlLayoutRegular("layout\\((\"|\')(.*?)\\.html(\"|\')");
        globalSettingVO.setBeetlIncludeRegular("include\\((\"|\')(.*?)\\.html(\"|\')\\)");
        return globalSettingVO;
    }

    private HashMap<String, String> tableData;

    private String beetlLayoutRegular;

    private String beetlIncludeRegular;

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
