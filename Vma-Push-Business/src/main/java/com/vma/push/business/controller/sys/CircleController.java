package com.vma.push.business.controller.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.common.exception.BusinessException;
import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.rsp.RspAllCircle;
import com.vma.push.business.dto.rsp.RspCircleDetail;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.CircleCommentLog;
import com.vma.push.business.entity.CircleCompany;
import com.vma.push.business.entity.CircleZanLog;
import com.vma.push.business.service.ICircleCommentLogService;
import com.vma.push.business.service.ICircleCompanyService;
import com.vma.push.business.service.ICircleZanLogService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by huxiangqiang on 2018/4/25.
 */
@RestController
@RequestMapping("/v1.0")
@Api(value = "企业朋友圈", description = "管理后台--企业朋友圈", tags = {"CircleController"})
public class CircleController extends ControllerExceptionHandler{

    private Logger LOG = Logger.getLogger(this.getClass());
    @Autowired
    private ICircleCompanyService circleCompanyService;

    @Autowired
    private ICircleCommentLogService circleCommentLogService;

    @Autowired
    private ICircleZanLogService circleZanLogService;

    @ApiOperation(value = "发布公司朋友圈",notes = "发布公司朋友圈")
    @RequestMapping(value = "/Circle",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAddCircle",value = "发布公司朋友圈 需要传session_id",required = true,dataType = "ReqAddCircle")

    public void sendCircle(@RequestBody ReqAddCircle reqAddCircle, HttpServletRequest request){
        /*CircleCompany circleCompany=new CircleCompany(reqAddCircle);
        circleCompanyService.insertSelective(circleCompany);*/
        LOG.info("发布公司朋友圈");
        reqAddCircle.setEmployee_id(UserDataUtil.getEnterpriseId(request));//UserDataUtil.getStaffId(request);
        reqAddCircle.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
        circleCompanyService.sendCircle(reqAddCircle,1);
    }



    @ApiOperation(value = "编辑公司朋友圈",notes = "编辑公司朋友圈")
    @RequestMapping(value = "/Circle",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateCircle",value = "编辑公司朋友圈",required = true,dataType = "ReqUpdateCircle")

    //不区分企业跟个人
    public void editCircle(@RequestBody ReqUpdateCircle reqUpdateCircle){
        LOG.info("编辑朋友圈");
        circleCompanyService.editCircle(reqUpdateCircle);
    }


    @ApiOperation(value = "删除朋友圈",notes = "删除朋友圈")
    @RequestMapping(value = "/Circle/{id}",method = RequestMethod.DELETE)
    @ApiImplicitParam(name = "id",value = "朋友圈id",required = true,dataType = "String",paramType = "path")
    //不区分企业跟个人
    public void delCircle(@PathVariable("id")  String id){
        LOG.info("删除朋友圈");
        circleCompanyService.delCircle(id);
    }

    @ApiOperation(value = "公司评论朋友圈",notes = "公司评论朋友圈")
    @RequestMapping(value = "/Comment",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAddComment",value = "公司评论朋友圈 需要传session_id",required = true,dataType = "ReqAddComment")
    public void commentCircle(@RequestBody ReqAddComment reqAddComment,HttpServletRequest request){
        LOG.info("评论朋友圈");
        CircleCommentLog circleCommentLog=reqAddComment.toCircleCommentLog();
        circleCommentLog.setFlag(1);
        circleCommentLog.setUserId(UserDataUtil.getEnterpriseId(request));
        circleCommentLogService.circleComment(circleCommentLog);
    }


    @ApiOperation(value = "删除评论",notes = "删除评论")
    @RequestMapping(value = "/Comment/{id}",method = RequestMethod.DELETE)
    @ApiImplicitParam(name = "id",value = "评论记录id",required = true,dataType = "String",paramType = "path")
    //不去分企业跟个人
    public void delComment(@PathVariable("id")  String id){
        LOG.info("删除评论");
        circleCommentLogService.delComment(id);
    }


    @ApiOperation(value = "企业 点赞或取消赞",notes = "点赞或取消赞")
    @RequestMapping(value = "/Zan",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqZan",value = "点赞或取消赞 需要传session_id",required = true,dataType = "ReqZan")
    public void zan(@RequestBody  ReqZan reqZan,HttpServletRequest request){
        LOG.info("点赞或取消赞");
        CircleZanLog circleZanLog=reqZan.toCircleZanLog();//new CircleZanLog(reqZan);
        circleZanLog.setFlag(1);//1公司点赞 0个人点赞
        circleZanLog.setUserId(UserDataUtil.getEnterpriseId(request));
        circleZanLogService.zan(circleZanLog);
    }


    /**
     * 获取所有朋友圈
     * @return
     */


    @ApiOperation(value = "查找朋友圈", notes = "查找朋友圈")
    @RequestMapping(value = "/Circle/page", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqSelectCircle",value = "查询条件 需要传session",required = true,dataType = "ReqSelectCircle")
    public RspPage<RspAllCircle> CirclePage(@RequestBody ReqSelectCircle reqSelectCircle,HttpServletRequest request){
        LOG.info("获取所有朋友圈");
        reqSelectCircle.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
        return circleCompanyService.circlePage(reqSelectCircle);
        //return circleCompanyService.getAll(page.getPage_num(),page.getPage_size());

    }
/*    @ApiOperation(value = "通过获取员工的所有朋友圈", notes = "获取所有朋友圈")
    @RequestMapping(value = "/Circle/page/{id}", method = RequestMethod.POST)
    @ApiImplicitParam(name = "id",value = "朋友圈id",required = true,dataType = "String",paramType = "path")
    public RspPage<RspCircleDetail> CirclePageByStaffId(@RequestBody Page page,@PathVariable("id") String id){
        LOG.info("通过获取员工的所有朋友圈");
        return circleCompanyService.getAllByStaffId(page.getPage_num(),page.getPage_size(),id);

    }*/
    @ApiOperation(value = "获取朋友圈详情",notes = "获取朋友圈详情")
    @RequestMapping(value = "/Circle/Detail/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "朋友圈id",required = true,dataType = "String",paramType = "path")
    public RspCircleDetail circleDetail(@PathVariable("id")  String id){
        LOG.info("获取朋友圈详情");
        return circleCompanyService.circleDetail(id);
    }
}
