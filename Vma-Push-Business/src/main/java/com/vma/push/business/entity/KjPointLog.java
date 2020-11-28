package com.vma.push.business.entity;

import java.util.Date;

public class KjPointLog {
    private String id;

    private String customId;

    private String content;

    private String operation;

    private Float operationPoint;

    private Float remainPoint;

    private Date createTime;

    private String createBy;

    private String targetId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Float getOperationPoint() {
        return operationPoint;
    }

    public void setOperationPoint(Float operationPoint) {
        this.operationPoint = operationPoint;
    }

    public Float getRemainPoint() {
        return remainPoint;
    }

    public void setRemainPoint(Float remainPoint) {
        this.remainPoint = remainPoint;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
}