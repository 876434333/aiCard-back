package com.vma.push.business.dto.req;

import com.vma.push.business.entity.UserInfo;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by huxiangqiang on 2018/5/4.
 */
@Data
public class ReqAdduser {
    /*private String id;*/

    @ApiModelProperty(value = "微信open_id",required = true)
    private String open_id;

    @ApiModelProperty(value = "企业id",required = false)
    private String enterprise_id;

    @ApiModelProperty(value = "销售人员id",required = true)
    private String employee_id;

    @ApiModelProperty(value = "来源1扫码2分享")
    private Integer froms;

   /* @ApiModelProperty(value = "小程序id",required = true)
    private String wx_soft_id;*/

    @ApiModelProperty(value = "头像",required = true)
    private String head_icon;

    @ApiModelProperty(value = "昵称",required = true)
    private String nick_name;

    @ApiModelProperty(value = "性别",required = true)
    private Integer sex;

    @ApiModelProperty(value = "若是转发的。传转发用户的openId")
    private String from_open_id;

    @ApiModelProperty(value = "部门编号",hidden = true)
    private String departmentId;

    public UserInfo touserinfo(){
        UserInfo userInfo=new UserInfo();
        userInfo.setCreateTime(new Date());
        userInfo.setModifyTime(new Date());
        userInfo.setId(UuidUtil.getRandomUuid());
        userInfo.setNickName(this.getNick_name());
        userInfo.setHeadIcon(this.getHead_icon());
        userInfo.setSex(this.getSex());
        //userInfo.setWxSoftId(this.getWx_soft_id());
        userInfo.setOpenId(this.getOpen_id());
        return userInfo;
    }
}
