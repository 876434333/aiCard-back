package com.vma.push.business.controller.sale;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.config.PlatformEnterprise;
import com.vma.push.business.dao.OfferRecommendMapper;
import com.vma.push.business.dto.RspProdPage;
import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.req.userInfo.ReqLog;
import com.vma.push.business.dto.req.userInfo.ReqQueryUserInfo;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.actionLog.UserActionLog;
import com.vma.push.business.dto.rsp.ai.CompanyList;
import com.vma.push.business.dto.rsp.ai.repChoseCompany;
import com.vma.push.business.dto.rsp.boss.RspViewData;
import com.vma.push.business.dto.rsp.staff.RspStaffOfferIntro;
import com.vma.push.business.dto.rsp.store.RspWxConfig;
import com.vma.push.business.dto.rsp.userInfo.RspAnalysisData;
import com.vma.push.business.dto.rsp.userInfo.RspUserDetail4Sale;
import com.vma.push.business.dto.rsp.userInfo.RspUserInfoList;
import com.vma.push.business.entity.*;
import com.vma.push.business.dto.rsp.userInfo.RspUserSimpleInfo;
import com.vma.push.business.service.*;
import com.vma.push.business.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenzui on 2018/5/11.
 */
@RestController
@RequestMapping("/v3.0")
@Api(value = "销售端用户接口", description = "销售端--销售端用户接口", tags = {"UserInfoController"})
public class UserInfoController extends ControllerExceptionHandler {
	private Logger LOG = Logger.getLogger(this.getClass());

	@Autowired
	private IUserInfoService userInfoService;

	@Autowired
	private OfferRecommendMapper offerRecommendMapper;

	@Autowired
	private WeChatApi weChatApi;

	@Autowired
	private IStaffInfoService staffInfoService;

	@Autowired
	IProductService productService;

	@Autowired
	private ICircleCompanyService circleCompanyService;

	@Autowired
	private IOrderService orderService;

	@Autowired
	private IUserLabelInfoService userLabelInfoService;

	@Autowired
	private ICircleZanLogService circleZanLogService;

	@Autowired
	private ICircleCommentLogService circleCommentLogService;

	@Autowired
	private IBossService bossService;

	@Autowired
	private IDepartmentService departmentService;
	@Autowired
	private TlsUtil tlsUtil;

	@Autowired
	private IEnterpriseService enterpriseService;


	@ApiOperation(value = "查询销售人员的对应的客户列表", notes = "查询销售人员的对应的客户列表")
	@RequestMapping(value = "/user/list", method = RequestMethod.POST)
	@ApiImplicitParam(value = "reqQueryUserInfo", required = true, name = "reqQueryUserInfo", dataType = "ReqQueryUserInfo")
	public RspPage<RspUserInfoList> queryUsers(@RequestBody ReqQueryUserInfo reqQueryUserInfo, HttpServletRequest request) {
		LOG.info("查询销售人员的对应的客户列表");
		reqQueryUserInfo.setStaff_id(UserDataUtil.getStaffId(request));
		return userInfoService.queryUsers(reqQueryUserInfo);
	}


	@ApiOperation(value = "销售人员简要名片 我的", notes = "获取销售人员名片详情")
	@RequestMapping(value = "/card", method = RequestMethod.GET)
	public RspCard cardInfo(HttpServletRequest request) {
		LOG.info("销售人员简要名片 我的");
		String staffid = UserDataUtil.getStaffId(request);
		String enid = UserDataUtil.getEnterpriseId(request);
		return staffInfoService.cardInfo(staffid, enid);
	}

	@ApiOperation(value = "销售人员名片详情 我的", notes = "销售人员名片详情")
	@RequestMapping(value = "/cardDetail", method = RequestMethod.GET)
	public editCard cardDetail(HttpServletRequest request) {
		LOG.info("获取销售人员名片详情");
		String staffid = UserDataUtil.getStaffId(request);
		String enid = UserDataUtil.getEnterpriseId(request);
		editCard card = staffInfoService.getCard(staffid, enid);
		return card;
	}


	@ApiOperation(value = "修改名片 我的", notes = "修改名片")
	@RequestMapping(value = "/editCard", method = RequestMethod.PUT)
	@ApiImplicitParam(name = "editCard", value = "名片信息", required = true, dataType = "editCard")
	public void editCard(@RequestBody editCard editCard, HttpServletRequest request) {
		LOG.info("修改名片");
		//Staff staff=editCard.toStaff();
		//staff.setEnterpriseId(UserDataUtil.getEnterpriseId(request));
		staffInfoService.editCard(editCard, request);

	}

	@ApiOperation(value = "获取用户照片", notes = "获取用户照片")
	@RequestMapping(value = "/userPhoto", method = RequestMethod.GET)
	public List<RspStaffIntro> userIntro(HttpServletRequest request) {
		LOG.info("获取用户照片");
		String staffid = UserDataUtil.getStaffId(request);
		String enid = UserDataUtil.getEnterpriseId(request);
		return staffInfoService.getintro(staffid, enid);

	}

	@ApiOperation(value = "个人推荐商品列表", notes = "个人推荐商品列表")
	@RequestMapping(value = "/staff/offer/list/{staffId}/{entId}", method = RequestMethod.POST)
	@ApiImplicitParam(name = "page", value = "分页信息", required = true, dataType = "Page")
	public RspPage<RspStaffOfferIntro> staffOffer(@RequestBody Page page, HttpServletRequest request,
												  @PathVariable String staffId, @PathVariable String entId) {
		LOG.info("个人推荐商品列表");
		String staffid = staffId;
		String enid = entId;
		return staffInfoService.staffOfferIntro(page, staffid, enid);
	}

	@ApiOperation(value = "添加推荐商品", notes = "添加推荐商品")
	@RequestMapping(value = "/staff/offer/{id}", method = RequestMethod.POST)
	@ApiImplicitParam(name = "id", value = "商品id", required = true, dataType = "String", paramType = "path")
	public String addOfferIntro(@PathVariable String id, HttpServletRequest request) {
		LOG.info("个人推荐商品列表");
		OfferRecommend offerRecommend = new OfferRecommend();
		offerRecommend.setId(UuidUtil.getRandomUuid());//id
		offerRecommend.setCreateTime(new Date());//时间
		offerRecommend.setEnterpriseId(UserDataUtil.getEnterpriseId(request));//企业
		offerRecommend.setStaffId(UserDataUtil.getStaffId(request));//员工
		offerRecommend.setOfferId(id);//商品id
		offerRecommend.setStatus(1);//状态
		//判断推荐商品是否已存在避免重复添加问题
		OfferRecommend exist = offerRecommendMapper.checkOfferRecommendExist(offerRecommend);
		if (exist == null) {
			offerRecommendMapper.insertSelective(offerRecommend);
			return offerRecommend.getId();
		} else {
			return exist.getId();
		}
	}

	@ApiOperation(value = "取消推荐商品", notes = "取消推荐商品")
	@RequestMapping(value = "/staff/offer/{intro_id}", method = RequestMethod.DELETE)
	@ApiImplicitParam(name = "intro_id", value = "商品id", required = true, dataType = "String", paramType = "path")
	public void delOfferIntro(@PathVariable String intro_id, HttpServletRequest request) {
		LOG.info("取消推荐商品");
		System.out.println(offerRecommendMapper.deleteByPrimaryKey(intro_id));

	}

	@ApiOperation(value = "修改推荐商品的语音介绍", notes = "修改推荐商品的语音介绍")
	@RequestMapping(value = "/staff/offer/{intro_id}", method = RequestMethod.PUT)
	@ApiImplicitParam(name = "intro_id", value = "商品id", required = true, dataType = "String", paramType = "path")
	public void editOfferIntro(@PathVariable String intro_id,
							   @RequestParam("voice_intro") String voice_intro,
							   HttpServletRequest request) {
		LOG.info("修改推荐商品的语音介绍");
		OfferRecommend offerRecommend = offerRecommendMapper.selectByPrimaryKey(intro_id);
		offerRecommend.setVoiceIntro(voice_intro);
		offerRecommendMapper.updateByPrimaryKeySelective(offerRecommend);

	}

	@ApiOperation(value = "上传用户图片", notes = "上传用户图片")
	@RequestMapping(value = "/userPhoto", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqAddIntro", value = "上传用户图片", required = true, dataType = "ReqAddIntro")
	public void addIntro(@RequestBody ReqAddIntro reqAddIntro, HttpServletRequest request) {
		LOG.info("获取用户照片");

		String staffid = UserDataUtil.getStaffId(request);
		String enid = UserDataUtil.getEnterpriseId(request);
		//reqAddIntro.setUrl(url);
		reqAddIntro.setType("1");
		reqAddIntro.setCreate_time(new Date());
		reqAddIntro.setEnterprise_id(enid);
		reqAddIntro.setStaff_id(staffid);
		reqAddIntro.setId(UuidUtil.getRandomUuid());
		//reqAddIntro.setOrder(order++);
		//staffInfoMapper.insertIntro(reqAddIntro);
		staffInfoService.insertIntro(reqAddIntro);


	}

	@ApiOperation(value = "删除用户照片", notes = "修改用户照片")
	@RequestMapping(value = "/userPhoto/{id}", method = RequestMethod.DELETE)
	@ApiImplicitParam(name = "id", value = "查看企业参数", paramType = "path", required = true, dataType = "String")
	public void delIntro(@PathVariable String id) {
		LOG.info("删除用户照片");
		staffInfoService.delintro(id);

	}

	@ApiOperation(value = "获取用户的所在企业发布的产品列表", notes = "获取用户的所在发布的产品列表")
	@RequestMapping(value = "/staffProduct", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqProductPage", value = "分页信息", dataType = "ReqProductPage")
	public RspProdPage staffProduct(@RequestBody ReqProductPage reqProductPage, HttpServletRequest request) {
		String enid = UserDataUtil.getEnterpriseId(request);
		String userid = UserDataUtil.getStaffId(request);
		reqProductPage.setEnterprise_id(enid);
		reqProductPage.setUser_id(userid);
		RspProdPage repAllProductRspPage = productService.enterpriseProduct(reqProductPage);
		return repAllProductRspPage;
	}

	@ApiOperation(value = "获取用户的所在企业发布的产品数", notes = "获取用户的所在企业发布的产品数")
	@RequestMapping(value = "/staffProductcount", method = RequestMethod.POST)
	public Integer staffProduct(HttpServletRequest request) {
		String enid = UserDataUtil.getEnterpriseId(request);
		String userid = UserDataUtil.getStaffId(request);
		return productService.staffProduct(enid, userid);
	}

	@ApiOperation(value = "通过获取员工及所属企业的所有朋友圈", notes = "获取所有朋友圈")
	@RequestMapping(value = "/Circle/page", method = RequestMethod.POST)
	public RspPage<RspCircleDetail> CirclePageByStaffId(@RequestBody Page page, HttpServletRequest request) {
		LOG.info("通过获取员工及所属企业的所有朋友圈");
		ReqCircleByStaffAndEnterprise req = new ReqCircleByStaffAndEnterprise();
		req.setEmployee_id(UserDataUtil.getUserId(request));
		req.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
		return circleCompanyService.getAllByStaffId(page.getPage_num(), page.getPage_size(), req);

	}

	@ApiOperation(value = "通过获取员工及所属企业的所有朋友圈数量", notes = "通过获取员工及所属企业的所有朋友圈数量")
	@RequestMapping(value = "/Circle/pageCount", method = RequestMethod.POST)
	public Integer pageCount(HttpServletRequest request) {
		LOG.info("通过获取员工及所属企业的所有朋友圈数量");
		String id = UserDataUtil.getEnterpriseId(request);
		return circleCompanyService.pageCount(id);

	}

	@ApiOperation(value = "通过获取员工的所有朋友圈", notes = "通过获取员工的所有朋友圈")
	@RequestMapping(value = "/Circle/pageByStaff", method = RequestMethod.POST)
	public RspPage<RspCircleDetail> CirclePageByStaff(@RequestBody Page page, HttpServletRequest request) {
		LOG.info("通过获取员工的所有朋友圈");
		ReqCircleByStaffAndEnterprise req = new ReqCircleByStaffAndEnterprise();
		req.setEmployee_id(UserDataUtil.getStaffId(request));
		return circleCompanyService.getAllByStaff(page.getPage_num(), page.getPage_size(), req);

	}

	@ApiOperation(value = "通过获取员工的所有朋友圈数量", notes = "通过获取员工的所有朋友圈数量")
	@RequestMapping(value = "/Circle/pageCountByStaff", method = RequestMethod.POST)
	public Integer pageCountByStaff(HttpServletRequest request) {
		LOG.info("通过获取员工的所有朋友圈数量");
		String id = UserDataUtil.getStaffId(request);
		return circleCompanyService.pageCountByStaff(id);

	}

	@ApiOperation(value = "获取朋友圈详情", notes = "获取朋友圈详情")
	@RequestMapping(value = "/Circle/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "朋友圈id", required = true, dataType = "String", paramType = "path")
	public RspCircleDetail circleDetail(@PathVariable("id") String id, HttpServletRequest request) {
		LOG.info("获取朋友圈详情");
		String userId = UserDataUtil.getStaffId(request);
		System.out.println(id);
		return circleCompanyService.salecircleDetail(id, userId);
	}

	@ApiOperation(value = "员工订单列表", notes = "员工订单列表")
	@RequestMapping(value = "/Circle/Order", method = RequestMethod.POST)
	public RspPage<RspStaffOrder> orderByStaffid(@RequestBody Page page, HttpServletRequest request) {
		LOG.info("员工订单列表");
		String id = UserDataUtil.getStaffId(request);
		String enid = UserDataUtil.getEnterpriseId(request);
		return orderService.orderByStaffid(page, id, enid);

	}

	@ApiOperation(value = "员工订单数", notes = "员工订单数")
	@RequestMapping(value = "/Circle/OrderCount", method = RequestMethod.POST)
	public Integer OrderCount(HttpServletRequest request) {
		LOG.info("员工订单列表");
		String id = UserDataUtil.getStaffId(request);
		String enid = UserDataUtil.getEnterpriseId(request);
		return orderService.OrderCount(id, enid);

	}

	@ApiOperation(value = "订单详情", notes = "订单详情")
	@RequestMapping(value = "/Circle/OrderDetail/{id}/{detailId}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "订单id", required = true, dataType = "String", paramType = "path")
	public RspOrderDetail orderDetail(@PathVariable("id") String id, @PathVariable("detailId") String detailId, HttpServletRequest request) {
		LOG.info("订单详情");
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		return orderService.orderDetail(id, detailId, enterpriseId);

	}

	@ApiOperation(value = "查询用户简要信息", notes = "查询用户简要信息")
	@RequestMapping(value = "/user/simple/", method = RequestMethod.GET)
	public RspUserSimpleInfo simpleInfo(@ApiParam(value = "user_id", name = "user_id") @RequestParam(value = "user_id") String userId, @ApiParam(value = "staff_id", name = "staff_id") @RequestParam(value = "staff_id", required = false) String staffId, HttpServletRequest request) {
		LOG.info("查询用户简要信息");
		String enterpriseId = UserDataUtil.getMyEnterpriseId(request);
		if (staffId == null) {
			staffId = UserDataUtil.getMyStaffId(request);
		}
        /*UserInfo userInfo = userInfoService.selectByPrimaryKey(id);
        RspUserSimpleInfo simpleInfo = new RspUserSimpleInfo();
        simpleInfo.setHead_icon(userInfo.getHeadIcon());
        simpleInfo.setName(userInfo.getName());
        simpleInfo.setNick_name(userInfo.getNickName());
        simpleInfo.setHx_im_login(userInfo.getHxImLogin());*/
		return userInfoService.simpleUserInfo(userId, staffId, enterpriseId);
	}

	@ApiOperation(value = "互动跟进", notes = "互动跟进")
	@RequestMapping(value = "/user/log/page", method = RequestMethod.POST)
	public RspPage<UserActionLog> queryUserActionLog(@RequestBody ReqLog reqLog, HttpServletRequest request) {
		LOG.info("查询用户动作");
		reqLog.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
		reqLog.setStaff_id(UserDataUtil.getStaffId(request));
		return userInfoService.queryUserActionLog(reqLog);
	}

	@ApiOperation(value = "跟进", notes = "跟进")
	@RequestMapping(value = "/user/follow/page", method = RequestMethod.POST)
	public RspPage<UserActionLog> queryFollowLog(@RequestBody ReqLog reqLog, HttpServletRequest request) {
		LOG.info("查询用户动作");
		reqLog.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
		reqLog.setStaff_id(UserDataUtil.getStaffId(request));
		return userInfoService.queryFollowLog(reqLog);
	}

	@ApiOperation(value = "能力排行-跟进跟进/跟进", notes = "跟进跟进/跟进")
	@RequestMapping(value = "/user/follow_and_log/page", method = RequestMethod.POST)
	public RspPage<UserActionLog> queryFollowLogAndAction(@RequestBody ReqLog reqLog, HttpServletRequest request) {
		LOG.info("能力排行-跟进跟进/跟进");
		reqLog.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
		reqLog.setStaff_id(UserDataUtil.getStaffId(request));
		if (reqLog.getType() == 1) {
			return userInfoService.queryUserActionLog(reqLog);
		} else {
			return userInfoService.queryFollowLog(reqLog);
		}

	}

	@ApiOperation(value = "查询用户AI分析数据", notes = "查询用户AI分析数据")
	@RequestMapping(value = "/user/ai/analysis", method = RequestMethod.GET)
	public RspAnalysisData analysisData(@ApiParam(value = "user_id", name = "user_id") @RequestParam(value = "user_id") String userId
			, HttpServletRequest request) {
		String staffId = UserDataUtil.getStaffId(request);
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		return userInfoService.queryUserData(userId, staffId, enterpriseId);
	}

	@ApiOperation(value = "客户详情", notes = "客户详情")
	@RequestMapping(value = "/user/detail", method = RequestMethod.GET)
	public RspUserDetail4Sale detail(@ApiParam(value = "user_id", name = "user_id") @RequestParam(value = "user_id") String userId, HttpServletRequest request) {
		String staffId = UserDataUtil.getStaffId(request);
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		return userInfoService.queryDetail(userId, staffId, enterpriseId);
	}

	@ApiOperation(value = "通过BOSS雷达查看对应销售人员的客户的详情", notes = "通过BOSS雷达查看对应销售人员的客户的详情")
	@RequestMapping(value = "/user/getDetailByStaffId", method = RequestMethod.GET)
	public RspUserDetail4Sale detail(@ApiParam(value = "user_id", name = "user_id") @RequestParam(value = "user_id") String userId, @ApiParam(value = "staff_id", name = "staff_id") @RequestParam(value = "staff_id") String staffId, HttpServletRequest request) {
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		return userInfoService.queryDetail(userId, staffId, enterpriseId);
	}

	@ApiOperation(value = "编辑用户信息", notes = "编辑用户信息")
	@RequestMapping(value = "/user/edit", method = RequestMethod.POST)
	@ApiImplicitParam(name = "rspUserDetail4Sale", value = "编辑用户信息", required = true, dataType = "RspUserDetail4Sale")
	public void editUser(@RequestBody RspUserDetail4Sale rspUserDetail4Sale) {
		rspUserDetail4Sale.setModify_time(new Date());
		userInfoService.editUser(rspUserDetail4Sale);
	}

	@ApiOperation(value = "给客户添加、去除标签", notes = "给客户添加、去除标签")
	@RequestMapping(value = "/user/label", method = RequestMethod.PUT)
	public void addLabel(@ApiParam(value = "user_id", name = "user_id") @RequestParam(value = "user_id") String userId,
						 @ApiParam(value = "staff_id", name = "staff_id") @RequestParam(value = "staff_id") String staffId,
						 @ApiParam(value = "enterprise_id", name = "enterprise_id") @RequestParam(value = "enterprise_id") String enterpriseId,
						 @ApiParam(value = "label_id", name = "label_id") @RequestParam(value = "label_id") String labelId,
						 @ApiParam(value = "1添加2删除", name = "type") @RequestParam(value = "type") String type) {
		if (type.equals("1")) {
			UserLabelInfo userLabelInfo = new UserLabelInfo();
			userLabelInfo.setCreateTime(new Date());
			userLabelInfo.setModifyTime(new Date());
			userLabelInfo.setId(UuidUtil.getRandomUuid());
			userLabelInfo.setLabelId(labelId);
			userLabelInfo.setStaffId(staffId);
			userLabelInfo.setUserId(userId);
			userLabelInfoService.insertSelective(userLabelInfo);
		} else {
			userLabelInfoService.delete(userId, staffId, labelId);
		}

	}


	@ApiOperation(value = "销售 发布朋友圈", notes = "销售 发布朋友圈")
	@RequestMapping(value = "/user/Circle", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqAddCircle", value = "销售 发布朋友圈", required = true, dataType = "ReqAddCircle")
	public void sendCircle1(@RequestBody ReqAddCircle reqAddCircle, HttpServletRequest request) {
		LOG.info("发布个人朋友圈");
		reqAddCircle.setType(0);
		//reqAddCircle.setEmployee_id(UserDataUtil.getStaffId(request));
		//reqAddCircle.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
		if (reqAddCircle.getFlag() == null) {
			circleCompanyService.sendCircle(reqAddCircle, 0);
		} else {
			if (reqAddCircle.getFlag() == 1) {
				// 发布公司朋友圈 employee_id 表示企业id
				reqAddCircle.setEmployee_id(UserDataUtil.getEnterpriseId(request));
			}
			circleCompanyService.sendCircle(reqAddCircle, reqAddCircle.getFlag());
		}

	}

	@ApiOperation(value = "销售 点赞或取消赞", notes = "点赞或取消赞")
	@RequestMapping(value = "/user/Zan", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqZan", value = "点赞或取消赞", required = true, dataType = "ReqZan")
	public RspStaffCommentOrZan zan(@RequestBody ReqZan reqZan, HttpServletRequest request) {
		RspStaffCommentOrZan rsp = new RspStaffCommentOrZan();
		LOG.info("点赞或取消赞");
		reqZan.setUserId(UserDataUtil.getStaffId(request));
		CircleZanLog circleZanLog = reqZan.toCircleZanLog();//new CircleZanLog(reqZan);
		circleZanLog.setFlag(0);//1公司点赞 0销售点赞 2客户点赞
		rsp.setId(circleZanLogService.zan(circleZanLog));
		rsp.setName(staffInfoService.selectByPrimaryKey(circleZanLog.getUserId()).getName());
		return rsp;
	}

	@ApiOperation(value = "销售人员 评论朋友圈", notes = "销售人员 评论朋友圈")
	@RequestMapping(value = "/user/Comment", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqAddComment", value = "销售人员 评论朋友圈", required = true, dataType = "ReqAddComment")
	public RspStaffCommentOrZan commentCircle(@RequestBody ReqAddComment reqAddComment, HttpServletRequest request) {
		RspStaffCommentOrZan rsp = new RspStaffCommentOrZan();
		LOG.info("销售人员评论朋友圈");
		reqAddComment.setUserId(UserDataUtil.getStaffId(request));
		CircleCommentLog circleCommentLog = reqAddComment.toCircleCommentLog();
		circleCommentLog.setFlag(0);
		rsp.setContent(reqAddComment.getContent());
		rsp.setId(circleCommentLog.getId());
		rsp.setName(staffInfoService.selectByPrimaryKey(reqAddComment.getUserId()).getName());
		circleCommentLogService.circleComment(circleCommentLog);
		return rsp;
	}

	@ApiOperation(value = "编辑销售朋友圈", notes = "编辑销售朋友圈")
	@RequestMapping(value = "/user/Circle", method = RequestMethod.PUT)
	@ApiImplicitParam(name = "reqUpdateCircle", value = "编辑公司朋友圈", required = true, dataType = "ReqUpdateCircle")

	//不区分企业跟个人
	public void editCircle(@RequestBody ReqUpdateCircle reqUpdateCircle) {
		LOG.info("编辑朋友圈");
		circleCompanyService.editCircle(reqUpdateCircle);
	}

	@ApiOperation(value = "删除朋友圈", notes = "删除朋友圈")
	@RequestMapping(value = "/user/Circle/{id}", method = RequestMethod.DELETE)
	@ApiImplicitParam(name = "id", value = "朋友圈id", required = true, dataType = "String", paramType = "path")
	//不区分企业跟个人
	public void delCircle(@PathVariable("id") String id) {
		LOG.info("删除朋友圈");
		circleCompanyService.delCircle(id);
	}

	@ApiOperation(value = "删除评论", notes = "删除评论")
	@RequestMapping(value = "/user/Comment/{id}", method = RequestMethod.DELETE)
	@ApiImplicitParam(name = "id", value = "评论记录id", required = true, dataType = "String", paramType = "path")
	//不去分企业跟个人
	public void delComment(@PathVariable("id") String id) {
		LOG.info("删除评论");
		circleCommentLogService.delComment(id);
	}

	@RequestMapping(value = "/chooseEnterprise", method = RequestMethod.POST)
	@ApiOperation(value = "企业微信平台用户选择企业", notes = "企业微信平台用户选择企业")
	public repChoseCompany choseEnterprise(String code, String enterpriseid, String wx_id) {
		// 获取平台的
		if (!code.equals("null")) {
			wx_id = weChatApi.aiUserIdByCode(code, enterpriseid);
		}
		LOG.info("---wx_id:" + wx_id);
		List<Enterprise> enterpriseList = enterpriseService.getEnterpriseListByWxId(wx_id);
		List<CompanyList> repChoseCompanyList = new ArrayList<>();
		for (Enterprise enterprise : enterpriseList) {
			CompanyList choseCompany = new CompanyList();
			choseCompany.setEnterpriseId(enterprise.getId());
			choseCompany.setName(enterprise.getName());
			choseCompany.setLogo(enterprise.getHeadIcon());
			repChoseCompanyList.add(choseCompany);
		}
		repChoseCompany repChoseCompany = new repChoseCompany();
		repChoseCompany.setWx_id(wx_id);
		repChoseCompany.setCompanyList(repChoseCompanyList);
		return repChoseCompany;
	}

	@RequestMapping(value = "/login/ai", method = RequestMethod.POST)
	@ApiOperation(value = "ai登陆session", notes = "ai登陆session")
	public String aitoken(String code, String enterpriseid, String wx_id) {

		LOG.info("code:" + code + "----------enterpriseid:" + enterpriseid + "----------wx_id:" + wx_id);
		if (!code.equals("null")) {
			wx_id = weChatApi.aiUserIdByCode(code, enterpriseid);
		}
		LOG.info("---wx_id:" + wx_id);

		// Add by PLH at 2018-12-01 for 平台AI登陆，暂时先默认取第一个企业 Begin
		String staffEnterpriseId = enterpriseid;
		if (PlatformEnterprise.ID.equals(enterpriseid)) {
			//如果是平台ID，这时候要用wx_id去找员工有哪些企业
			List<Enterprise> enterpriseList = enterpriseService.getEnterpriseListByWxId(wx_id);
			if (enterpriseList != null && enterpriseList.size() > 0) {
				staffEnterpriseId = enterpriseList.get(0).getId();
			}
		}
		// Add by PLH at 2018-12-01 for 平台AI登陆，暂时先默认取第一个企业 End

		Staff staff = staffInfoService.findstaffBywxidAndEnid(wx_id, staffEnterpriseId);
		LOG.info("findstaffBywxidAndEnid--------------------------------------B");
		LOG.info(GsonUtil.toJson(staff));
		LOG.info("findstaffBywxidAndEnid--------------------------------------E");
		if (staff == null) {
			return "0";
		}
		String token = "";
		if (staff.getOpenAi() == 1) {
			token = MD5Helper.getMD5Str(staff.getId());
			UserDataUtil.setStaffId(token, staff.getId());
			UserDataUtil.setEnterpriseId(token, staff.getEnterpriseId());
			UserDataUtil.setDepartmentId(token, staff.getDepartmentId());
			UserDataUtil.setMyStaffId(token, staff.getId());
			UserDataUtil.setMyEnterpriseId(token, staff.getEnterpriseId());
			UserDataUtil.setMyDepartmentId(token, staff.getDepartmentId());
			return token;
		} else {
			return "0";
		}

	}

	@RequestMapping(value = "/login/boss", method = RequestMethod.POST)
	@ApiOperation(value = "boss登陆session", notes = "boss登陆session")
	public String bosstoken(String code, String enterpriseid, String wx_id) {
		if (!code.equals("null")) {
			wx_id = weChatApi.bossUserIdByCode(code, enterpriseid);
		}

		// Add by PLH at 2018-12-01 for 平台AI登陆，暂时先默认取第一个企业 Begin
		String staffEnterpriseId = enterpriseid;
		if (PlatformEnterprise.ID.equals(enterpriseid)) {
			//如果是平台ID，这时候要用wx_id去找员工有哪些企业
			List<Enterprise> enterpriseList = enterpriseService.getEnterpriseListByWxId(wx_id);
			if (enterpriseList != null && enterpriseList.size() > 0) {
				staffEnterpriseId = enterpriseList.get(0).getId();
			}
		}
		// Add by PLH at 2018-12-01 for 平台AI登陆，暂时先默认取第一个企业 End

		Staff staff = staffInfoService.findstaffBywxidAndEnid(wx_id, staffEnterpriseId);
		if (staff == null) {
			return "0";
		}
		String token = "";
		if (staff.getOpenBoss() == 1) {
			token = MD5Helper.getMD5Str(staff.getId());
			UserDataUtil.setStaffId(token, staff.getId());
			System.out.print("登陆_______" + staff.getEnterpriseId());
			UserDataUtil.setEnterpriseId(token, staff.getEnterpriseId());
			UserDataUtil.setDepartmentId(token, staff.getDepartmentId());
			UserDataUtil.setMyStaffId(token, staff.getId());
			UserDataUtil.setMyEnterpriseId(token, staff.getEnterpriseId());
			UserDataUtil.setMyDepartmentId(token, staff.getDepartmentId());
			return token;
		} else {
			return "0";
		}

	}

	@ApiOperation(value = "查询总览数据", notes = "查询总览数据")
	@RequestMapping(value = "/mini/index/view", method = RequestMethod.GET)
	public List<RspViewData> viewdata(@ApiParam(value = "1汇总2昨日3近七天4近30天", name = "type") @RequestParam(value = "type") Integer type, HttpServletRequest request) {
		String departmentId = UserDataUtil.getDepartmentId(request);
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		return bossService.viewdata(type, null, departmentId, enterpriseId);
	}

	@ApiOperation(value = "客户列表", notes = "客户列表")
	@RequestMapping(value = "/userList/simple", method = RequestMethod.GET)
	public RspUserandStaff userinfo(HttpServletRequest request) {
//        String Staffid=UserDataUtil.getStaffId(request);
//        String enterpriseid=UserDataUtil.getEnterpriseId(request);
		String Staffid = UserDataUtil.getMyStaffId(request);
		String enterpriseid = UserDataUtil.getMyEnterpriseId(request);
		return userInfoService.userinfo(Staffid, enterpriseid);
	}

	@ApiOperation(value = "部门名称", notes = "部门名称")
	@RequestMapping(value = "/departname", method = RequestMethod.GET)
	public String departname(@RequestParam(value = "id") String id, HttpServletRequest request) {
		String enterpriseid = UserDataUtil.getEnterpriseId(request);
		return staffInfoService.departname(enterpriseid, id);
	}


	@ApiOperation(value = "部门列表", notes = "部门列表")
	@RequestMapping(value = "/departList", method = RequestMethod.GET)
	public List<RspDepartInfo> departList(HttpServletRequest request) {
		String enterpriseid = UserDataUtil.getEnterpriseId(request);
		return departmentService.departinfo(enterpriseid);
	}

	@ApiOperation(value = "获取sig", notes = "获取sig")
	@RequestMapping(value = "/sig/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
	public String sig(@PathVariable("id") String id, HttpServletRequest request) {
		String enid = UserDataUtil.getEnterpriseId(request);
		return tlsUtil.getSig(id, enid);
	}

	@ApiOperation(value = "注册im", notes = "注册im")
	@RequestMapping(value = "/adduser", method = RequestMethod.GET)
	@ApiImplicitParam(name = "repImInfo", value = "用户id 昵称", required = true, dataType = "RepImInfo")
	public Integer adduser(@RequestBody RepImInfo repImInfo, HttpServletRequest request) {
		String enid = UserDataUtil.getEnterpriseId(request);
		String id = repImInfo.getId();
		String nick = repImInfo.getNick_name();
		return tlsUtil.addUser(id, nick, enid);
	}

	@ApiOperation(value = "获取微信用户的头像", notes = "获取微信用户的头像")
	@RequestMapping(value = "/userhead", method = RequestMethod.POST)
	@ApiImplicitParam(name = "reqImInfo", value = "reqImInfo", required = true, dataType = "ReqImInfo")
	public List<RspHeadName2> userhead(@RequestBody List<String> id) {
		return userInfoService.userhead(id);
	}

	@ApiOperation(value = "获取微信用户的头像", notes = "获取微信用户的头像")
	@RequestMapping(value = "/userhead/{id}", method = RequestMethod.GET)
	@ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
	public RspHeadName userhead(@PathVariable String id) {
		return userInfoService.userhead2(id);
	}

	@ApiOperation(value = "获取销售签名", notes = "获取销售签名")
	@RequestMapping(value = "/signature", method = RequestMethod.GET)
	public String getSig(HttpServletRequest request) {
		String staffId = UserDataUtil.getStaffId(request);
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		return staffInfoService.getSig(staffId, enterpriseId);
	}

	@ApiOperation(value = "修改销售签名", notes = "修改销售签名")
	@RequestMapping(value = "/signature/{content}", method = RequestMethod.PUT)
	@ApiImplicitParam(name = "content", value = "签名", paramType = "path", required = true, dataType = "String")
	public void editSig(@PathVariable String content, HttpServletRequest request) {
		String staffId = UserDataUtil.getStaffId(request);
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		staffInfoService.editSig(content, staffId, enterpriseId);
	}

	@ApiOperation(value = "修改销售签名(new)", notes = "修改销售签名(new)")
	@RequestMapping(value = "/signature", method = RequestMethod.POST)
	@ApiImplicitParam(name = "content", value = "签名", required = true, dataType = "String")
	public void editSignew(@RequestBody String content, HttpServletRequest request) {
		String staffId = UserDataUtil.getStaffId(request);
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		staffInfoService.editSig(content, staffId, enterpriseId);
	}

	@ApiOperation(value = "获取语音介绍", notes = "获取语音介绍")
	@RequestMapping(value = "/signature_video", method = RequestMethod.GET)
	public String getSigVideo(HttpServletRequest request) {
		String staffId = UserDataUtil.getEnterpriseId(request);
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		return staffInfoService.getSigVideo(staffId, enterpriseId);
	}

	@ApiOperation(value = "修改语音介绍", notes = "修改语音介绍")
	@RequestMapping(value = "/signature_video", method = RequestMethod.PUT)
	public void editSigVideo(@ApiParam(value = "content", name = "content") @RequestParam(value = "content") String content, HttpServletRequest request) {
		String staffId = UserDataUtil.getEnterpriseId(request);
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		staffInfoService.editSigVideo(content, staffId, enterpriseId);
	}

	@ApiOperation(value = "企信相关", notes = "企业微信相关")
	@RequestMapping(value = "/wx_config", method = RequestMethod.GET)
	public RspWxConfig wxConfig(HttpServletRequest request) {
		RspWxConfig wxConfig = new RspWxConfig();
		String enterpriseId = UserDataUtil.getEnterpriseId(request);
		String jsapi_ticket = weChatApi.jsapi_ticket(enterpriseId);
		String corpid = enterpriseService.findCropId(enterpriseId);
		wxConfig.setCorpid(corpid);
		wxConfig.setJsapi_ticket(jsapi_ticket);
		return wxConfig;
	}

}
