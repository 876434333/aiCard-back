package com.vma.push.business.controller.sys;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dao.EnterpriseMapper;
import com.vma.push.business.dto.req.ReqStaffPassword;
import com.vma.push.business.dto.req.ReqUpdateEnterprise;
import com.vma.push.business.dto.req.enterprise.ReqModifyPhone;
import com.vma.push.business.dto.rsp.RspEnterprise;
import com.vma.push.business.entity.Enterprise;
import com.vma.push.business.service.IBasicService;
import com.vma.push.business.service.IEnterpriseService;
import com.vma.push.business.service.IStaffInfoService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * Created by ChenXiaoBin
 * 2018/5/4 5:50
 */
@RestController
@RequestMapping("/v1.0")
@Api(value = "企业基本信息API", description = "管理后台--企业基本信息", tags = {"BasicController"})
public class BasicController  extends ControllerExceptionHandler {
    private Logger LOG = Logger.getLogger(this.getClass());
    @Autowired
    private IEnterpriseService enterpriseService;
   @Autowired
    private IBasicService basicService;
   @Autowired
   private IStaffInfoService staffInfoService;

    /**
     * 查看企业信息
     * @param
     * @return
     */
    @ApiOperation(value = "查看企业信息", notes = "查看企业信息")
    @RequestMapping(value = "/enterprise/staff", method = RequestMethod.GET)
    public RspEnterprise getEnterpriseById(HttpServletRequest request){
        LOG.info("查看企业基本信息");
        String enterpriseId = UserDataUtil.getEnterpriseId(request);
        RspEnterprise rspEnterprise =  basicService.selectEnterprisetById(enterpriseId);
        return rspEnterprise;
    }



    /**
     *修改企业信息
     * @param reqUpdateEnterprise
     */
    @ApiOperation(value = "修改企业信息",notes = "修改企业信息")
    @RequestMapping(value = "/enterprise/update",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateEnterprise",value = "修改企业信息",dataType = "ReqUpdateEnterprise")
    public void updateStatus(@RequestBody ReqUpdateEnterprise reqUpdateEnterprise ,HttpServletRequest request){
        LOG.info("修改企业信息");
        Enterprise enterprise = basicService.selectByPrimaryKey(UserDataUtil.getEnterpriseId(request));
        String enterpriseId = UserDataUtil.getEnterpriseId(request);//缓存中获取企业id
        enterprise.setAddress(reqUpdateEnterprise.getAddress());
        enterprise.setId(enterpriseId);
        enterprise.setHeadIcon(reqUpdateEnterprise.getHead_icon());
        enterprise.setModifyTime(new Date());
        enterprise.setManagerName(reqUpdateEnterprise.getManager_name());
        enterprise.setPhone(reqUpdateEnterprise.getPhone());
        enterprise.setManagerPhone(reqUpdateEnterprise.getManager_phone());
        basicService.updateByPrimaryKeySelective(enterprise);
    }

    /**
     *修改密码
     * @param
     */
    @ApiOperation(value = "修改密码",notes = "修改密码")
    @RequestMapping(value = "/enterprise/password",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqStaffPassword",value = "修改密码",dataType = "ReqStaffPassword")
    public void updatePassword(@RequestBody ReqStaffPassword reqStaffPassword,HttpServletRequest request) {
        String enterprise_id = UserDataUtil.getEnterpriseId(request);//缓存中获取企业id
        Enterprise enterprise =  enterpriseService.selectByPrimaryKey(enterprise_id);
        String oldassWord = enterprise.getPassword();//得到原始密码
        String password = reqStaffPassword.getPass_word();//获得新密码
        if ( oldassWord.equals(password)) {   //如果与原密码相等
            Enterprise sta = new Enterprise();
            sta.setId(enterprise_id);//企业id
            sta.setPassword(reqStaffPassword.getNew_password());
            //staffInfoService.updatePassword(sta);//更新密码
            enterpriseService.updateByPrimaryKeySelective(sta);
        } else {
            throw new BusinessException(ErrCode.LOSE_NUM, "原密码不正确");
        }
    }

    @ApiOperation(value = "校验验证码",notes = "校验验证码，成功通过下一步")
    @RequestMapping(value = "/enterprise/phone/check",method = RequestMethod.PUT)
    public void checkPhone(@ApiParam(value = "手机号",name = "phone") @RequestParam(value = "phone") String phone,
        @ApiParam(value = "验证码",name = "code") @RequestParam(value = "code") String code){
        String redisCode = UserDataUtil.getMsgCode(phone);
        if(redisCode != null && !"".equals(redisCode)){
            if(!code.equals(redisCode)){
                throw new BusinessException(ErrCode.MSG_CODE_ERROR,"验证码错误");
            }
        }else {
            throw new BusinessException(ErrCode.MSG_CODE_TIME_OUT,"请重新请求验证码");
        }

    }

    @ApiModelProperty(value = "修改手机号与密码")
    @ApiImplicitParam(value = "reqModifyPhone",name = "reqModifyPhone",required = true,dataType = "ReqModifyPhone")
    @RequestMapping(value = "/enterprise/phone",method = RequestMethod.PUT)
    public void modifyPhone(@RequestBody ReqModifyPhone reqModifyPhone,HttpServletRequest request){
        LOG.info("修改手机号与密码");

        basicService.modifyPhone(reqModifyPhone,request);
    }


}
