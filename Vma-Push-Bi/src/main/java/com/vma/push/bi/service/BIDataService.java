package com.vma.push.bi.service;

/**
 * Created by chenzui on 2018/5/19.
 */
public interface BIDataService {

    /**
     * 生成当日BI_USER_LOG数据
     */
    public void createBIUserLog() throws Exception;

    public void createTotal();

    /**
     * 生成当日BI动作日志
     */
    public void createBIActionLog() throws Exception;


    /**
     * 生成跟进用户数据
     */
    public void createBIAttachUserLog();


    /**
     * 生成每日订单BI数据
     */
    public void createOrderLog() throws Exception;


    public void addRela();

    public void moveClickData();

    public void changeAdvStatus();

}
