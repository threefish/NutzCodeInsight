package io.github.threefish.idea.plugin.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * @author huchuc@vip.qq.com
 * date: 2019/5/23
 */
public class PsiFileUtil {

    public static List<String> findProperties(Project project, Collection<VirtualFile> virtualFiles, String key) {
        List<String> result = new ArrayList<>();
        for (VirtualFile virtualFile : virtualFiles) {
            PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
            Properties properties = new Properties();
            try (StringReader reader = new StringReader(psiFile.getText())) {
                properties.load(reader);
            } catch (IOException e) {
            }
            properties.forEach((name, val) -> {
                if (name.equals(key)) {
                    result.add(String.valueOf(val));
                }
            });
        }
        return result;
    }
}
