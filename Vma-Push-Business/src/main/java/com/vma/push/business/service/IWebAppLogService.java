package com.vma.push.business.service;

import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.ReqAddWebApplyLog;
import com.vma.push.business.dto.req.ReqPageApply;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.WebApplyLog;

/**
 * Created by chenzui on 2018/6/2.
 */
public interface IWebAppLogService extends IBaseService<WebApplyLog,String> {

    public RspPage<ReqAddWebApplyLog> findPage(ReqPageApply page);
}
