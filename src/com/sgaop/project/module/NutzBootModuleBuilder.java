package com.sgaop.project.module;

import com.intellij.ide.util.projectWizard.*;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.StdModuleTypes;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.projectRoots.SdkTypeId;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.sgaop.project.ui.IntroductionWizardStep;
import com.sgaop.project.ui.NutzBootMakerChooseWizardStep;
import com.sgaop.util.IconsUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * https://github.com/JetBrains/intellij-community/blob/master/plugins/maven/src/main/java/org/jetbrains/idea/maven/wizards/MavenModuleWizardStep.java
 */
public class NutzBootModuleBuilder extends ModuleBuilder implements SourcePathsBuilder {

    private String moduleName;


    @Override
    public void setupRootModel(ModifiableRootModel rootModel) throws ConfigurationException {
        VirtualFile root = createAndGetContentEntry();
        rootModel.addContentEntry(root);
        if (myJdk != null) {
            rootModel.setSdk(myJdk);
        } else {
            rootModel.inheritSdk();
        }
        System.out.println("start to create folder in path");
    }

    private VirtualFile createAndGetContentEntry() {
        String path = FileUtil.toSystemDependentName(getContentEntryPath());
        new File(path).mkdirs();
        return LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
    }

    @Override
    public ModuleType getModuleType() {
        return StdModuleTypes.JAVA;
    }

    @Override
    public List<Pair<String, String>> getSourcePaths() throws ConfigurationException {
        return Collections.emptyList();
    }


    @Override
    public void setSourcePaths(List<Pair<String, String>> sourcePaths) {

    }

    @Override
    public void addSourcePath(Pair<String, String> sourcePathInfo) {

    }

    @Override
    public String getGroupName() {
        return "Nutz boot";
    }

    @Override
    public String getPresentableName() {
        return "Nutz boot";
    }

    @Override
    public String getParentGroup() {
        return JavaModuleType.BUILD_TOOLS_GROUP;
    }

    @Override
    public int getWeight() {
        return JavaModuleBuilder.BUILD_SYSTEM_WEIGHT;
    }


    @Override
    public boolean isSuitableSdkType(SdkTypeId sdkType) {
        return sdkType == JavaSdk.getInstance();
    }


    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
        return new ModuleWizardStep[]{
                new NutzBootMakerChooseWizardStep(this, wizardContext)
        };
    }

    @Nullable
    @Override
    public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        return new IntroductionWizardStep();
    }

    @Override
    public ModuleWizardStep modifyStep(SettingsStep settingsStep) {
        return super.modifyStep(settingsStep);
    }

    @Nullable
    @Override
    public String getBuilderId() {
        return getClass().getName();
    }


    @Nullable
    @Override
    public ModuleWizardStep modifySettingsStep(@NotNull SettingsStep settingsStep) {
        ModuleNameLocationSettings moduleNameLocationSettings = settingsStep.getModuleNameLocationSettings();
        if (moduleNameLocationSettings != null) {
            moduleNameLocationSettings.setModuleName(moduleName);
        }
        return super.modifySettingsStep(settingsStep);
    }


    @Override
    public Icon getNodeIcon() {
        return IconsUtil.NUTZ;
    }

    @Nullable
    @Override
    public Project createProject(String name, String path) {
        return super.createProject(name, path);
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}