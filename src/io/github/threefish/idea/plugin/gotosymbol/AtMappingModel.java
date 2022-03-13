package io.github.threefish.idea.plugin.gotosymbol;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.util.gotoByName.FilteringGotoByModel;
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
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/30 0030 23:48
 */
public class AtMappingModel extends FilteringGotoByModel<FileType> implements DumbAware {

    public AtMappingModel(@NotNull Project project) {
        super(project, Arrays.asList(new AtMappingContributor()));
    }

    @Nullable
    @Override
    protected FileType filterValueFor(NavigationItem navigationItem) {
        return JavaFileType.INSTANCE;
    }

    @Override
    public String getPromptText() {
        return "输入Nutz Action Url地址";
    }

    @Override
    public String getNotInMessage() {
        return "找不到匹配项";
    }

    @Override
    public String getNotFoundMessage() {
        return "找不到映射";
    }

    @Nullable
    @Override
    public String getCheckBoxName() {
        return null;
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
        return true;
    }
}
