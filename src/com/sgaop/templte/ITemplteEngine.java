package com.sgaop.templte;

import java.nio.file.Path;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * 创建人: 黄川
 * 创建时间: 2017/12/4  10:59
 * 描述此类：
 */
public interface ITemplteEngine {
    /**
     * 解析模版
     *
     * @param templePath 模版地址
     * @param bindData   绑定参数
     * @return
     */
    String render(String templePath, Map bindData) throws Exception;

    void renderToFile(String templePath, Map bindData, Path path);
}
