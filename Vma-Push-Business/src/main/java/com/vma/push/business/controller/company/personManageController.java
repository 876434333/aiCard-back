package com.vma.push.business.controller.company;

import com.vma.push.business.common.ErrCode;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.req.staff.ReqCustomCount;
import com.vma.push.business.dto.req.staff.ReqStaffPage;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.radar.EnterpriseRadarInfo;
import com.vma.push.business.dto.rsp.staff.RspCustomCount;
import com.vma.push.business.dto.rsp.staff.RspStaff;
import com.vma.push.business.entity.Enterprise;
import com.vma.push.business.entity.Staff;
import com.vma.push.business.service.IBasicService;
import com.vma.push.business.service.IDepartmentService;
import com.vma.push.business.service.IStaffInfoService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/7/13.
 */
@RestController(value = "personManageController")
@RequestMapping("/V1.0")
@Api(value = "企业后台", description = "人员管理", tags = {"personManageController"})
public class personManageController {
    private Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IStaffInfoService staffInfoService;
    @Autowired
    private IBasicService basicService;


    @ApiOperation(value = "获取部门树", notes = "获取部门树")
    @RequestMapping(value = "/company/person/department/tree", method = RequestMethod.POST)
    public List<RspDepartTreeNew> DepartmentPage(HttpServletRequest request){
        LOG.info("获取所有的部门");
        String enterpriseId= UserDataUtil.getCustomId(request);
        return departmentService.departTreeNew(enterpriseId,"0");
    }

    /**
     * 获取所有的员工
     * @return
     */
    @ApiOperation(value = "获取所有的员工", notes = "获取所有的员工")
    @RequestMapping(value = "/company/person/staff/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqStaffPage",value = "获取所有的员工",required = true,dataType = "ReqStaffPage")
    public RspPage<RspStaff> staffPage(@RequestBody ReqStaffPage reqStaffPage, HttpServletRequest request){
        LOG.info("获取企业所有所有的员工");
        String enterpriseid=UserDataUtil.getCustomId(request);
        return staffInfoService.getAll(reqStaffPage,enterpriseid);
    }

    /**
     * 新增员工
     * @param reqAddStaffInfo
     */
    @ApiOperation(value = "新增员工",notes = "新增员工")
    @RequestMapping(value = "/company/person/staff",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAddStaffInfo",value = "新增员工参数",required = true,dataType = "ReqAddStaffInfo")
    public RspStaffId addStaff(@RequestBody ReqAddStaffInfo reqAddStaffInfo, HttpServletRequest request) throws IOException {
        LOG.info("新增员工信息");
        reqAddStaffInfo.setStaff_id(UserDataUtil.getStaffId(request));
        reqAddStaffInfo.setEnterprise_id(UserDataUtil.getCustomId(request));
        return staffInfoService.addStaff(reqAddStaffInfo,UserDataUtil.getCustomId(request), true);

    }

    @ApiOperation(value = "通过部门查询员工",notes = "通过部门查询员工")
    @RequestMapping(value = "/company/StaffByDepart/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "部门编号",required = true,dataType = "String",paramType = "path")
    public List<RspStaff> getStaffByDepart(@PathVariable("id") String  id,HttpServletRequest request){
        LOG.info("通过部门查询员工");
        String enterpriseid=UserDataUtil.getCustomId(request);
        return staffInfoService.getStaffByDepart(enterpriseid,id);
    }

    @ApiOperation(value = "获取所有的部门", notes = "获取所有的部门")
    @RequestMapping(value = "/company/department/page", method = RequestMethod.POST)
    @ApiImplicitParam(name = "page",value = "分页信息",required = true,dataType = "Page")
    public RspPage<RspDepartPage> DepartmentPage(@RequestBody Page page,HttpServletRequest request){
        LOG.info("获取所有的部门");
        //PageHelper.startPage(page.getPage_num(),page.getPage_size());
        //List<RspDepartPage> repAllProduct = departmentService.getAll();
        //PageInfo pageInfo = new PageInfo(repAllProduct);
        page.setPage_num(1);
        page.setPage_size(100);
        return departmentService.getAll(page,UserDataUtil.getCustomId(request));
    }

    @ApiOperation(value = "查询员工",notes = "查询员工")
    @RequestMapping(value = "/company/person/staff/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "员工ID",required = true,dataType = "String",paramType = "path")
    public RspEnStaff getStaff(@PathVariable("id") String  id, HttpServletRequest request){
        LOG.info("查询员工");
        String enterpriseid=UserDataUtil.getCustomId(request);
        return staffInfoService.queryStaff(id,enterpriseid);
        //return new RspStaff(staffInfoService.selectByPrimaryKey(id));
    }
    @ApiOperation(value = "查询员工权限",notes = "查询员工权限")
    @RequestMapping(value = "/company/person/staff_radar/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "员工ID",required = true,dataType = "String",paramType = "path")
    public void staffRadar(@PathVariable("id") String  id, HttpServletRequest request){
        LOG.info("查询员工");
        String enterpriseid=UserDataUtil.getEnterpriseId(request);
        Integer isRadar=staffInfoService.staffRadar(id,enterpriseid);
        if (isRadar>0){
            throw new BusinessException(1000,"交接工作前请先关闭雷达权限！");
        }
        //return new RspStaff(staffInfoService.selectByPrimaryKey(id));
    }

    @ApiOperation(value = "修改员工",notes = "修改员工")
    @RequestMapping(value = "/company/person/staff",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateStaffInfo",value = "修改增员工参数",required = true,dataType = "ReqUpdateStaffInfo")
    public void editStaff(@RequestBody ReqUpdateStaffInfo reqUpdateStaffInfo, HttpServletRequest request){
        LOG.info("修改员工信息");
        reqUpdateStaffInfo.setEnterprise_id(UserDataUtil.getCustomId(request));
        staffInfoService.updateStaff(reqUpdateStaffInfo,UserDataUtil.getCustomId(request));
    }

    @ApiOperation(value = "删除员工",notes = "删除员工")
    @RequestMapping(value = "/company/person/staff",method = RequestMethod.DELETE)
    @ApiImplicitParam(name = "reqDelStaff",value = "员工ID",required = true,dataType = "ReqDelStaff")
    public void delStaff(@RequestBody ReqDelStaff reqDelStaff, HttpServletRequest request){
        LOG.info("删除员工信息");
        staffInfoService.delStaff(reqDelStaff.getId(),reqDelStaff.getWx_id(),UserDataUtil.getCustomId(request));
    }

    @ApiOperation(value = "新增部门",notes = "新增部门")
    @RequestMapping(value = "/company/person/department",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAddDeaprtInfo",value = "新增部门",required = true,dataType = "ReqAddDepartInfo")
    public void addDepart(@RequestBody ReqAddDepartInfo reqAddDeaprtInfo, HttpServletRequest request){
        LOG.info("添加部门");
        reqAddDeaprtInfo.setEnterprise_id(UserDataUtil.getCustomId(request));
        departmentService.addDepart(reqAddDeaprtInfo,UserDataUtil.getCustomId(request));

    }

    @ApiOperation(value = "修改部门",notes = "修改部门")
    @RequestMapping(value = "/company/person/department",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateDepartInfo",value = "修改部门",required = true,dataType = "ReqUpdateDepartInfo")
    public void editDepart(@RequestBody ReqUpdateDepartInfo reqUpdateDepartInfo, HttpServletRequest request){
        LOG.info("修改部门");
        String enterprise_id =UserDataUtil.getCustomId(request);
        reqUpdateDepartInfo.setEnterprise_id(UserDataUtil.getCustomId(request));
        departmentService.updateDepart(reqUpdateDepartInfo,enterprise_id);
    }

    @ApiOperation(value="删除部门",notes="删除部门")
    @RequestMapping(value="/company/person/department/{id}",method = RequestMethod.DELETE)
    @ApiImplicitParam(name = "id",value = "部门id",required = true,dataType = "String",paramType="path")
    public void delDepart(@PathVariable("id") String id,HttpServletRequest request){
        String enterprise_id = UserDataUtil.getCustomId(request);
        LOG.info("删除部门");
        departmentService.delDepart(id,enterprise_id);
    }
    @ApiOperation(value = "部门名称",notes ="部门名称" )
    @RequestMapping(value = "/company/person/departname/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "部门id",required = true,dataType = "String",paramType="path")
    public String departname(@PathVariable("id") String id, HttpServletRequest request){
        String enterpriseid=UserDataUtil.getCustomId(request);
        return staffInfoService.departname(enterpriseid,id);
    }

    @ApiOperation(value = "工作交接 需要session_id",notes ="工作交接" )
    @RequestMapping(value = "/company/person/handover",method = RequestMethod.POST)
    public void handover(@RequestBody ReqHandover reqHandover, HttpServletRequest request){
        String enterpriseId=UserDataUtil.getCustomId(request);
        List<RspHandover> result=staffInfoService.handover(reqHandover.getFromid(),reqHandover.getToid(),enterpriseId);
        //return result;
    }

    @ApiOperation(value = "工作交接 客户数",notes ="工作交接 客户数" )
    @RequestMapping(value = "/company/person/handover/custom_count",method = RequestMethod.POST)
    public RspCustomCount handoverCustom(@RequestBody ReqCustomCount reqCustomCount, HttpServletRequest request){
        return staffInfoService.customCount(reqCustomCount);

    }
    /**
     * 权限列表条件查询
     *
     * @return
     */
    @ApiOperation(value = "条件查询雷达列表", notes = "条件查询雷达列表")
    @RequestMapping(value = "/company/person/radar/select", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqStaffSelect", value = "查看产品列表", dataType = "ReqStaffSelect")
    public RspRadarPage<RspStaff> findStaffSelect(@RequestBody ReqStaffSelect reqStaffSelect, HttpServletRequest request){
        String enterpriseId = UserDataUtil.getCustomId(request);
        reqStaffSelect.setEnterprise_id(enterpriseId);
        return staffInfoService.findStaffSelect(reqStaffSelect);

    }

    @ApiOperation(value = "修改雷达状态",notes = "修改雷达状态")
    @RequestMapping(value = "/company/person/radar/update",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateRadar",required = true,value = "修改雷达状态",dataType = "ReqUpdateRadar")
    public void updateStatus(@RequestBody ReqUpdateRadar reqUpdateRadar,HttpServletRequest request) {
        LOG.info("修改雷达状态");
        String staffId = reqUpdateRadar.getId();
        String enterpriseId = UserDataUtil.getCustomId(request);
        Staff staff = staffInfoService.selectByPrimaryKey(staffId);
        if (!enterpriseId.equals(staff.getEnterpriseId())) {
            throw new BusinessException(ErrCode.ILLEGAL_REQUEST, "非法请求");
        }
        staff.setOpenAi(reqUpdateRadar.getOpen_ai());
        staff.setOpenBoss(reqUpdateRadar.getOpen_boss());
        staffInfoService.updateByPrimaryKeySelective(staff);
        basicService.updateCardUsedInfo(enterpriseId, staff);

    }

    @ApiOperation(value = "刷新二维码",notes = "刷新二维码")
    @RequestMapping(value = "/company/person/radar/refresh",method = RequestMethod.PUT)
    public void refreshRadar(HttpServletRequest request) {
        LOG.info("刷新二维码");
        String enterpriseId = UserDataUtil.getCustomId(request);
        //String enterpriseId = "56c369ec-0c96-40a8-8d9e-a69727f19180";
        staffInfoService.getRefreshRader(enterpriseId);

    }


    @ApiModelProperty(value = "获取企业雷达使用情况")
    @RequestMapping(value = "/company/person/radar/info",method = RequestMethod.GET)
    public EnterpriseRadarInfo getRadarInfo(HttpServletRequest request){
        String enterpriseId = UserDataUtil.getCustomId(request);
        Enterprise enterprise =  basicService.selectByPrimaryKey(enterpriseId);
        EnterpriseRadarInfo info =  new EnterpriseRadarInfo();
        info.setTotal(enterprise.getMoneySum());
        info.setUsed(enterprise.getMoneySum()-enterprise.getMoneyLeave());//总绩点数-剩余绩点数==已用绩点数
        return info;
    }
    }
