package com.holyw.holyw.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Cron {
    @Id
    @GeneratedValue
    private Integer no;
    /**
     * cron_id
     */
    private String cronId;
    /**
     * cron
     */
    private String cron;

    /**
     * 状态("1":有效   "0":无效)
     */
    private String status;

    public Cron() {
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getCronId() {
        return cronId;
    }

    public void setCronId(String cronId) {
        this.cronId = cronId;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}