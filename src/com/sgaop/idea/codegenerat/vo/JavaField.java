package com.sgaop.idea.codegenerat.vo;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/12/24
 */
public class JavaField {
    /**
     * JAVA字段名称
     */
    String name;

    /**
     * 主键
     */
    boolean primaryKey;

    /**
     * 字段描述
     */
    String comment;

    /**
     * 数据库字段值
     */
    String dbName;

    /**
     * JAVA字段类型
     */
    String type;

    /**
     * JAVA字段类型包含引用
     */
    String fullType;

    /**
     * 日期
     */
    boolean date;

    /**
     * 是字典
     */
    boolean dict;
    /**
     * 字典CODE
     */
    String dictCode;

    public JavaField() {
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isDate() {
        return date;
    }

    public void setDate(boolean date) {
        this.date = date;
    }

    public boolean isDict() {
        return dict;
    }

    public void setDict(boolean dict) {
        this.dict = dict;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    @Override
    public String toString() {
        return "JavaField{" +
                "name='" + name + '\'' +
                ", primaryKey=" + primaryKey +
                ", comment='" + comment + '\'' +
                ", dbName='" + dbName + '\'' +
                ", type='" + type + '\'' +
                ", fullType='" + fullType + '\'' +
                ", date=" + date +
                ", dict=" + dict +
                ", dictCode='" + dictCode + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFullType() {
        return fullType;
    }

    public void setFullType(String fullType) {
        this.fullType = fullType;
    }
}
