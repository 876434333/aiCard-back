package com.vma.push.business.controller.common;

import com.qiniu.common.Zone;
import com.qiniu.util.Auth;
import com.vma.push.business.common.Constants;
import com.vma.push.business.dao.SysConfigMapper;
import com.vma.push.business.dao.WebsiteTemplateMapper;
import com.vma.push.business.dto.rsp.RspWebsite;
import com.vma.push.business.entity.SysConfig;
import com.vma.push.business.entity.WebsiteTemplate;
import com.vma.push.business.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by chenzui on 2018/6/21.
 */
@RestController
@RequestMapping("/V1.0")
@Api(value = "UtilController",description = "工具类",tags = {"UtilController"})
public class UtilController {
    private Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    private WebsiteTemplateMapper websiteTemplateMapper;

    @Autowired
    private QiniuUtils qiniuUtils;

    @Autowired
    private SysConfigMapper sysConfigMapper;
    /**
     * 生成验证码
     */
    @ApiOperation(value = "生成图形验证码", notes = "生成图形验证码")
    @RequestMapping(value = "/common/img/verify", method = RequestMethod.GET)
    public void getVerify(HttpServletRequest request , HttpServletResponse response) {
        try {
//            HttpServletResponse response= request.get
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            LOG.error("获取验证码失败>>>>   ", e);
        }
    }

    /**
     * 获取七牛上传token
     * @return
     */
    @ApiOperation(value = "获取七牛上传token",notes = "获取七牛上传token")
    @RequestMapping(value = "/common/qiniu/{type}/qiniu_token",method = RequestMethod.GET)
    public RspQiniuToken getQiniuToken(HttpServletRequest request, @ApiParam(required=true, name="type", value="类型(1图片)") @PathVariable("type") Integer type){
        LOG.info("获取七牛上传token");
        SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(1);

//        String mac = request.getHeader(Constants.MAC_KEY);
        RspQiniuToken rspQiniuToken = new RspQiniuToken();
        Auth auth = Auth.create(sysConfig.getQiniuAccessKey()  , sysConfig.getQiniuSecretKey());
//        StringMap putPolicy = new StringMap();
        String upToken = auth.uploadToken(sysConfig.getQiniuBucket(),null,Constants.EXPIRE_SECONDS,null);
        rspQiniuToken.setToken(upToken);
        QiniuZone qiniuZone = new QiniuZone();
        List<String> urls = new ArrayList<>();

        //M by PLH at 2019-01-09 for 支持多地域配置 https://developer.qiniu.com/kodo/manual/1671/region-endpoint
        //urls.add("http://up-z2.qiniu.com");
        urls.add(qiniuUtils.getQiNiuZone().upHost(null));

        qiniuZone.setUp_urls(urls);
        rspQiniuToken.setQiniu_zone(qiniuZone);//华南
        if (type == 1){
            rspQiniuToken.setDomain_url(sysConfig.getQiniuUrl());
        }
        return rspQiniuToken;
    }
    @ApiOperation(value = "刷企业官网数据",notes = "刷企业官网数据，旧版更新新版本")
    @RequestMapping(value = "/common/webtemplate/{enterprise_id}",method = RequestMethod.GET)
    public void translateWebConfig( @ApiParam(required=true, name="enterprise_id", value="企业ID") @PathVariable("enterprise_id") String enterpriseId){
        List<RspWebsite> list = websiteTemplateMapper.findWebsiteByEnterprise(enterpriseId);
        for(RspWebsite rspWebsite: list){
            if(StringUtils.isEmpty(rspWebsite.getText_content())){
                websiteTemplateMapper.deleteByPrimaryKey(rspWebsite.getId());
            }else {
                WebsiteTemplate websiteTemplate = new WebsiteTemplate();
                String[] imgs = rspWebsite.getText_content().split(",");
                List<String> imgList = Arrays.asList(imgs);
                int i = 0;
                for(String img:imgList){
                    if(!StringUtils.isEmpty(img)){
                        websiteTemplate.setId(UuidUtil.getRandomUuid());
                        websiteTemplate.setOrderNum(i);
                        websiteTemplate.setEnterpriseId(enterpriseId);
                        websiteTemplate.setTextContent("{\"type\":\"poster\",\"alias\":\"图片\",\"data\":{\"imageShowMode\":{\"type\":\"inputImageShowMode\",\"value\":{\"mode\":\"single\"}},\"image\":{\"type\":\"inputImage\",\"maxSize\":1,\"value\":[{\"url\":"+"\""+img+"\""+",\"width\":440,\"height\":0,\"is_active\":true,\"id\":0}]}},\"_timestamp\":1534233369781}");
                        websiteTemplate.setType(7);
                        websiteTemplate.setCreateTime(new Date());
                        websiteTemplate.setModifyTime(new Date());
                        websiteTemplateMapper.insertSelective(websiteTemplate);

                        websiteTemplateMapper.deleteByPrimaryKey(rspWebsite.getId());
                        i++;
                    }
                }
            }
        }
    }

    public void translateOfferSpec(){

    }
}
