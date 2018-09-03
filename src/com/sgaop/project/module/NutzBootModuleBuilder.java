package com.sgaop.project.module;

import com.intellij.ide.util.projectWizard.*;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModuleType;
import org.jetbrains.annotations.Nullable;

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
        return new ProjectJdkStep(context);
    }

}