package com.vma.push.business.controller.sale;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dto.req.label.ReqAddLabel;
import com.vma.push.business.dto.rsp.userInfo.RspLabelType;
import com.vma.push.business.entity.LabelInfo;
import com.vma.push.business.entity.LabelType;
import com.vma.push.business.service.ILabelInfoService;
import com.vma.push.business.service.ILabelTypeService;
import com.vma.push.business.service.IUserInfoService;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by chenzui on 2018/5/14.
 */
@RestController
@RequestMapping("/v3.0")
@Api(value = "销售端--用户标签相关接口", description = "销售端--用户标签相关接口", tags = {"LabelTypeController"})
public class LabelTypeController  extends ControllerExceptionHandler {

    @Autowired
    private ILabelTypeService labelTypeService;

    @Autowired
    private ILabelInfoService labelInfoService;

    @Autowired
    private IUserInfoService userInfoService;

    @ApiOperation(value = "添加标签类别",notes = "添加标签类别")
    @RequestMapping(value = "/label_type",method = RequestMethod.PUT)
    public void addType(@ApiParam(value = "标签类别名称",name = "name") @RequestParam(value = "name") String name){

        LabelType labelType = new LabelType();
        labelType.setId(UuidUtil.getRandomUuid());
        labelType.setTypeName(name);
        labelType.setCreateTime(new Date());
        labelType.setModifyTime(new Date());
        labelTypeService.insertSelective(labelType);
    }

    @ApiOperation(value = "添加标签",notes = "添加标签")
    @RequestMapping(value = "/label",method = RequestMethod.POST)
    @ApiImplicitParam(value = "参数",name = "reqAddLabel",dataType = "ReqAddLabel")
    public void addLabel(@RequestBody ReqAddLabel reqAddLabel){

        LabelInfo labelInfo = new LabelInfo();
        labelInfo.setModifyTime(new Date());
        labelInfo.setCreateTime(new Date());
        labelInfo.setLabelName(reqAddLabel.getLabel_name());
        labelInfo.setTypeId(reqAddLabel.getType_id());
        labelInfo.setId(UuidUtil.getRandomUuid());
        labelInfoService.insertSelective(labelInfo);

    }

    @ApiOperation(value = "查询用户标签信息",notes = "查询用户标签信息")
    @RequestMapping(value = "/user/label/{user_id}",method = RequestMethod.GET)
    public List<RspLabelType> queryUserLabel(
            @ApiParam(value = "user_id") @PathVariable(value = "user_id") String userId){

        return userInfoService.queryUserLabelInfo(userId);

    }

}
