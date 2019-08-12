package com.sgaop.idea;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * <p>
 * 创建时间: 2018/1/22  19:23
 */
public class NutzCons {

    public static final String LOCALIZATION = "org.nutz.mvc.annotation.Localization";
    public static final String OK = "org.nutz.mvc.annotation.Ok";
    public static final String AT = "org.nutz.mvc.annotation.At";
    public static final String GET = "org.nutz.mvc.annotation.GET";
    public static final String POST = "org.nutz.mvc.annotation.POST";
    public static final String DELETE = "org.nutz.mvc.annotation.DELETE";
    public static final String PUT = "org.nutz.mvc.annotation.PUT";
    public static final String SQLS_XML = "com.github.threefish.nutz.sqltpl.SqlsXml";
    public static final List<String> SQLS = Arrays.asList("org.nutz.dao.entity.annotation.SQL");

    public static final Icon NUTZ = IconLoader.findIcon("/icons/nutz.png");


}
