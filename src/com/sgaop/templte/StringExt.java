package com.sgaop.templte;


/**
 * @author 黄川 huchuc@vip.qq.com
 * @date 2017/12/30 0030
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
