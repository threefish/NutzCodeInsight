package com.sgaop.idea.highlightfilter;

import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/2
 */
public class XmlSqlFileHighlightFilter implements Condition {
    @Override
    public boolean value(Object o) {
        if (o instanceof VirtualFile) {
            try {
                VirtualFile virtualFile = (VirtualFile) o;
                List<String> lines = Files.readAllLines(Paths.get(virtualFile.getCanonicalPath()));
                return false;
            } catch (Exception e) {
                return true;
            }
        } else {
            return false;
        }
    }
}
