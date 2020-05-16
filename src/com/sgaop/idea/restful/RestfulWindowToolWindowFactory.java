package com.sgaop.idea.restful;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2020/5/16
 */
public class RestfulWindowToolWindowFactory implements ToolWindowFactory, DumbAware {

    private static final String TOOL_WINDOW_ID = "RestfulWindow";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

    }

    @Override
    public void init(@NotNull ToolWindow toolWindow) {
        ((ToolWindowEx) toolWindow).setTitleActions(new RefreshAction("刷新", "重新加载URL", AllIcons.Actions.Refresh));
    }


    private abstract class WordBookAction extends DumbAwareAction {

        public WordBookAction(@Nls(capitalization = Nls.Capitalization.Title) @Nullable String text, @Nls(capitalization = Nls.Capitalization.Sentence) @Nullable String description, @Nullable Icon icon) {
            super(text, description, icon);
        }


        @Override
        public void actionPerformed(@NotNull AnActionEvent e) {
            System.out.println("xxx");
        }
    }


    class RefreshAction extends WordBookAction {

        public RefreshAction(@Nls(capitalization = Nls.Capitalization.Title) @Nullable String text, @Nls(capitalization = Nls.Capitalization.Sentence) @Nullable String description, @Nullable Icon icon) {
            super(text, description, icon);
        }


    }
}
