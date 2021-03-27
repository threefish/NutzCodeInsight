package com.sgaop.idea.project.module.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2018/8/30
 */
public class NutzBootItemVO {

    private String lable;

    private String name;

    private Boolean enable;

    private List<NutzBootProsVO> pros = new ArrayList<>();

    public NutzBootItemVO() {
    }

    public NutzBootItemVO(String lable, String name, Boolean enable, List<NutzBootProsVO> pros) {
        this.lable = lable;
        this.name = name;
        this.enable = enable;
        this.pros = pros;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public List<NutzBootProsVO> getPros() {
        return pros;
    }

    public void setPros(List<NutzBootProsVO> pros) {
        this.pros = pros;
    }

    @Override
    public String toString() {
        return "NutzBootItemVO{" +
                "lable='" + lable + '\'' +
                ", name='" + name + '\'' +
                ", enable=" + enable +
                ", pros=" + pros +
                '}';
    }
}
