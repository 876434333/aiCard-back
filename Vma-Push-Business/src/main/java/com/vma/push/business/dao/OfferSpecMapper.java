package com.vma.push.business.dao;

import com.vma.push.business.dto.req.store.*;
import com.vma.push.business.dto.req.store.RspOfferImg;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.req.PageSelect;
import com.vma.push.business.dto.req.ReqAddProduct;
import com.vma.push.business.dto.rsp.staff.RspStaffOfferIntro;
import com.vma.push.business.dto.rsp.store.*;
import com.vma.push.business.entity.OfferImg;
import com.vma.push.business.entity.OfferSpec;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/4/25 11:06
 */
public interface OfferSpecMapper extends BaseDao<OfferSpec,String>{
    RepSpecAndImg selectProductById(String id);
    List<RepAllProduct> findAllProductBySelect(PageSelect pageSelect);//模糊查询产品
    List<RspStaffProduct> enterpriseProduct(String enterpriseid);
    Integer staffProduct(@Param("enid")String  enid, @Param("userid")String userid);
   void deleteImg(String id);//删除图片
    List<RspImage> selectImg(String id);

    /**
     * 获取产品图片路径
     * @param id
     * @return
     */
    List<RspImage> selectImgUrls(String id);

    OfferSpec findAll(String id);//根据id获取产品

    List<RspOfferInfo> offerPage(@Param("eneterpriseId") String eneterpriseId,@Param("staffId")String staffId);

    List<RspOfferList> offerList(ReqOfferList reqOfferList);

    ReqEditOffer offerDetail(String id);

    List<RspOfferNorms> normsDetail(String id);

    List<RspOfferImg> imgDetail(String id);

    void delNorms(RspDelNorms rspDelNorms);

    void changeStatus(ReqChangStatus reqChangStatus);

    RspNormsInfo normsInfo(String id);

    List<RspNormsInfo> normsInfo2(String id);

    RspOffInfo offerInfo(String id);

    List<com.vma.push.business.dto.rsp.store.RspOfferImg> imgInfo(String id);

    List<RspRecOffer> recList(String staffId);

    List<RspStaffOfferIntro> staffOfferIntro(@Param("staffid") String staffid, @Param("enid") String enid);
}
