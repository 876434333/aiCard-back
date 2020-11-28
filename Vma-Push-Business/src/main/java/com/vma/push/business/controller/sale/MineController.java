package com.vma.push.business.controller.sale;

import com.vma.push.business.dto.req.ReqAiUpdateTemp;
import com.vma.push.business.dto.req.ReqUpdateDeploy;
import com.vma.push.business.service.IStaffInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By ChenXiAoBin
 * on 2018/7/24
 */
@RestController
@RequestMapping("/v3.0")
@Api(value = "销售端我的接口模版", description = "销售端--我的接口模版", tags = {"MineController"})
public class MineController {
    private Logger LOG = Logger.getLogger(this.getClass());
    @Autowired
    private IStaffInfoService iStaffInfoService;

    @ApiOperation(value = "修改名片模版",notes = "修改名片模版")
    @RequestMapping(value = "/ai/update_template",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqAiUpdateTemp",required = true,value = "修改名片模版",dataType = "ReqAiUpdateTemp")
    public void change(@RequestBody ReqAiUpdateTemp reqAiUpdateTemp){
        LOG.info("修改名片模版");
        iStaffInfoService.updateTemplate(reqAiUpdateTemp);
    }
}
