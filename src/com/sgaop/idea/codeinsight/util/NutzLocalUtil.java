package com.sgaop.idea.codeinsight.util;

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

public class NutzLocalUtil {

    public static List<String> findProperties(Project project, Collection<VirtualFile> virtualFiles, String key) {
        List<String> result = new ArrayList<>();
        for (VirtualFile virtualFile : virtualFiles) {
            PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
            Properties properties = new Properties();
            try (StringReader reader = new StringReader(psiFile.getText())) {
                properties.load(reader);
            } catch (IOException e) {
                e.printStackTrace();
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