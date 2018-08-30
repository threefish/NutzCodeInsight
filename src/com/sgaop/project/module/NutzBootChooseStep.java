package com.sgaop.project.module;

import com.intellij.ide.util.projectWizard.ProjectJdkStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.ide.wizard.CommitStepException;
import com.intellij.openapi.options.ConfigurationException;
import com.sgaop.project.ui.ModuleWizardStepUI;

import javax.swing.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 */
public class NutzBootChooseStep extends ProjectJdkStep {

    protected final WizardContext wizardContext;
    private ModuleWizardStepUI moduleWizardStepUI;

    public NutzBootChooseStep(WizardContext wizardContext) {
        super(wizardContext);
        this.wizardContext = wizardContext;
        this.moduleWizardStepUI = new ModuleWizardStepUI();
    }

    @Override
    public JComponent getComponent() {
        return moduleWizardStepUI.getRoot();
    }

    @Override
    public void updateDataModel() {
        System.out.println("updateDataModel");
    }

    @Override
    public void onWizardFinished() throws CommitStepException {
        System.out.println("onWizardFinished");
    }

    @Override
    public boolean validate() throws ConfigurationException {
        System.out.println("validate");
        return true;
    }

    @Override
    public void onStepLeaving() {
        System.out.println("onStepLeaving");
    }

    @Override
    public boolean isStepVisible() {
        System.out.println("isStepVisible");
        return true;
    }

    @Override
    public void disposeUIResources() {
        System.out.println("disposeUIResources");
    }

    @Override
    public void updateStep() {
        System.out.println("updateStep");
    }

}