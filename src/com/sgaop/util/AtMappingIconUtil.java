package com.sgaop.util;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;
import com.sgaop.idea.enums.NutzApiMethodType;
import com.sgaop.idea.restful.tree.TreeObjectTypeEnum;

import javax.swing.*;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2020/5/22
 */
public class AtMappingIconUtil {


    private static final Icon root = IconLoader.findIcon("/icons/nutz.png");
    private static final Icon module = AllIcons.Nodes.ModuleGroup;
    private static final Icon post = IconLoader.findIcon("/icons/restful/POST.png");
    private static final Icon get = IconLoader.findIcon("/icons/restful/GET.png");
    private static final Icon put = IconLoader.findIcon("/icons/restful/PUT.png");
    private static final Icon delect = IconLoader.findIcon("/icons/restful/DELETE.png");

    public static Icon getIcon(NutzApiMethodType methodType) {
        Icon icon = null;
        switch (methodType) {
            case POST:
                icon = post;
                break;
            case GET:
                icon = get;
                break;
            case DELETE:
                icon = delect;
                break;
            case PUT:
                icon = put;
                break;
            default:
                break;
        }
        return icon;
    }

    public static Icon getIcon(TreeObjectTypeEnum treeObjectType) {
        Icon icon = null;
        switch (treeObjectType) {
            case ROOT:
                icon = root;
                break;
            case MODULE:
                icon = module;
                break;
            case POST:
                icon = post;
                break;
            case GET:
                icon = get;
                break;
            case DELETE:
                icon = delect;
                break;
            case PUT:
                icon = put;
                break;
            default:
                break;
        }
        return icon;
    }


}
