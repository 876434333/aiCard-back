package com.vma.push.business.service;

import com.vma.push.business.entity.CircleZanLog;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
public interface ICircleZanLogService extends IBaseService<CircleZanLog,String> {
    /**
     * 点赞/取消赞
     * @param circleZanLog
     */
    String zan(CircleZanLog circleZanLog);
}
