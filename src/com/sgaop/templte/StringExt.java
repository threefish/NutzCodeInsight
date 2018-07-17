package com.sgaop.templte;


/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2017/12/8  10:59
 * 描述此类：
 */
public class StringExt {

    /**
     * 首字母大写转换
     *
     * @param string
     * @return
     */
    public String capitalize(String string) {
        String first = string.substring(0, 1);
        String end = string.substring(1, string.length());
        return first.toUpperCase() + end;
    }

    /**
     * 首字母小写转换
     *
     * @param string
     * @return
     */
    public String uncapitalize(String string) {
        String first = string.substring(0, 1);
        String end = string.substring(1, string.length());
        return first.toLowerCase() + end;
    }
}
