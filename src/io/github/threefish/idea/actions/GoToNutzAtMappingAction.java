package io.github.threefish.idea.actions;

import com.intellij.ide.actions.GotoActionBase;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import io.github.threefish.idea.gotosymbol.AtMappingModel;
import io.github.threefish.idea.gotosymbol.AtMappingNavigationItem;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/30 0030
 */
public class GoToNutzAtMappingAction extends GotoActionBase {
    @Override
    protected void gotoActionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(CommonDataKeys.PROJECT);
        this.showNavigationPopup(anActionEvent, new AtMappingModel(project), new GoToRequestMappingActionCallback(), true);
    }

    private class GoToRequestMappingActionCallback extends GotoActionBase.GotoActionCallback<String> {
        @Override
        public void elementChosen(ChooseByNamePopup popup, Object element) {
            if (element instanceof AtMappingNavigationItem) {
                AtMappingNavigationItem el = (AtMappingNavigationItem) element;
                if (el.canNavigate()) {
                    el.navigate(true);
                }
            }
        }
    }
}
