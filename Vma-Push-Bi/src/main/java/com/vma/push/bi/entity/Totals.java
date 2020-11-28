package com.vma.push.bi.entity;

public class Totals {
    private String id;

    private Integer zhuanOld;

    private Integer zhuanToday;

    private Integer zanOld;

    private Integer zanToday;

    private Integer viewOld;

    private Integer viewToday;

    private String staffId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getZhuanOld() {
        return zhuanOld;
    }

    public void setZhuanOld(Integer zhuanOld) {
        this.zhuanOld = zhuanOld;
    }

    public Integer getZhuanToday() {
        return zhuanToday;
    }

    public void setZhuanToday(Integer zhuanToday) {
        this.zhuanToday = zhuanToday;
    }

    public Integer getZanOld() {
        return zanOld;
    }

    public void setZanOld(Integer zanOld) {
        this.zanOld = zanOld;
    }

    public Integer getZanToday() {
        return zanToday;
    }

    public void setZanToday(Integer zanToday) {
        this.zanToday = zanToday;
    }

    public Integer getViewOld() {
        return viewOld;
    }

    public void setViewOld(Integer viewOld) {
        this.viewOld = viewOld;
    }

    public Integer getViewToday() {
        return viewToday;
    }

    public void setViewToday(Integer viewToday) {
        this.viewToday = viewToday;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}