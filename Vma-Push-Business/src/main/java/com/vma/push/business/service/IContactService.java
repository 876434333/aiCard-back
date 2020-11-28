package com.vma.push.business.service;

import com.vma.push.business.dto.ContactCard;
import com.vma.push.business.dto.ContactStaff;
import com.vma.push.business.dto.req.mini.ReqSearch;
import com.vma.push.business.dto.rsp.mini.ResOpenId;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * created by ljh on 2018/10/29
 */
public interface IContactService{

    List<ContactCard> getCardList(String staffId,String openId,String relaStatus);

    List<ContactCard> getContactCardList(String staffId,String openId);

    List<ContactStaff> getContactStaffList(String userId, String openId);

    ContactCard getReceiverStaff(String id);

    String getStaffId(String open_id);
    // 收藏名片
    void collectCard(String id,int isCollect);
    List<ContactCard> getCollectCardList(String userId);
    List<ContactCard> searchCard(ReqSearch reqSearch, HttpServletRequest request);
    List<ResOpenId> getSeeMyselfCardList(HttpServletRequest request);
}
