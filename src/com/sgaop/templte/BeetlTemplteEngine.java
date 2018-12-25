package com.sgaop.templte;

import com.sgaop.util.FileUtil;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/30 0030
 */
public class BeetlTemplteEngine implements ITemplteEngine {

    static final StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();

    static Configuration cfg;

    static GroupTemplate gt;

    static {
        try {
            cfg = Configuration.defaultConfiguration();
            cfg.setPlaceholderStart("#[");
            cfg.setPlaceholderEnd("]");
            cfg.setStatementStart("<##");
            cfg.setStatementEnd("##>");
            cfg.setHtmlTagSupport(false);
            cfg.setErrorHandlerClass("com.sgaop.templte.TemplateErrorHandler");
            gt = new GroupTemplate(resourceLoader, cfg);
            gt.registerFunctionPackage("sp", new StringExt());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BeetlTemplteEngine() {

    }

    /**
     * 解析模版
     *
     * @param templeText 模版内容
     * @param bindData   绑定参数
     * @return
     */
    @Override
    public String render(String templeText, Map bindData) throws BeetlException {
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
    public void renderToFile(String templeText, Map bindData, Path path) throws BeetlException{
        Path floderPath = path.getParent();
        if (!floderPath.toFile().exists()) {
            floderPath.toFile().mkdirs();
        }
        FileUtil.strToFile(render(templeText, bindData), path.toFile());
    }
}
