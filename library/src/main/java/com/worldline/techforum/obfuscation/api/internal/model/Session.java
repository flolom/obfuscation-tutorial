package com.worldline.techforum.obfuscation.api.internal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {

    private String description;
    private String title;
    private String author;
    private When when;
    private String bu;
    private List<String> theme = new ArrayList<String>();
    private String _id;
    private Integer where;
    private String day;
    private Map<String, Object> additionalProperties = new HashMap<>();

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The when
     */
    public When getWhen() {
        return when;
    }

    /**
     * 
     * @param when
     *     The when
     */
    public void setWhen(When when) {
        this.when = when;
    }

    /**
     * 
     * @return
     *     The bu
     */
    public String getBu() {
        return bu;
    }

    /**
     * 
     * @param bu
     *     The bu
     */
    public void setBu(String bu) {
        this.bu = bu;
    }

    /**
     * 
     * @return
     *     The theme
     */
    public List<String> getTheme() {
        return theme;
    }

    /**
     * 
     * @param theme
     *     The theme
     */
    public void setTheme(List<String> theme) {
        this.theme = theme;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return _id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this._id = id;
    }

    /**
     * 
     * @return
     *     The where
     */
    public Integer getWhere() {
        return where;
    }

    /**
     * 
     * @param where
     *     The where
     */
    public void setWhere(Integer where) {
        this.where = where;
    }

    /**
     * 
     * @return
     *     The day
     */
    public String getDay() {
        return day;
    }

    /**
     * 
     * @param day
     *     The day
     */
    public void setDay(String day) {
        this.day = day;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
