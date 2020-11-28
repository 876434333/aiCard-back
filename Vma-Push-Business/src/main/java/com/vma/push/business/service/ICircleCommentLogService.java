package com.vma.push.business.service;

import com.vma.push.business.dto.req.ReqAddComment;
import com.vma.push.business.dto.req.ReqCircleComment;
import com.vma.push.business.dto.rsp.RspCirComFormCompanyPage;
import com.vma.push.business.entity.CircleCommentLog;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
public interface ICircleCommentLogService extends IBaseService<CircleCommentLog,String> {
    void circleComment(CircleCommentLog circleCommentLog);
    void delComment(String id);
    RspCirComFormCompanyPage getAllComment(ReqCircleComment req);
}
