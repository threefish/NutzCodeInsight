package io.github.threefish.idea.plugin.template;

import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory;
import io.github.threefish.idea.plugin.NutzCons;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2019/4/2
 */
public class SqlTplFileTemplateGroupFactory implements FileTemplateGroupDescriptorFactory {
    public static final String NUTZ_SQL_TPL_XML = "NutzSqlTpl.xml";

    @Override
    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
        FileTemplateGroupDescriptor group = new FileTemplateGroupDescriptor("NutzSqlTpl", NutzCons.NUTZ);
        group.addTemplate(new FileTemplateDescriptor(NUTZ_SQL_TPL_XML));
        return group;
    }
}
