package io.github.threefish.idea.linemarker.navigation.runnable;

import com.intellij.openapi.editor.Document;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2019/5/25
 */
public class DocumentReplace implements Runnable {

    /**
     * 当前所在文档
     */
    Document document;

    int startOffset;

    int endOffset;

    CharSequence text;

    public DocumentReplace(Document document, int startOffset, int endOffset, CharSequence text) {
        this.document = document;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.text = text;
    }

    @Override
    public void run() {
        document.replaceString(startOffset, endOffset, text);
    }
}
