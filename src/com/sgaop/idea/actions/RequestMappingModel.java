package com.sgaop.idea.actions;

import com.intellij.ide.util.gotoByName.CustomMatcherModel;
import com.intellij.ide.util.gotoByName.FilteringGotoByModel;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * @date 2017/12/30 0030 23:48
 */
public class RequestMappingModel extends FilteringGotoByModel<FileType> implements DumbAware, CustomMatcherModel {

    public RequestMappingModel(@NotNull Project project) {
        super(project, Arrays.asList(new RequestMappingContributor()).toArray(new ChooseByNameContributor[0]));
    }

    protected RequestMappingModel(@NotNull Project project, @NotNull ChooseByNameContributor[] chooseByNameContributors) {
        super(project, chooseByNameContributors);
    }

    @Nullable
    @Override
    protected FileType filterValueFor(NavigationItem navigationItem) {
        return null;
    }

    @Override
    public String getPromptText() {
        return null;
    }

    @Override
    public String getNotInMessage() {
        return null;
    }

    @Override
    public String getNotFoundMessage() {
        return null;
    }

    @Nullable
    @Override
    public String getCheckBoxName() {
        return null;
    }

    @Override
    public char getCheckBoxMnemonic() {
        return 0;
    }

    @Override
    public boolean loadInitialCheckBoxState() {
        return false;
    }

    @Override
    public void saveInitialCheckBoxState(boolean b) {

    }

    @NotNull
    @Override
    public String[] getSeparators() {
        return new String[0];
    }

    @Nullable
    @Override
    public String getFullName(Object o) {
        return null;
    }

    @Override
    public boolean willOpenEditor() {
        return false;
    }

    @Override
    public boolean matches(@NotNull String s, @NotNull String s1) {
        return false;
    }
}
