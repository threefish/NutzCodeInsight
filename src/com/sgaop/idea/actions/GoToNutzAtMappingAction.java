package com.sgaop.idea.actions;

import com.intellij.ide.actions.GotoActionBase;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/30 0030
 */
public class GoToNutzAtMappingAction extends GotoActionBase implements DumbAware {
    @Override
    protected void gotoActionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(CommonDataKeys.PROJECT);
        AtMappingModel model = new AtMappingModel(project);
        showNavigationPopup(anActionEvent, model, new GoToRequestMappingActionCallback(), false);
    }

    private class GoToRequestMappingActionCallback extends GotoActionBase.GotoActionCallback<String> {
        @Override
        public void elementChosen(ChooseByNamePopup popup, Object element) {
            if (element instanceof AtMappingItem) {
                AtMappingItem el = (AtMappingItem) element;
                if (el.canNavigate()) {
                    el.navigate(true);
                }
            }
        }
    }
}
