package com.sgaop.idea.project.ui.gui;

import com.sgaop.idea.project.module.vo.NutzBootItemVO;

import javax.swing.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/8/31
 */
public class DataCheckBox extends JCheckBox {

    NutzBootItemVO itemVO;

    public DataCheckBox(String text, NutzBootItemVO itemVO) {
        super(text);
        this.itemVO = itemVO;
    }

    public NutzBootItemVO getItemVO() {
        return itemVO;
    }

    public void setItemVO(NutzBootItemVO itemVO) {
        this.itemVO = itemVO;
    }
}
