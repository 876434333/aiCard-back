package com.vma.push.business.controller.company;

import com.vma.push.business.dto.req.ReqUpdateEnterprise;
import com.vma.push.business.dto.rsp.RspEnterprise;
import com.vma.push.business.entity.Enterprise;
import com.vma.push.business.service.IBasicService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by huxiangqiang on 2018/7/13.
 */
@RestController(value = "AccountManageController")
@RequestMapping("/V1.0")
@Api(value = "企业后台", description = "账号管理", tags = {"accountManageController"})
public class accountManageController {

    private Logger LOG = Logger.getLogger(this.getClass());
    @Autowired
    private IBasicService basicService;

    /**
     * 查看企业信息
     * @param
     * @return
     */
    @ApiOperation(value = "查看企业信息", notes = "查看企业信息")
    @RequestMapping(value = "/company/enterprise", method = RequestMethod.GET)
    public RspEnterprise getEnterpriseById(HttpServletRequest request){
        LOG.info("查看企业基本信息");
        String enterpriseId = UserDataUtil.getCustomId(request);
        RspEnterprise rspEnterprise =  basicService.selectEnterprisetById(enterpriseId);
        return rspEnterprise;
    }

    /**
     *修改企业信息
     * @param reqUpdateEnterprise
     */
    @ApiOperation(value = "修改企业信息",notes = "修改企业信息")
    @RequestMapping(value = "/company/enterprise",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateEnterprise",value = "修改企业信息",dataType = "ReqUpdateEnterprise")
    public void updateStatus(@RequestBody ReqUpdateEnterprise reqUpdateEnterprise , HttpServletRequest request){
        LOG.info("修改企业信息");
        Enterprise enterprise = basicService.selectByPrimaryKey(UserDataUtil.getCustomId(request));
        String enterpriseId = UserDataUtil.getCustomId(request);//缓存中获取企业id
        enterprise.setAddress(reqUpdateEnterprise.getAddress());
        enterprise.setId(enterpriseId);
        enterprise.setHeadIcon(reqUpdateEnterprise.getHead_icon());
        enterprise.setModifyTime(new Date());
        enterprise.setManagerName(reqUpdateEnterprise.getManager_name());
        enterprise.setPhone(reqUpdateEnterprise.getPhone());
        enterprise.setManagerPhone(reqUpdateEnterprise.getManager_phone());
        basicService.updateByPrimaryKeySelective(enterprise);
    }
}
