package com.sgaop.idea.codeinsight.folding;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 306955302@qq.com
 * 创建人：黄川
 * 创建时间: 2018/1/25  10:56
 * 描述此类：
 */
public class NutzLocalizationFoldingDescriptor extends FoldingDescriptor {

    private String value;

    public NutzLocalizationFoldingDescriptor(@NotNull ASTNode node, @NotNull TextRange range, String value) {
        super(node, range);
        this.value = value;
    }


    @Nullable
    @Override
    public String getPlaceholderText() {
        String valueOf = this.value;
        return valueOf == null ? "" : valueOf.replaceAll("\n", "\\n").replaceAll("\"", "\\\\\"");
    }
}
