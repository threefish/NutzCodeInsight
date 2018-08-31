package com.sgaop.project.module.vo;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/8/30
 */
public class NutzBootProsVO {
    private String name;
    private Object val;

    public NutzBootProsVO() {
    }

    public NutzBootProsVO(String name, Object val) {
        this.name = name;
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "NutzBootProsVO{" +
                "name='" + name + '\'' +
                ", val='" + val + '\'' +
                '}';
    }
}
