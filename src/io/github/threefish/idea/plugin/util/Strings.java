package io.github.threefish.idea.plugin.util;

import org.jetbrains.annotations.Nullable;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2019/1/20 0020
 */
public class Strings {

    public static boolean isNullOrEmpty(@Nullable String s) {
        return s == null || s.isEmpty();
    }

    public static String nullToEmpty(@Nullable String s) {
        return s == null ? "" : s;
    }
}
