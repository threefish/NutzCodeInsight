package io.github.threefish.idea.gotosymbol;

import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.util.FindClassUtil;
import com.intellij.util.xml.model.gotosymbol.GoToSymbolProvider;
import io.github.threefish.idea.NutzCons;
import com.sgaop.util.FindRequestMappingItemsUtil;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author huchuc@vip.qq.com
 * date: 2019/8/11
 */
public class AtMappingGotoSymbolProvider extends GoToSymbolProvider {

    @Override
    protected void addNames(@NotNull Module module, Set<String> result) {
        List<AtMappingNavigationItem> itemList = FindRequestMappingItemsUtil.findRequestMappingItems(module.getProject());
        if (CollectionUtils.isNotEmpty(itemList)) {
            List<String> temp = itemList.stream().map(atMappingItem -> atMappingItem.getText()).distinct().collect(Collectors.toList());
            result.addAll(temp);
        }
    }

    @Override
    protected void addItems(@NotNull Module module, String name, List<NavigationItem> result) {
        List<AtMappingNavigationItem> itemList = FindRequestMappingItemsUtil.findRequestMappingItems(module.getProject());
        if (CollectionUtils.isNotEmpty(itemList)) {
            result.addAll(itemList);
        }
    }

    @NotNull
    @Override
    protected Collection<Module> calcAcceptableModules(@NotNull Project project) {
        return FindClassUtil.findModulesWithClass(project, NutzCons.AT);
    }

    @Override
    protected boolean acceptModule(Module module) {
        return true;
    }
}
