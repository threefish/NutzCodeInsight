package com.sgaop.idea.linemarker.navigation.ui;

/**
 * @author 黄川 huchuc@vip.qq.com
 * date: 2019/5/25
 */
public class JsonFormat {
    /**
     * 是否忽略 JsonShape 注解
     */
    Boolean ignoreJsonShape;
    /**
     * 缩进时用的字符串
     */
    String indentBy;
    /**
     * 是否使用紧凑模式输出
     */
    Boolean compact;
    /**
     * 是否给字段添加双引号
     */
    Boolean quoteName;
    /**
     * 是否忽略null值
     */
    Boolean ignoreNull;
    /**
     * 仅输出的字段的正则表达式
     */
    String actived;
    /**
     * 不输出的字段的正则表达式
     */
    String locked;
    /**
     * 用到的类型转换器
     */
    String castors = "castors";
    /**
     * 分隔符
     */
    String separator;
    /**
     * 是否自动将值应用Unicode编码
     */
    Boolean autoUnicode;
    /**
     * unicode编码用大写还是小写
     */
    Boolean unicodeLower;
    /**
     * 日期格式
     */
    String dateFormat;
    /**
     * 数字格式
     */
    String numberFormat;
    /**
     * 遇到空值的时候写入字符串
     */
    Boolean nullAsEmtry;
    Boolean nullListAsEmpty;
    Boolean nullStringAsEmpty;
    Boolean nullBooleanAsFalse;
    Boolean nullNumberAsZero;
    Boolean timeZone;
    Boolean locale;
    Boolean dateFormatRaw;

    public Boolean getIgnoreJsonShape() {
        return ignoreJsonShape;
    }

    public void setIgnoreJsonShape(Boolean ignoreJsonShape) {
        this.ignoreJsonShape = ignoreJsonShape;
    }

    public String getIndentBy() {
        return indentBy;
    }

    public void setIndentBy(String indentBy) {
        this.indentBy = indentBy;
    }

    public Boolean getCompact() {
        return compact;
    }

    public void setCompact(Boolean compact) {
        this.compact = compact;
    }

    public Boolean getQuoteName() {
        return quoteName;
    }

    public void setQuoteName(Boolean quoteName) {
        this.quoteName = quoteName;
    }

    public Boolean getIgnoreNull() {
        return ignoreNull;
    }

    public void setIgnoreNull(Boolean ignoreNull) {
        this.ignoreNull = ignoreNull;
    }

    public String getActived() {
        return actived;
    }

    public void setActived(String actived) {
        this.actived = actived;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getCastors() {
        return castors;
    }

    public void setCastors(String castors) {
        this.castors = castors;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public Boolean getAutoUnicode() {
        return autoUnicode;
    }

    public void setAutoUnicode(Boolean autoUnicode) {
        this.autoUnicode = autoUnicode;
    }

    public Boolean getUnicodeLower() {
        return unicodeLower;
    }

    public void setUnicodeLower(Boolean unicodeLower) {
        this.unicodeLower = unicodeLower;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }

    public Boolean getNullAsEmtry() {
        return nullAsEmtry;
    }

    public void setNullAsEmtry(Boolean nullAsEmtry) {
        this.nullAsEmtry = nullAsEmtry;
    }

    public Boolean getNullListAsEmpty() {
        return nullListAsEmpty;
    }

    public void setNullListAsEmpty(Boolean nullListAsEmpty) {
        this.nullListAsEmpty = nullListAsEmpty;
    }

    public Boolean getNullStringAsEmpty() {
        return nullStringAsEmpty;
    }

    public void setNullStringAsEmpty(Boolean nullStringAsEmpty) {
        this.nullStringAsEmpty = nullStringAsEmpty;
    }

    public Boolean getNullBooleanAsFalse() {
        return nullBooleanAsFalse;
    }

    public void setNullBooleanAsFalse(Boolean nullBooleanAsFalse) {
        this.nullBooleanAsFalse = nullBooleanAsFalse;
    }

    public Boolean getNullNumberAsZero() {
        return nullNumberAsZero;
    }

    public void setNullNumberAsZero(Boolean nullNumberAsZero) {
        this.nullNumberAsZero = nullNumberAsZero;
    }

    public Boolean getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Boolean timeZone) {
        this.timeZone = timeZone;
    }

    public Boolean getLocale() {
        return locale;
    }

    public void setLocale(Boolean locale) {
        this.locale = locale;
    }

    public Boolean getDateFormatRaw() {
        return dateFormatRaw;
    }

    public void setDateFormatRaw(Boolean dateFormatRaw) {
        this.dateFormatRaw = dateFormatRaw;
    }
}
