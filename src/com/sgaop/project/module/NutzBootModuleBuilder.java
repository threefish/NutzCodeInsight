package com.sgaop.project.module;

import com.intellij.ide.util.projectWizard.JavaModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.SettingsStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModifiableModuleModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @mark https://github.com/JetBrains/intellij-community/blob/master/plugins/maven/src/main/java/org/jetbrains/idea/maven/wizards/MavenModuleWizardStep.java
 */
public class NutzBootModuleBuilder extends JavaModuleBuilder {

    @Override
    public void setupRootModel(ModifiableRootModel model) throws ConfigurationException {
        super.setupRootModel(model);
    }

    @Override
    public ModuleType getModuleType() {
        return NutzBootModuleType.getInstance();
    }

    @Override
    public ModuleWizardStep modifySettingsStep(SettingsStep settingsStep) {
        return super.modifySettingsStep(settingsStep);
    }

    @Nullable
    @Override
    public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        return new NutzBootMakerChooseStep(context);
    }

    @Nullable
    @Override
    public List<Module> commit(@NotNull Project project, ModifiableModuleModel model, ModulesProvider modulesProvider) {
        List<Module> moduleList = super.commit(project, model, modulesProvider);
        System.out.println("我可以重新下载项目");
        return moduleList;
    }


    /**
     * 取得当前模块路径
     *
     * @return
     */
    public String getPath() {
        return getContentEntryPath();
    }

}