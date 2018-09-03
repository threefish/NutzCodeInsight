package com.sgaop.project.module;

import com.intellij.ide.util.projectWizard.JavaModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.SourcePathsBuilder;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.sgaop.project.ui.IntroductionUi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * https://github.com/JetBrains/intellij-community/blob/master/plugins/maven/src/main/java/org/jetbrains/idea/maven/wizards/MavenModuleWizardStep.java
 */
public class NutzBootModuleBuilder extends JavaModuleBuilder implements SourcePathsBuilder {

    @Override
    public ModuleType getModuleType() {
        return NutzBootModuleType.getInstance();
    }

    @Nullable
    @Override
    public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {

        return new ModuleWizardStep() {
            @Override
            public JComponent getComponent() {
                return new IntroductionUi().getRoot();
            }

            @Override
            public void updateDataModel() {

            }
        };
    }


    @Nullable
    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext context, @NotNull ModulesProvider provider) {
        return new ModuleWizardStep[]{new NutzBootMakerChooseStep(context)};
    }

}