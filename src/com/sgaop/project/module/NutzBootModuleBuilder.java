package com.sgaop.project.module;

import com.intellij.ide.util.projectWizard.JavaModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.ProjectJdkStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.roots.ModifiableRootModel;
import org.jetbrains.annotations.Nullable;

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

    @Nullable
    @Override
    public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        return new ProjectJdkStep(context);
    }
}