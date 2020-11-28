package com.vma.push.business.dto.rsp.staff;

import com.vma.push.business.dto.rsp.store.RspRecOffer;
import com.vma.push.business.entity.StaffIntro;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by chenzui on 2018/5/8.
 */
@Data
public class RspCardInfo {
    @ApiModelProperty(value = "人员ID")
    String staffId;
    @ApiModelProperty(value = "用户ID")
    String userId;
    @ApiModelProperty(value = "销售人员姓名")
    String name;
    @ApiModelProperty(value = "岗位")
    String station;
    @ApiModelProperty(value = "部门名称")
    String department_name;
    @ApiModelProperty(value = "查看名片数")
    Integer view_num;
    @ApiModelProperty(value = "点赞数量")
    Integer zan_num;
    @ApiModelProperty(value = "转发数量")
    Integer zhuan_num;
    @ApiModelProperty(value = "是否点赞1是0否")
    Integer is_zan;
    @ApiModelProperty(value = "手机号")
    String mobile;
    @ApiModelProperty(value = "邮箱")
    String mail;
    @ApiModelProperty(value = "微信号")
    String weixin;
    @ApiModelProperty(value = "固定电话")
    String phone;
    @ApiModelProperty(value = "地址")
    String address;
    @ApiModelProperty(value = "头像")
    String head_icon;
    @ApiModelProperty(value = "签名")
    private String signature;
    @ApiModelProperty(value = "音频")
    private String signature_vedio;
    @ApiModelProperty(value = "录音")
    private String signature_audio;
    @ApiModelProperty(value = "图片列表")
    List<StaffIntro> staffIntros;
    @ApiModelProperty(value = "企业ID")
    private String enterprise_name;
    @ApiModelProperty(value = "名片模板ID")
    Integer temlate_id;
    @ApiModelProperty(value = "推荐商品")
    List<RspRecOffer> rec_offer;
    @ApiModelProperty(value = "wxId")
    String wx_id;
    @ApiModelProperty(value = "QRCode")
    String soft_img_url;
    @ApiModelProperty(value = "名片分享编辑文本")
    String share_setup;
    @ApiModelProperty(value = "折扣")
    private BigDecimal discount;
    @ApiModelProperty(value = "浏览人员头像")
    List<String> user_icons;
    @ApiModelProperty(value = "企业logo")
    private String icon;
    @ApiModelProperty(value = "用户角色")
    private Integer role;
    @ApiModelProperty(value = "登陆类型")
    private Integer loginType;
    @ApiModelProperty(value="当前企业id")
    private String enterprise_id;
    @ApiModelProperty(value="部门id")
    private String department_id;
    @ApiModelProperty(value="是否自己的名片")
    private Integer isMyself;
    @ApiModelProperty(value="分享名片的背景图")
    private String card_img_url;
}
