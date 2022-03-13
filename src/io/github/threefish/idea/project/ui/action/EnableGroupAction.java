package io.github.threefish.idea.project.ui.action;

import io.github.threefish.idea.project.module.NutzBootMakerChooseWizardStep;
import io.github.threefish.idea.project.ui.gui.DataCheckBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2018/8/31
 */
public class EnableGroupAction implements ActionListener {


    private NutzBootMakerChooseWizardStep.UiCatch uiCatch;

    public EnableGroupAction(NutzBootMakerChooseWizardStep.UiCatch uiCatch) {
        this.uiCatch = uiCatch;
    }

    public EnableGroupAction() {
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        JCheckBox checkBox = (JCheckBox) source;
        if (source instanceof DataCheckBox) {
            //动态更新数据
            DataCheckBox dataCheckBox = (DataCheckBox) source;
            dataCheckBox.getItemVO().setEnable(checkBox.isSelected());
        }
        //控制显示隐藏
        if (uiCatch != null) {
            uiCatch.getjPanel().setVisible(checkBox.isSelected());
        }
    }
}
