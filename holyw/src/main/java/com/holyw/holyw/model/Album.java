package com.holyw.holyw.model;



import java.sql.Timestamp;



public class Album implements java.io.Serializable {



    /**
	 *
	 */
	private static final long serialVersionUID = 1016719501293928812L;
	/**
     * id
     */
    private String id;
    /**
     * 相册姓名
     */
    private String name;
    /**
     * 相册描述
     */
    private String desc;

    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 修改时间
     */
    private Timestamp updateTime;

    /**
     * 创建者id
     */
    private String createUser;
    /**
     * 创建者姓名
     */
    private String createUsername;
    /**
     *
     */
    private Timestamp time;
    /**
     *
     */
    private String url;

    public Album() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
