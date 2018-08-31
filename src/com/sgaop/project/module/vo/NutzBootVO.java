package com.sgaop.project.module.vo;

import java.util.Arrays;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/8/31
 */
public class NutzBootVO {

    String[] version;

    List<NutzBootGroupVO> groups;

    public NutzBootVO() {
    }

    public NutzBootVO(String[] version, List<NutzBootGroupVO> groups) {
        this.version = version;
        this.groups = groups;
    }

    public String[] getVersion() {
        return version;
    }

    public void setVersion(String[] version) {
        this.version = version;
    }

    public List<NutzBootGroupVO> getGroups() {
        return groups;
    }

    public void setGroups(List<NutzBootGroupVO> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "NutzBootVO{" +
                "version=" + Arrays.toString(version) +
                ", groups=" + groups +
                '}';
    }
}
