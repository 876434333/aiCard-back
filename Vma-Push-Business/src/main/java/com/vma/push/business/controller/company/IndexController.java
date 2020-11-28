package com.vma.push.business.controller.company;

import com.vma.push.business.config.SmsConfig;
import com.vma.push.business.dto.rsp.ai.CompanyList;
import com.vma.push.business.dto.rsp.boss.RspViewData;
import com.vma.push.business.dto.rsp.staff.RspIndex;
import com.vma.push.business.dto.rsp.userInfo.DataItem;
import com.vma.push.business.entity.SysConfig;
import com.vma.push.business.service.IBossService;
import com.vma.push.business.service.ILoginService;
import com.vma.push.business.service.ISendSMS;
import com.vma.push.business.util.RandomValidateCodeUtil;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huxiangqiang on 2018/7/13.
 */

@RestController(value = "IndexController")
@RequestMapping("/V1.0")
@Api(value = "企业后台", description = "首页", tags = {"IndexController"})
public class IndexController {

    @Autowired
    private IBossService bossService;
    @Autowired
    private ILoginService loginService;
    @Autowired
    private ISendSMS sendSMSService;
    @Autowired
    private SmsConfig smsConfig;

    @ApiOperation(value = "名片账号状态",notes ="名片账号状态" )
    @RequestMapping(value = "/company/index/main",method = RequestMethod.GET)
    public RspIndex viewdata(HttpServletRequest request){
        String enterpriseId = UserDataUtil.getCustomId(request);
        return bossService.cardAndAcount(enterpriseId);
    }

    @ApiOperation(value = "查询总览数据",notes ="查询总览数据" )
    @RequestMapping(value = "/company/index/view",method = RequestMethod.GET)
    public List<RspViewData> viewdata(@ApiParam(value = "1汇总2昨日3近七天4近30天",name = "type") @RequestParam(value = "type") Integer type , HttpServletRequest request){
        String enterpriseId = UserDataUtil.getCustomId(request);
//       String departmentId = UserDataUtil.getDepartmentId(request);
        return bossService.viewdata(type,null,"1",enterpriseId);
    }

    @ApiOperation(value = "查询用户新增情况",notes = "查询用户新增情况")
    @RequestMapping(value = "/company/index/user",method = RequestMethod.GET)
    public List<DataItem> countUserAdd(@ApiParam(value = "7,15,30",name = "day") @RequestParam(value = "day") Integer day, HttpServletRequest request){
        String enterpriseId = UserDataUtil.getCustomId(request);
//        String departmentId = UserDataUtil.getDepartmentId(request);
        Map param = new HashMap();
        param.put("day",day);
        param.put("departmentId","1");
        param.put("enterpriseId",enterpriseId);
        return bossService.countUserAdd(param);
    }
    @ApiOperation(value = "指标视图",notes = "指标视图")
    @RequestMapping(value = "/company/index/kpi_view",method = RequestMethod.GET)
    public List<DataItem> kpiView(@ApiParam(value = "7,15,30",name = "day") @RequestParam(value = "day") Integer day,
                                  @ApiParam(value = "1客户 2跟进 3浏览 4转发 5保存 6点赞",name = "type") @RequestParam(value = "type") Integer type,
                                  HttpServletRequest request){
        String enterpriseId = UserDataUtil.getCustomId(request);
        Map param = new HashMap();
        param.put("type",type);
        param.put("day",day);
        param.put("enterpriseId",enterpriseId);
        return bossService.kpiView(param);
    }

    @ApiOperation(value = "企业登录选择企业", notes = "企业登录选择企业")
    @RequestMapping(value = "/company/index/chooseCompany/{phone}", method = RequestMethod.GET)
    public List<CompanyList> chooseCompany(@PathVariable(value = "phone") String phone) {
        return loginService.getCompanyList(phone);
    }

    @ApiOperation(value = "企业登录获取验证码", notes = "企业登录获取验证码")
    @RequestMapping(value = "/company/index/getMsm/{phone}", method = RequestMethod.GET)
    public String forgetPassword(@PathVariable(value = "phone") String phone) {
        String msgCode = RandomValidateCodeUtil.getMsgValidateCode();
        String[] params = {msgCode};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
        Integer template = smsConfig.getTemplate().get("user_register");
        Integer result = sendSMSService.sendSMS(phone, params, template);
        if (result == 0) {
            // 存入Redis
            UserDataUtil.setMsg(phone, msgCode);
            // 短信发送成功
            return "ok";
        } else {
            return "-1";
        }
    }
}
