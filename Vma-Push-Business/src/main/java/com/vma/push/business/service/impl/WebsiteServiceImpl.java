package com.vma.push.business.service.impl;

import com.vma.push.business.dao.WebsiteTemplateMapper;
import com.vma.push.business.dto.req.website.ReqWebSite;
import com.vma.push.business.dto.rsp.RspWebsite;
import com.vma.push.business.entity.WebsiteTemplate;
import com.vma.push.business.service.IWebsiteService;
import com.vma.push.business.util.UuidUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by chenzui on 2018/5/15.
 */
@Service
public class WebsiteServiceImpl extends AbstractServiceImpl<WebsiteTemplate,String> implements IWebsiteService {
    private WebsiteTemplateMapper mapper;

    public WebsiteServiceImpl(WebsiteTemplateMapper mapper) {
        super(mapper);
        this.mapper = mapper;
    }


    @Override
    public void add(List<ReqWebSite> reqWebSites,String enterpriseId) {
        mapper.delete(enterpriseId);

        for (int i=0;i<reqWebSites.size();i++) {
            ReqWebSite reqWebSite = reqWebSites.get(i);
            WebsiteTemplate template = new WebsiteTemplate();
            template.setId(UuidUtil.getRandomUuid());
            template.setType(reqWebSite.getType());
            template.setEnterpriseId(enterpriseId);
            template.setCreateTime(new Date());
            template.setModifyTime(new Date());
            template.setTextContent(reqWebSite.getText_content());
            template.setOrderNum(i);
            if(reqWebSite.getConfig()!=null) {
                template.setConfig(reqWebSite.getConfig());
            }
            mapper.insertSelective(template);
        }
    }


    @Override
    public List<RspWebsite> findWebsiteByEnterprise(String id) {
        return mapper.findWebsiteByEnterprise(id);
    }

}
