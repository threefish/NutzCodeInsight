package com.sgaop.idea.actions;

import com.intellij.ide.actions.GotoActionBase;
import com.intellij.ide.util.NavigationItemListCellRenderer;
import com.intellij.ide.util.gotoByName.*;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import com.intellij.util.Processor;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/30 0030
 */
public class GoToNutzAtMappingAction2 extends GotoActionBase {
    @Override
    protected void gotoActionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(CommonDataKeys.PROJECT);
        ChooseByNamePopup popup = ChooseByNamePopup.createPopup(project, new ChooseByNameM(), new Provider());
        popup.setShowListForEmptyPattern(true);
        popup.setAdText("ad text");
        popup.invoke(new ChooseByNamePopupComponent.Callback() {
            @Override
            public void elementChosen(Object o) {

            }
        }, ModalityState.defaultModalityState(), false);
    }

    class Provider implements ChooseByNameItemProvider {

        @Override
        public List<String> filterNames(@NotNull ChooseByNameBase base, @NotNull String[] names, @NotNull String pattern) {
            return Arrays.asList("a", "b");
        }

        @Override
        public boolean filterElements(@NotNull ChooseByNameBase base, @NotNull String pattern, boolean everywhere,
                                      @NotNull ProgressIndicator cancelled, @NotNull Processor<Object> consumer) {
            List l = Arrays.asList("a", "b");
            return ContainerUtil.process(l, consumer);
        }
    }

    class ChooseByNameM implements ChooseByNameModel {


        @Override
        public String getPromptText() {
            return "getPromptText";
        }

        @NotNull
        @Override
        public String getNotInMessage() {
            return "getNotInMessage";
        }

        @NotNull
        @Override
        public String getNotFoundMessage() {
            return "getNotFoundMessage";
        }

        @Nullable
        @Override
        public String getCheckBoxName() {
            return "getCheckBoxName";
        }

        @Override
        public boolean loadInitialCheckBoxState() {
            return false;
        }

        @Override
        public void saveInitialCheckBoxState(boolean b) {

        }

        @Override
        public ListCellRenderer getListCellRenderer() {
            return new NavigationItemListCellRenderer();
        }

        @NotNull
        @Override
        public String[] getNames(boolean b) {
            return new String[0];
        }

        @NotNull
        @Override
        public Object[] getElementsByName(@NotNull String s, boolean b, @NotNull String s1) {
            return new Object[0];
        }

        @Nullable
        @Override
        public String getElementName(@NotNull Object o) {
            return null;
        }

        @NotNull
        @Override
        public String[] getSeparators() {
            return new String[0];
        }

        @Nullable
        @Override
        public String getFullName(@NotNull Object o) {
            return null;
        }

        @Nullable
        @Override
        public String getHelpId() {
            return null;
        }

        @Override
        public boolean willOpenEditor() {
            return false;
        }

        @Override
        public boolean useMiddleMatching() {
            return false;
        }


    }

    private class GoToRequestMappingActionCallback extends GotoActionCallback<String> {
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
