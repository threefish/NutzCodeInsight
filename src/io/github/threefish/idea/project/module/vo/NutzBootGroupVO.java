package io.github.threefish.idea.project.module.vo;

import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2018/8/30
 */
public class NutzBootGroupVO {

    private String lable;

    private Boolean enable;

    private List<NutzBootItemVO> items;

    public NutzBootGroupVO() {
    }

    public NutzBootGroupVO(String lable, Boolean enable, List<NutzBootItemVO> items) {
        this.lable = lable;
        this.enable = enable;
        this.items = items;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public List<NutzBootItemVO> getItems() {
        return items;
    }

    public void setItems(List<NutzBootItemVO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "NutzBootGroupVO{" +
                "lable='" + lable + '\'' +
                ", enable=" + enable +
                ", items=" + items +
                '}';
    }
}
