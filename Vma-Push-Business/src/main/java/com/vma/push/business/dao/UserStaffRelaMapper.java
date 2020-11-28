package com.vma.push.business.dao;

import com.vma.push.business.dto.req.ReqUpdateRate;
import com.vma.push.business.dto.req.ReqUpdateTime;
import com.vma.push.business.dto.req.staff.ReqCarAction;
import com.vma.push.business.dto.rsp.mini.ResOpenId;
import com.vma.push.business.dto.rsp.userInfo.RspUserDetail4Sale;
import com.vma.push.business.dto.rsp.userInfo.RspUserSimpleInfo;
import com.vma.push.business.entity.UserStaffRela;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by chenzui on 2018/5/14.
 */
public interface UserStaffRelaMapper extends BaseDao<UserStaffRela, String> {
	public UserStaffRela queryStaffInfoByUser(@Param("staffId") String staffId, @Param("userId") String userId, @Param("enterpriseId") String enterpriseId);

	public Long countUser(Map param);

	void updateCarAction(ReqCarAction reqCarAction);//开启 关闭名片

	RspUserSimpleInfo simpleUserInfo(@Param("id") String id, @Param("staffid") String staffid, @Param("enterpriseid") String enterpriseid);

	int editUser(RspUserDetail4Sale rspUserDetail4Sale);

	public void updateDepartment(@Param("staffId") String staffId, @Param("newer") String newer, @Param("older") String older);

	public void deleteByStaff(@Param("staffId") String staffId);

	Integer updateClinchTime(ReqUpdateTime reqUpdateTime);

	Integer updateClinchRate(ReqUpdateRate reqUpdateRate);

	long attachcountUser(Map param);

	List<UserStaffRela> getUserRelaListByStaffid(@Param("staffId") String staffId);

	//Add by PLH at 2018-11-29 for 关联深卡小秘
	List<UserStaffRela> getUserRelaListByUserId(@Param("userId") String userId);

	String getPhone(@Param("userId") String userId, @Param("staffId") String staffId);

	Integer updataPhone(UserStaffRela userStaffRela);

	// 收藏名片
	Integer updataIsCollect(@Param("id")String id,@Param("is_collect") Integer isCollect);

	// 获取看过我名片的人
	List<ResOpenId> getSeeMyselfCardList(@Param("listStaffId")List<String> listStaffId);
}
