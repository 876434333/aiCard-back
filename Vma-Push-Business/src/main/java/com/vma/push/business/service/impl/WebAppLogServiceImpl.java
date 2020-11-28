package com.vma.push.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.vma.push.business.dao.BaseDao;
import com.vma.push.business.dao.WebApplyLogMapper;
import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.ReqAddWebApplyLog;
import com.vma.push.business.dto.req.ReqPageApply;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.dto.rsp.userInfo.RspUserInfoList;
import com.vma.push.business.entity.WebApplyLog;
import com.vma.push.business.service.IWebAppLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenzui on 2018/6/2.
 */
@Service
public class WebAppLogServiceImpl extends AbstractServiceImpl<WebApplyLog,String> implements IWebAppLogService {

    @Autowired
    private WebApplyLogMapper mapper;

    public WebAppLogServiceImpl(WebApplyLogMapper mapper) {
        super(mapper);
        this.mapper = mapper;
    }

    @Override
    public RspPage<ReqAddWebApplyLog> findPage(ReqPageApply page) {
        RspPage<ReqAddWebApplyLog> rspPage = new RspPage<>();
        com.github.pagehelper.Page pageHelper = PageHelper.startPage(page.getPage_num(),page.getPage_size(),true);
        List<ReqAddWebApplyLog> orders = mapper.find(page);
        rspPage.setData_list(orders);
        rspPage.setTotal_num(pageHelper.getTotal());
        rspPage.setPage_num(pageHelper.getPageNum());
        rspPage.setPage_size(pageHelper.getPageSize());
        return rspPage;
    }
}
