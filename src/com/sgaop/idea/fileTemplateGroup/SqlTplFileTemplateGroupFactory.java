package com.sgaop.idea.fileTemplateGroup;

import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory;
import com.sgaop.util.IconsUtil;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/4/2
 */
public class SqlTplFileTemplateGroupFactory implements FileTemplateGroupDescriptorFactory {
    public static final String NUTZ_SQL_TPL_XML = "NutzSqlTpl.xml";

    @Override
    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        FileTemplateGroupDescriptor group = new FileTemplateGroupDescriptor("NutzSqlTpl", IconsUtil.NUTZ);
        group.addTemplate(new FileTemplateDescriptor(NUTZ_SQL_TPL_XML));
        return group;
    }
}
