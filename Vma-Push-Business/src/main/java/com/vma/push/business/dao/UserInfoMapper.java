package com.vma.push.business.dao;


import com.vma.push.business.dto.req.staff.ReqCarAction;
import com.vma.push.business.dto.req.staff.ReqCustomList;
import com.vma.push.business.dto.req.userInfo.ReqQueryUserInfo;
import com.vma.push.business.dto.rsp.RspHeadName;
import com.vma.push.business.dto.rsp.staff.RspCustomList;
import com.vma.push.business.dto.rsp.store.RspUserCartList;
import com.vma.push.business.dto.rsp.superback.RspLoginInfo;
import com.vma.push.business.dto.rsp.userInfo.RspUserDetail4Sale;
import com.vma.push.business.dto.rsp.userInfo.RspUserInfo;
import com.vma.push.business.dto.rsp.userInfo.RspUserInfoList;
import com.vma.push.business.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper extends BaseDao<UserInfo,String> {
	
	public List<RspUserInfoList> queryUsers(ReqQueryUserInfo reqQueryUserInfo);

  	public UserInfo selectByOpenId(@Param("openId") String openId);

	public List<String> queryUserLabels(@Param("userId") String userId,@Param("staffId") String staffId);

	public RspUserDetail4Sale queryUserByStaff(@Param("enterpriseId") String enterpriseId,@Param("userId") String userId, @Param("staffId") String staffId);

	RspUserInfo findUserInfoByOpenId(@Param("open_id") String open_id);//根据open_id查询

	String findUserId(@Param("open_id")String open_d);//获取用户的id

	RspHeadName userhead(String id);
	String openByUserId(String userId);
	RspLoginInfo userInfo(String id);

	String isPhone(@Param("id") String id);

	List<RspCustomList> customOfStaffOrderByUserNumber(ReqCustomList req);

	List<RspCustomList> customOfStaffOrderByInteract(ReqCustomList req);

	List<RspCustomList> customOfStaffOrderByClosedRate(ReqCustomList req);

	List<RspUserCartList> userShopCartList(@Param("userId") String userId,@Param("staffId")String staffId);
	List<String> getUserInfoListByLastStaffId(@Param("staffIdList") List<String> staffIdList);
}



 
 

 
