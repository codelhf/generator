package com.example.generator.config;

import com.example.generator.util.StringUtil;
import com.example.generator.util.StringUtil;

import java.util.List;

/**
 * @Description: 旨在创建class时，对注释进行控制
 * @Auther: liuhf
 * @CreateTime: 2019/2/27 16:57
 */
public class CommentGenerator {
    /**
     * 作者
     */
    private String author;
    /**
     * 公司
     */
    private String company;
    /**
     * 基础包名
     */
    private String basePackageName;
    /**
     * 生成所有内容
     */
    private boolean suppressAllComments = true;
    /**
     * 生成日期注解
     */
    private boolean suppressDate = true;
    /**
     * 生成日期注解格式
     */
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";
    /**
     * 是否添加备注内容
     */
    private boolean addRemarkComments = true;

    public void validate(List<String> errors) {
        if(StringUtil.isBlank(author)) {
            errors.add("author can not be blank");
        }
        if (StringUtil.isBlank(company)) {
            errors.add("company can not be blank");
        }
        if (StringUtil.isBlank(basePackageName)) {
            errors.add("basePackageName can not be blank");
        }
        if (StringUtil.isBlank(dateFormat)) {
            errors.add("dateFormat can not be blank");
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBasePackageName() {
        return basePackageName;
    }

    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    public boolean isSuppressDate() {
        return suppressDate;
    }

    public void setSuppressDate(boolean suppressDate) {
        this.suppressDate = suppressDate;
    }

    public boolean isSuppressAllComments() {
        return suppressAllComments;
    }

    public void setSuppressAllComments(boolean suppressAllComments) {
        this.suppressAllComments = suppressAllComments;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public boolean isAddRemarkComments() {
        return addRemarkComments;
    }

    public void setAddRemarkComments(boolean addRemarkComments) {
        this.addRemarkComments = addRemarkComments;
    }
}