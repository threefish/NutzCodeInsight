package com.sgaop.project.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author 黄川 huchuc@vip.qq.com
 */
public class NutzBootModuleType extends ModuleType<NutzBootModuleBuilder> {

    private static final String ID = "NutzBoot";

    Icon icon = IconLoader.findIcon("/icons/nutz.png");

    public NutzBootModuleType() {
        super(ID);
    }

    public static NutzBootModuleType getInstance() {
        return (NutzBootModuleType) ModuleTypeManager.getInstance().findByID(ID);
    }

    @NotNull
    @Override
    public NutzBootModuleBuilder createModuleBuilder() {
        return new NutzBootModuleBuilder();
    }

    @NotNull
    @Override
    public String getName() {
        return ID;
    }

    @NotNull
    @Override
    public String getDescription() {
        return "NutzBoot项目";
    }

    public Icon getBigIcon() {
        return icon;
    }

    @Override
    public Icon getNodeIcon(@Deprecated boolean b) {
        return icon;
    }

    @NotNull
    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull NutzBootModuleBuilder moduleBuilder, @NotNull ModulesProvider modulesProvider) {
        ArrayList<ModuleWizardStep> wizardSteps = new ArrayList<>();
        wizardSteps.add(new NutzBootChooseStep(wizardContext));
        return wizardSteps.toArray(new ModuleWizardStep[]{});
    }
}