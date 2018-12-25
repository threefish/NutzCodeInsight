package com.sgaop.templte;

import org.beetl.core.ConsoleErrorHandler;
import org.beetl.core.Resource;
import org.beetl.core.ResourceLoader;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.ErrorInfo;

import java.io.IOException;
import java.io.Writer;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/12/25
 */
public class TemplateErrorHandler extends ConsoleErrorHandler {

    @Override
    public void processExcption(BeetlException e, Writer writer) {
        //判断是不是开发者模式,如果不是调用父类方法(默认输出控制台)
        if (!Boolean.valueOf(e.gt.getConf().getProperty("RESOURCE.autoCheck"))) {
            super.processExcption(e, writer);
        }
        ErrorInfo error = new ErrorInfo(e);
        StringBuilder title = new StringBuilder();
        StringBuilder msg = new StringBuilder();

        if (error.getErrorCode().equals(BeetlException.CLIENT_IO_ERROR_ERROR)) {
            //不输出详细提示信息
            title = new StringBuilder("").append("客户端IO异常:").append(e.resource.getId());
            if (e.getCause() != null) {
                msg.append(e.getCause());
            }
            throwError(title.toString(), msg.toString());
            return;
        }

        int line = error.getErrorTokenLine();

        title = new StringBuilder("").append(error.getType()).append(":").append(error.getErrorTokenText())
                .append(" 位于").append(line).append("行\n\n");

        if (error.getErrorCode().equals(BeetlException.TEMPLATE_LOAD_ERROR)) {
            if (error.getMsg() != null) {
                msg.append(error.getMsg());
            }
            throwError(title.toString(), msg.toString());
            return;
        }

        if (e.getMessage() != null) {
            msg.append(e.getMessage()).append("\n");
        }

        ResourceLoader resLoader = e.gt.getResourceLoader();
        //潜在问题，此时可能得到是一个新的模板，不过可能性很小，忽略！
        String content = null;
        Resource res = resLoader.getResource(e.resource.getId());
        //显示前后三行的内容
        int[] range = this.getRange(line);
        try {
            content = res.getContent(range[0], range[1]);
        } catch (IOException e1) {
        }
        if (content != null) {
            String[] strs = content.split(e.cr);
            int lineNumber = range[0];
            for (int i = 0; i < strs.length; i++) {
                msg.append("" + lineNumber).append("|").append(strs[i].trim()).append("\n");
                lineNumber++;
            }
        }

        if (error.hasCallStack()) {
            msg.append("  ========================").append("\n");
            msg.append("  调用栈:").append("\n");
            for (int i = 0; i < error.getResourceCallStack().size(); i++) {
                msg.append("  " + error.getResourceCallStack().get(i) + " 行：")
                        .append(error.getTokenCallStack().get(i).line).append("\n");
            }
            Throwable t = error.getCause();
            if (t != null) {
                msg.append(t.toString()).append("\n");
            }
        }
        throwError(title.toString(), msg.toString());
    }

    protected void throwError(String title, String msg) {
        throw new RuntimeException(title + msg);
    }


}
