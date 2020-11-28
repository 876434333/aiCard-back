package com.vma.push.business.dao;

import com.vma.push.business.dto.req.ReqAccountSelect;
import com.vma.push.business.dto.req.ReqSystemLogin;
import com.vma.push.business.dto.req.ReqUserMenu;
import com.vma.push.business.dto.req.superback.ReqAdminPage;
import com.vma.push.business.dto.req.superback.ReqUpdatePassword;
import com.vma.push.business.dto.rsp.RspAccount;
import com.vma.push.business.dto.rsp.RspAgent;
import com.vma.push.business.dto.rsp.ai.CompanyList;
import com.vma.push.business.dto.rsp.superback.RspAdmin;
import com.vma.push.business.dto.rsp.userInfo.RspWebInfo;
import com.vma.push.business.entity.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by ChenXiaoBin
 * 2018/5/13 15:48
 */

public interface AdminMapper extends BaseDao<Admin,String> {
    Admin findByPhone(String phone);//根据电话查询超级账号
    //asdasdasd

    List<RspAccount> findAllAccount(ReqAccountSelect reqAccountSelect);//查询所有账号

    List<RspAgent> findAllAgent();//查询代理商列表

    Admin selectByPhone(String phone);//根据电话查询

    Admin validateUser(ReqSystemLogin reqSystemLogin);//查询账号密码是否正确

    public Admin find(Map param);

    public List<RspAdmin> findPage(ReqAdminPage reqAdminPage); //分页查询员工信息

    /**
     * 通过账号跟权限判断账号是否存在
     * @return
     */
    public Integer isExist(Admin admin);

    /**
     * 修改登陆账号
     * @param admin
     * @return
     */
    public Integer updateLogin(Admin admin);

    /**
     * 通过登陆用户的type获取web信息
     * @param type
     * @return
     */
    public RspWebInfo findWebInfo(Integer type);

    void setMenuRole(ReqUserMenu reqUserMenu);

    void updatePassword(ReqUpdatePassword reqUpdatePassword);

    Admin selectLogin(String phone);

    void updateStatus(Admin admin);

    Admin queryEnterAdmin(String id);
    List<CompanyList> getEnterpriseListByPhone(String phone);
    List<Admin> selectAdminListByPhone(String phone);

    @Select("select id from admin")
    List<String> getUserIdList();

    @Insert("INSERT INTO user_menu_ref(id,menu_id,user_id,`status`) value ((select RAND() id),'63',#{userId},1)")
    Integer addMag(String userId);
}
