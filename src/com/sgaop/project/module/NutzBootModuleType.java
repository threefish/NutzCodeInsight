package com.sgaop.project.module;

import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 */
public class NutzBootModuleType extends JavaModuleType {

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
        return "NutzBoot Project";
    }


    @Override
    public Icon getNodeIcon(@Deprecated boolean b) {
        return icon;
    }

}