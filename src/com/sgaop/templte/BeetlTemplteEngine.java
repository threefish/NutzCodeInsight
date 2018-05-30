package com.sgaop.templte;

import com.sgaop.util.FileUtil;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * 创建人: 黄川
 * 创建时间: 2017/12/4  10:59
 * 描述此类：
 */
public class BeetlTemplteEngine implements ITemplteEngine {

    final ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("/fileTemplates/");
    Configuration cfg;
    GroupTemplate gt;

    public BeetlTemplteEngine() {
        try {
            cfg = Configuration.defaultConfiguration();
            gt = new GroupTemplate(resourceLoader, cfg);
            gt.registerFunctionPackage("sp", new StringExt());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析模版
     *
     * @param templePath 模版地址
     * @param bindData   绑定参数
     * @return
     */
    @Override
    public String render(String templePath, Map bindData) {
        Template template = gt.getTemplate(templePath);
        template.binding(bindData);
        return template.render();
    }

    /**
     * 解析模版
     *
     * @param templePath 模版地址
     * @param bindData   绑定参数
     * @param path       写入的文件
     * @return
     */
    @Override
    public void renderToFile(String templePath, Map bindData, Path path) {
        Path floderPath = path.getParent();
        if (!floderPath.toFile().exists()) {
            floderPath.toFile().mkdirs();
        }
        FileUtil.strToFile(render(templePath, bindData), path.toFile());
    }
}
