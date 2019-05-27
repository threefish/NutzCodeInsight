package com.sgaop.idea.psi.java.stubs.index;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiTypeElement;
import com.intellij.psi.impl.java.stubs.index.JavaStubIndexKeys;
import com.intellij.psi.impl.search.JavaSourceFilterScope;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.psi.stubs.StubIndexKey;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author huchuc@vip.qq.com
 * @date: 2019/5/27
 */
public class JavaMethodReturnNameIndex extends StringStubIndexExtension<PsiMethod> {

    public static final StubIndexKey<String, PsiTypeElement> METHOD_RETURN = StubIndexKey.createIndexKey("java.method.return");

    private static final JavaMethodReturnNameIndex ourInstance = new JavaMethodReturnNameIndex();

    public JavaMethodReturnNameIndex() {
    }

    public static JavaMethodReturnNameIndex getInstance() {
        return ourInstance;
    }

    @NotNull
    @Override
    public StubIndexKey<String, PsiMethod> getKey() {
        return JavaStubIndexKeys.METHODS;
    }

    @Override
    public Collection<PsiMethod> get(@NotNull String returnName, @NotNull Project project, @NotNull GlobalSearchScope scope) {
        return StubIndex.getElements(this.getKey(), returnName, project, new JavaSourceFilterScope(scope), PsiMethod.class);
    }
}
