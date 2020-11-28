package com.vma.push.business.controller.sys;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dao.EnterpriseMapper;
import com.vma.push.business.dao.StaffMapper;
import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.req.staff.ReqStaffPage;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.staff.RspStaff;
import com.vma.push.business.dto.rsp.store.RspWxConfig;
import com.vma.push.business.entity.Staff;
import com.vma.push.business.service.IDepartmentService;
import com.vma.push.business.service.IEnterpriseService;
import com.vma.push.business.service.IStaffInfoService;
import com.vma.push.business.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by huxaingqiang on 2018/4/18.
 */
@RestController
@RequestMapping("/v1.0")
@Api(value = "部门员工API", description = "管理后台--通讯录管理", tags = {"MaillistController"})
public class MaillistController  extends ControllerExceptionHandler {
    private Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IEnterpriseService enterpriseService;

    @Autowired
    private IStaffInfoService staffInfoService;

    @Autowired
    TlsUtil tlsUtil;

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private SmallSoftApi smallSoftApi;

    @Autowired
    private WeChatApi weChatApi;

    @Autowired
    private QiniuUtils qiniuUtils;


    /**
     * 新增员工
     * @param reqAddStaffInfo
     */
    @ApiOperation(value = "新增员工",notes = "新增员工")
    @RequestMapping(value = "/staff",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAddStaffInfo",value = "新增员工参数",required = true,dataType = "ReqAddStaffInfo")
    public RspStaffId addStaff(@RequestBody ReqAddStaffInfo reqAddStaffInfo, HttpServletRequest request) throws IOException {
        LOG.info("新增员工信息");
        reqAddStaffInfo.setStaff_id(UserDataUtil.getStaffId(request));
        reqAddStaffInfo.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
        return staffInfoService.addStaff(reqAddStaffInfo,UserDataUtil.getEnterpriseId(request), true);

    }


    @ApiOperation(value = "修改员工",notes = "修改员工")
    @RequestMapping(value = "/staff",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateStaffInfo",value = "修改增员工参数",required = true,dataType = "ReqUpdateStaffInfo")
    public void editStaff(@RequestBody ReqUpdateStaffInfo reqUpdateStaffInfo, HttpServletRequest request){
        LOG.info("修改员工信息");
        reqUpdateStaffInfo.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
        staffInfoService.updateStaff(reqUpdateStaffInfo,UserDataUtil.getEnterpriseId(request));
    }
    @ApiOperation(value = "删除员工",notes = "删除员工")
    @RequestMapping(value = "/staff",method = RequestMethod.DELETE)
    @ApiImplicitParam(name = "reqDelStaff",value = "员工ID",required = true,dataType = "ReqDelStaff")
    public void delStaff(@RequestBody ReqDelStaff reqDelStaff,HttpServletRequest request){
        LOG.info("删除员工信息");
        staffInfoService.delStaff(reqDelStaff.getId(),reqDelStaff.getWx_id(),UserDataUtil.getEnterpriseId(request));
    }


    @ApiOperation(value = "查询员工",notes = "查询员工")
    @RequestMapping(value = "/staff/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "员工ID",required = true,dataType = "String",paramType = "path")
    public RspEnStaff getStaff(@PathVariable("id") String  id, HttpServletRequest request){
        LOG.info("查询员工");
        String enterpriseid=UserDataUtil.getEnterpriseId(request);
        return staffInfoService.queryStaff(id,enterpriseid);
        //return new RspStaff(staffInfoService.selectByPrimaryKey(id));
    }

    @ApiOperation(value = "通过部门查询员工",notes = "通过部门查询员工")
    @RequestMapping(value = "/StaffByDepart/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "部门编号",required = true,dataType = "String",paramType = "path")
    public List<RspStaff> getStaffByDepart(@PathVariable("id") String  id,HttpServletRequest request){
        LOG.info("通过部门查询员工");
        String enterpriseid=UserDataUtil.getEnterpriseId(request);
        return staffInfoService.getStaffByDepart(enterpriseid,id);
    }

    /**
     * 获取所有的员工
     * @return
     */
    @ApiOperation(value = "获取所有的员工", notes = "获取所有的员工")
    @RequestMapping(value = "/staff/page", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqStaffPage",value = "获取所有的员工",required = true,dataType = "ReqStaffPage")
    public RspPage<RspStaff> staffPage(@RequestBody ReqStaffPage reqStaffPage, HttpServletRequest request){
        LOG.info("获取企业所有所有的员工");
        String enterpriseid=UserDataUtil.getEnterpriseId(request);
        return staffInfoService.getAll(reqStaffPage,enterpriseid);
    }
    @ApiOperation(value = "新增部门",notes = "新增部门")
    @RequestMapping(value = "/department",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAddDeaprtInfo",value = "新增部门",required = true,dataType = "ReqAddDepartInfo")
    public void addDepart(@RequestBody ReqAddDepartInfo reqAddDeaprtInfo,HttpServletRequest request){
        LOG.info("添加部门");
        reqAddDeaprtInfo.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
        departmentService.addDepart(reqAddDeaprtInfo,UserDataUtil.getEnterpriseId(request));

    }
    @ApiOperation(value = "修改部门",notes = "修改部门")
    @RequestMapping(value = "/department",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateDepartInfo",value = "修改部门",required = true,dataType = "ReqUpdateDepartInfo")
    public void editDepart(@RequestBody ReqUpdateDepartInfo reqUpdateDepartInfo,HttpServletRequest request){
        LOG.info("修改部门");
        String enterprise_id =UserDataUtil.getEnterpriseId(request);
        reqUpdateDepartInfo.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
        departmentService.updateDepart(reqUpdateDepartInfo,enterprise_id);
    }
    @ApiOperation(value="删除部门",notes="删除部门")
    @RequestMapping(value="/department/{id}",method = RequestMethod.DELETE)
    @ApiImplicitParam(name = "id",value = "部门id",required = true,dataType = "String",paramType="path")
    public void delDepart(@PathVariable("id") String id,HttpServletRequest request){
       String enterprise_id = UserDataUtil.getEnterpriseId(request);
        LOG.info("删除部门");
        departmentService.delDepart(id,enterprise_id);
    }

    @ApiOperation(value="查找部门",notes="查找部门")
    @RequestMapping(value="/department/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "部门id",required = true,dataType = "String",paramType="path")
    public RspDepartPage getDepart(@PathVariable("id") String id){
        LOG.info("查找部门");
        return new RspDepartPage(departmentService.selectByPrimaryKey(id));
    }

    @ApiOperation(value="注册某个用户",notes="注册某个用户")
    @RequestMapping(value="/test",method = RequestMethod.GET)
    public void test(String userid,String name,String enid){
        tlsUtil.addUser(userid,name,enid);
    }

    @ApiOperation(value="一键注册所有用户",notes="一键注册所有用户")
    @RequestMapping(value="/test2",method = RequestMethod.GET)
    public void test2(){
        List<ReqUserAndStaff> lists=staffMapper.AllUserAndStaff();
        for (ReqUserAndStaff list:lists){
            tlsUtil.addUser(list.getId(),list.getName(),list.getEnterprise_id());
        }
    }

    @ApiOperation(value="生成二维码",notes="生成二维码")
    @RequestMapping(value="/test3",method = RequestMethod.GET)
    public String  test3(String staffid,String deptid,String enid,String appid,String secret){

        try{
            return smallSoftApi.code(staffid,deptid,enid,appid,secret);
        }catch (Exception e){
            return "";
        }

    }

    @ApiOperation(value="一键将微信导出的头像转换成七牛的图片",notes="一键将微信导出的头像转换成七牛的图片")
    @RequestMapping(value="/test4",method = RequestMethod.GET)
    public void test4(){
        List<Staff> lists=staffMapper.getHttpUser();
        for (Staff list:lists){
            String newhead= qiniuUtils.newUlr(list.getHeadIcon());
            list.setHeadIcon(newhead);
            staffMapper.updateByPrimaryKeySelective(list);
        }
    }
    @ApiOperation(value="通过unacid获取小程序appid",notes="通过unacid获取小程序appid")
    @RequestMapping(value="/test5",method = RequestMethod.GET)
    public String  test5(String unacid){
        return weChatApi.getAppid(unacid);
    }
    @ApiOperation(value = "获取所有的部门", notes = "获取所有的部门")
    @RequestMapping(value = "/department/page", method = RequestMethod.POST)
    @ApiImplicitParam(name = "page",value = "分页信息",required = true,dataType = "Page")
    public RspPage<RspDepartPage> DepartmentPage(@RequestBody Page page,HttpServletRequest request){
        LOG.info("获取所有的部门");
        //PageHelper.startPage(page.getPage_num(),page.getPage_size());
        //List<RspDepartPage> repAllProduct = departmentService.getAll();
        //PageInfo pageInfo = new PageInfo(repAllProduct);
        return departmentService.getAll(page,UserDataUtil.getCustomId(request));
    }

    @ApiOperation(value="生成企业的归属关系",notes="生成企业的归属关系")
    @RequestMapping(value="/test6",method = RequestMethod.GET)
    public void  test6(String enid){
        System.out.println(new Date());
         enterpriseService.enterRela(enid);
        System.out.println(new Date());
    }
    @ApiOperation(value="生成部门的归属关系",notes="生成部门的归属关系")
    @RequestMapping(value="/test7",method = RequestMethod.GET)
    public void  test7(String enid,String dept){
        System.out.println(new Date());
        enterpriseService.deptRela(enid,dept);
        System.out.println(new Date());
    }

    @ApiOperation(value = "获取部门树", notes = "获取部门树")
    @RequestMapping(value = "/department/tree", method = RequestMethod.POST)
    public List<RspDepartTree> DepartmentPage(HttpServletRequest request){
        LOG.info("获取所有的部门");
        //PageHelper.startPage(page.getPage_num(),page.getPage_size());
        //List<RspDepartPage> repAllProduct = departmentService.getAll();
        //PageInfo pageInfo = new PageInfo(repAllProduct);
        String qiyeid=UserDataUtil.getEnterpriseId(request);
        return departmentService.departTree(qiyeid,"0");
    }

    @RequestMapping(value = "/ai_user_by_code",method = RequestMethod.POST)
    @ApiOperation(value = "获取ai雷达用户的个人信息",notes = "获取ai雷达用户的个人信息")
    public RspRadarStaffInfo aiUserByCode(String code){

        String userid="Chen";//WeChatApi.aiUserIdByCode(code);
        return new RspRadarStaffInfo(staffInfoService.selectByPrimaryKey(userid));

    }

    @RequestMapping(value = "/boss_user_by_code",method = RequestMethod.POST)
    @ApiOperation(value = "获取boss雷达用户的个人信息",notes = "获取boss雷达用户的个人信息")
    public RspRadarStaffInfo bossUserByCode(String code){

        String userid="Chen";//WeChatApi.bossUserIdByCode(code);
        return new RspRadarStaffInfo(staffInfoService.selectByPrimaryKey(userid));

    }
    /**
     * 查找部门
     * @param deptid  部门id
     * @return
     */
//    @ApiOperation(value = "查找部门", notes = "查找部门")
//    @RequestMapping(value = "/department", method = RequestMethod.GET)
//    public Department getdepart(String deptid){
//        return departmentService.selectByPrimaryKey(deptid);
//    }

    /**
     * 新增部门到数据库
     * @param dt
     * @return
     */
//    @ApiOperation(value = "新增部门到数据库", notes = "新增部门到数据库")
//    @RequestMapping(value = "/department", method = RequestMethod.POST)
//    public int adddepart(Department dt)
//    {
//        return departmentService.insert(dt);
//    }

    /**
     * 修改部门信息到数据库
     * @param dt
     * @return
     */
//    @ApiOperation(value = "修改部门到数据库", notes = "修改部门到数据库")
//    @RequestMapping(value = "/department", method = RequestMethod.PUT)
//    public int editdepart(Department dt){
//        return departmentService.updateByPrimaryKey(dt);
//    }

    /**
     * 通过主键删除部门
     * @param id
     * @return
     */
//    @ApiOperation(value = "通过主键删除部门", notes = "通过主键删除部门")
//    @RequestMapping(value = "/department", method = RequestMethod.DELETE)
//    public int deldepart(String id){
//        return departmentService.deleteByPrimaryKey(id);
//    }

    /**
     * 新增部门到微信api
     * @param dt
     * @return
     */
//    @ApiOperation(value = "新增部门到微信api", notes = "新增部门到微信api")
//    @RequestMapping(value = "/department", method = RequestMethod.POST)
//    public String adddepartApi(WechatDepartment dt){
//        return departmentService.insertApi(dt);
//    }

    /**
     * 修改部门到微信api
     * @param dt
     * @return
     */
//    @ApiOperation(value = "修改部门到微信api", notes = "修改部门到微信api")
//    @RequestMapping(value = "/department", method = RequestMethod.PUT)
//    public String editdepartApi(WechatDepartment dt){
//        return departmentService.updateApi(dt);
//    }

    /**
     * 删除微信api中的部门
     * @param corpID
     * @return
     */
//    @ApiOperation(value = "删除微信api中的部门", notes = "删除微信api中的部门")
//    @RequestMapping(value = "/department", method = RequestMethod.DELETE )
//    public String deldepartApi(String corpID){
//        return departmentService.deleteApi(corpID);
//    }



    /**
     * 通过主键获取员工
     * @param staffid
     * @return
     */
//    @ApiOperation(value = "查找员工", notes = "查找员工")
//    @RequestMapping(value = "/staff", method = RequestMethod.GET)
//    public staff getstaff(String staffid){
//s
//        return staffInfoService.selectByPrimaryKey(staffid);
//    }

//    /**
//     * 新增员工到数据库
//     * @param staffInfo
//     * @return
//     */
//    @ApiOperation(value = "新增员工到数据库", notes = "新增员工到数据库")
//    @RequestMapping(value = "/staff", method = RequestMethod.POST)
//    public int addstaff(staff staffInfo){
//
//        return staffInfoService.insert(staffInfo);
//    }






    /**
     * 修改员工到数据库
     * @param staffInfo
     * @return
     */
//    @ApiOperation(value = "修改员工到数据库", notes = "修改员工到数据库")
//    @RequestMapping(value = "/staff", method = RequestMethod.PUT)
//    public int editstaff(staff staffInfo){
//        return staffInfoService.updateByPrimaryKey(staffInfo);
//    }

    /**
     * 删除数据裤的员工
     * @param id
     * @return
     */
//    @ApiOperation(value = "删除数据裤的员工", notes = "删除数据裤的员工")
//    @RequestMapping(value = "/staff", method = RequestMethod.DELETE)
//    public int delstaff(String id){
//        return staffInfoService.deleteByPrimaryKey(id);
//    }

    /**
     * 新增员工到微信api
     * @param employee
     * @return
     */
//    @ApiOperation(value = "新增员工到微信api", notes = "新增员工到微信api")
//    @RequestMapping(value = "/staff", method = RequestMethod.POST)
//    public String addstaffapi(Employee employee){
//        return staffInfoService.insertApi(employee);
//    }

    /**
     * 修改员工信息到微信api
     * @param employee
     * @return
     */
//    @ApiOperation(value = "修改员工信息到微信api", notes = "修改员工信息到微信api")
//    @RequestMapping(value = "/staff", method = RequestMethod.PUT)
//    public String editstaffapi(Employee employee){
//        return staffInfoService.updateApi(employee);
//    }

    /**
     * 删除微信api中的员工数据
     * @param corpID
     * @return
     */
//    @ApiOperation(value = "删除微信api中的员工数据", notes = "删除微信api中的员工数据")
//    @RequestMapping(value = "/staff", method = RequestMethod.DELETE)
//    public String delstaffapi(String corpID){
//        return staffInfoService.deleteApi(corpID);
//    }


    /*
    *//**
     * 增加部门
     * @param dept  部门对象
     * @return
     *//*
    @ApiOperation(value = "增加部门", notes = "增加部门")
    @RequestMapping(value = "/department", method = RequestMethod.POST)
    public String addDepartment(WechatDepartment dept){
        String result="";
        String token=getToken();
        String url="https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token="+token;
        Map param = transBean2Map(dept);
        result=HttpUtil.httpPostReq(url,param).getResponse_str();
        return result;
    }

    *//**修改部门
     * @param dept  部门对象
     * @return
     *//*
    @ApiOperation(value = "修改部门", notes = "修改部门")
    @RequestMapping(value = "/department", method = RequestMethod.PUT)
    public String aditDepartment(WechatDepartment dept){
        String result="";
        String token=getToken();
        String url="https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token="+token;
        Map param = transBean2Map(dept);
        result=HttpUtil.httpPostReq(url,param).getResponse_str();
        return result;
    }

    *//**删除部门
     * @param deptid  部门id
     * @return
     *//*
    @ApiOperation(value = "删除部门", notes = "删除部门")
    @RequestMapping(value = "/department", method = RequestMethod.DELETE)
    public String delDepartment(@ApiParam("部门编号") @RequestParam("deptid") String  deptid){
        String result="";
        String token=getToken();
        String url="https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token="+token+"&id="+deptid;
        result=HttpUtil.httpGetReq(url).getResponse_str();
        return result;
    }

    *//**查找部门
     * @param deptid  部门id
     * @return
     *//*
    @ApiOperation(value = "查找部门", notes = "查找部门")
    @RequestMapping(value = "/department", method = RequestMethod.GET)
    public String getDepartment(@ApiParam("部门编号") @RequestParam("deptid") String  deptid){
        String result="";
        String token=getToken();
        String url="https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token="+token+"&id="+deptid;
        result=HttpUtil.httpGetReq(url).getResponse_str();
        return result;
    }

    *//**
     * 增加员工
     * @param employee 员工对象
     * @return
     *//*
    @ApiOperation(value = "增加员工", notes = "增加员工")
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public String addEmployee(Employee employee){
        String result="";
        String token=getToken();
        String url="https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token="+token;
        Map param = transBean2Map(employee);
        result=HttpUtil.httpPostReq(url,param).getResponse_str();
        return result;
    }

    *//**
     * 修改员工
     * @param employee 员工对象
     * @return
     *//*
    @ApiOperation(value = "修改员工", notes = "修改员工")
    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    public String editEmployee(Employee employee){
        String result="";
        String token=getToken();
        String url="https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token="+token;
        Map param = transBean2Map(employee);
        result=HttpUtil.httpPostReq(url,param).getResponse_str();
        return result;
    }

    *//**
     * 批量删除员工
     * @param employeeid 员工id数组
     * @return
     *//*
    @ApiOperation(value = "删除员工", notes = "删除员工")
    @RequestMapping(value = "/employee", method = RequestMethod.DELETE)
    public String delEmployee(@ApiParam("员工ID") @RequestParam("employeeid") String employeeid){
        String result="";
        String token=getToken();
        System.out.print(token);
        String url="https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token="+token+"&userid="+employeeid;
        result=HttpUtil.httpGetReq(url).getResponse_str();
        return result;
    }

    *//**
     * 查找员工
     * @param employeeid 员工id
     * @return
     *//*
    @ApiOperation(value = "查找员工", notes = "查找员工")
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String getEmployee(@ApiParam("员工ID") @RequestParam("employeeid") String employeeid){
        String result="";
        String token=getToken();
        String url="https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token="+token+"&userid="+employeeid;
        result=HttpUtil.httpGetReq(url).getResponse_str();
        return result;
    }

    *//**
     * 对象转map
     * @param obj
     * @return
     *//*
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            //LOGGER.error("transBean2Map Error {}" ,e);
        }
        return map;

    }*/

}
