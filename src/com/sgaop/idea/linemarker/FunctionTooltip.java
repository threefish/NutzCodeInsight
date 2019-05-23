package com.sgaop.idea.linemarker;

import com.intellij.util.Function;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * @date 2018/1/3 0003 22:43
 */
public class FunctionTooltip implements Function {

    String msg = "点我快速切换至对应文件";

    public FunctionTooltip() {
    }

    public FunctionTooltip(String msg) {
        this.msg = msg;
    }

    @Override
    public Object fun(Object o) {
        return msg;
    }

}
