package com.vma.push.business.controller.common;

import com.vma.push.business.dao.EnterApiRelaMapper;
import com.vma.push.business.dao.EnterpriseMapper;
import com.vma.push.business.dto.req.call.ReqDeploy;
import com.vma.push.business.entity.EnterApiRela;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by chenzui on 2018/8/7.
 */
@RestController
@RequestMapping("/V1.0")
@Api(value = "DeployCallBackController",description = "部署回调接口",tags = {"DeployCallBackController"})
public class DeployCallBackController {


    private Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    private EnterApiRelaMapper enterApiRelaMapper;

    @ApiOperation(value = "部署回调")
    @RequestMapping(value = "/common/deploy/info",method = RequestMethod.GET)
    public void deployCallBack(@RequestParam("appid") String appid,@RequestParam("url") String url,@RequestParam("img_url") String img_url){
        LOG.info("部署回调");
        EnterApiRela enterApiRela = new EnterApiRela();
        enterApiRela.setAppId(appid);
        enterApiRela.setApiUrl(url);
        enterApiRela.setImgUrl(img_url);
        enterApiRela.setCreateTime(new Date());
        enterApiRelaMapper.insertSelective(enterApiRela);
    }
}
