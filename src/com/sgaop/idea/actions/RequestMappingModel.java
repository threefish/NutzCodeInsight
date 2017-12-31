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
        return "Enter mapping url";
    }

    @Override
    public String getNotInMessage() {
        return "No matches found";
    }

    @Override
    public String getNotFoundMessage() {
        return "Mapping not found";
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
    public String getFullName(Object element) {
        return getElementName(element);
    }

    @Override
    public boolean willOpenEditor() {
        return false;
    }

    @Override
    public boolean matches(@NotNull String popupItem, @NotNull String userPattern) {
        if (userPattern == "/") {
            return true;
        } else if (!userPattern.contains("/")) {
            return userPattern.indexOf(popupItem) > -1;
        } else {
            return isSimilarUrlPaths(popupItem, userPattern);
        }
    }

    private boolean isSimilarUrlPaths(@NotNull String popupItem, @NotNull String userPattern) {
//        String[] popupItemList = popupItem
//                .split("/");
//                .drop(1) //drop method name
//        String[] userPatternList = userPattern
//                .split("/");
//                .dropFirstEmptyStringIfExists()
        return true;
    }
}
