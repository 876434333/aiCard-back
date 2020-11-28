package com.vma.push.business.service;

import com.vma.push.business.dto.req.website.ReqWebSite;
import com.vma.push.business.dto.rsp.RspWebsite;
import com.vma.push.business.entity.WebsiteTemplate;

import java.util.List;

/**
 * Created by chenzui on 2018/5/15.
 */
public interface IWebsiteService extends IBaseService<WebsiteTemplate,String> {
    public void add(List<ReqWebSite> reqWebSites,String enterpriseId);

   List<RspWebsite> findWebsiteByEnterprise(String id);//根据enterprise_id查询
}
