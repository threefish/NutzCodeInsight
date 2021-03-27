package com.sgaop.util;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;

import java.nio.file.Path;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2019/4/2
 */
public class VirtualFileUtil {
    /**
     * 刷新目录
     *
     * @param path
     */
    public static void refreshPath(Path path) {
        VirtualFile value = VirtualFileManager.getInstance().findFileByUrl(path.toUri().toString());
        if (value != null) {
            value.refresh(true, true);
        }
    }
}
