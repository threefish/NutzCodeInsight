package com.sgaop.idea.codeinsight.util;

import com.intellij.icons.AllIcons;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * <p>
 * 创建时间: 2018/1/3  14:26
 */
public class IconUtil {
    /**
     * 取得模版图标
     *
     * @param fileExtension
     * @return
     */
    public static Icon getTemplateIcon(String fileExtension) {
        Icon icon;
        switch (fileExtension.toLowerCase()) {
            case ".jsp":
            case "jsp":
                icon = AllIcons.FileTypes.Jsp;
                break;
            case ".html":
            case ".btl":
            case "html":
            case "btl":
                icon = AllIcons.FileTypes.Html;
                break;
            case ".css":
            case "css":
                icon = AllIcons.FileTypes.Css;
                break;
            case ".js":
            case "js":
            case ".gzjs":
            case "gzjs":
                icon = AllIcons.FileTypes.JavaScript;
                break;
            case ".json":
            case "json":
                icon = AllIcons.FileTypes.Json;
                break;
            case ".png":
            case ".jpg":
            case ".gif":
            case "png":
            case "jpg":
            case "gif":
                icon = AllIcons.FileTypes.Any_type;
                break;
            default:
                icon = AllIcons.FileTypes.Any_type;
                break;
        }
        return icon;
    }
}
