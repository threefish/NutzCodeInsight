package io.github.threefish.idea.plugin.project;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import io.github.threefish.idea.plugin.linemarker.vo.JavaNutzTemplateVO;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 黄川 huchuc@vip.qq.com
 * <p>
 * 创建时间: 2017/12/11  14:09
 */
@State(name = "NutzCodeInsight", storages = {@Storage("NutzCodeInsight.xml")})
public class ToolCfiguration implements PersistentStateComponent<ToolCfiguration> {

    GlobalSettingVO settingVO = GlobalSettingVO.loadDefualt();

    @Nullable
    public static ToolCfiguration getInstance() {
        return ServiceManager.getService(ToolCfiguration.class);
    }

    @Override
    @Nullable
    public ToolCfiguration getState() {
        return this;
    }

    @Override
    public void loadState(ToolCfiguration cfiguration) {
        XmlSerializerUtil.copyBean(cfiguration, this);
    }

    /**
     * 取得模版
     *
     * @param urlReg
     * @return
     */
    public JavaNutzTemplateVO getTemplate(String urlReg) {
        Iterator<String> iterator = this.settingVO.getTableData().keySet().iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            if (urlReg.startsWith(name)) {
                return new JavaNutzTemplateVO(name, this.settingVO.getTableData().get(name));
            }
        }
        return null;
    }

    public GlobalSettingVO getSettingVO() {
        return this.settingVO;
    }

    public void setSettingVO(GlobalSettingVO settingVO) {
        this.settingVO = settingVO;
    }

}
