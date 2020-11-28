package com.vma.push.business.dao;

import com.vma.push.business.dto.req.ReqAddWebApplyLog;
import com.vma.push.business.dto.req.ReqPageApply;
import com.vma.push.business.entity.WebApplyLog;

import java.util.List;

/**
 * Created by chenzui on 2018/6/2.
 */
public interface WebApplyLogMapper extends BaseDao<WebApplyLog,String> {
    public List<ReqAddWebApplyLog> find(ReqPageApply page);
}
