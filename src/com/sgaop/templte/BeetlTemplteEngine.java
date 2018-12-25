package com.sgaop.templte;

import com.sgaop.util.FileUtil;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * @author : 黄川
 * 创建时间: 2017/12/4  10:59
 * 描述此类：
 */
public class BeetlTemplteEngine implements ITemplteEngine {


    final StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();

    Configuration cfg;

    GroupTemplate gt;

    public BeetlTemplteEngine() {
        try {
            cfg = Configuration.defaultConfiguration();
            cfg.setPlaceholderStart("#[");
            cfg.setPlaceholderEnd("]");
            cfg.setStatementStart("<##");
            cfg.setStatementEnd("##>");
            cfg.setHtmlTagSupport(false);
            gt = new GroupTemplate(resourceLoader, cfg);
            gt.registerFunctionPackage("sp", new StringExt());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析模版
     *
     * @param templeText 模版内容
     * @param bindData   绑定参数
     * @return
     */
    @Override
    public String render(String templeText, Map bindData) {
        Template template = gt.getTemplate(templeText);
        template.binding(bindData);
        return template.render();
    }


    /**
     * 解析模版
     *
     * @param templeText 模版内容
     * @param bindData   绑定参数
     * @param path       写入的文件
     * @return
     */
    @Override
    public void renderToFile(String templeText, Map bindData, Path path) {
        Path floderPath = path.getParent();
        if (!floderPath.toFile().exists()) {
            floderPath.toFile().mkdirs();
        }
        FileUtil.strToFile(render(templeText, bindData), path.toFile());
    }
}
