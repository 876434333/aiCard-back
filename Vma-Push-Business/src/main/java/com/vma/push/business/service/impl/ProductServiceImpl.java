package com.vma.push.business.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.vma.push.business.common.ActionCode;
import com.vma.push.business.dao.*;
import com.vma.push.business.dto.RspProdPage;
import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.req.store.*;
import com.vma.push.business.dto.req.store.RspOfferImg;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.rsp.area.*;
import com.vma.push.business.dto.rsp.staff.RspStaff;
import com.vma.push.business.dto.rsp.store.*;
import com.vma.push.business.entity.*;
import com.vma.push.business.entity.AreaInfo;
import com.vma.push.business.service.IProductService;
import com.vma.push.business.service.IStaffInfoService;
import com.vma.push.business.util.UserDataUtil;
import com.vma.push.business.util.UuidUtil;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/4/27 16:05
 */
@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private OfferSpecMapper offerSpecMapper;
    @Autowired
    private OfferImgMapper offerImgMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private  ClickActionLogMapper clickActionLogMapper;
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private OfferNormsMapper offerNormsMapper;
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private AreaInfoMapper areaInfoMapper;

    @Autowired
    private CityInfoMapper cityInfoMapper;

    /**
     * 添加产品
     * @param reqSpecAndImg
     */
    @Override
    public RspProductId addProduct(ReqSpecAndImg reqSpecAndImg, HttpServletRequest request) {
        OfferImg offerImg = new OfferImg();
        OfferSpec offerSpec = new OfferSpec();
        offerSpec.setId(UuidUtil.getRandomUuid());
        offerSpec.setCreateStaffId(reqSpecAndImg.getStaff_id());//创建人的id
        offerSpec.setCreateTime(new Date());//创建时间
        offerSpec.setCode(UuidUtil.getRandomUuid());//商品编码
        offerSpec.setOfferPrice(reqSpecAndImg.getOffer_price());
        offerSpec.setOfferName(reqSpecAndImg.getOffer_name());
        offerSpec.setEnterpriseId(UserDataUtil.getEnterpriseId(request));
        offerSpecMapper.insertSelective(offerSpec);
        List<ReqImage> reqImages =reqSpecAndImg.getReq_images();
     for(ReqImage img : reqImages){
         offerImg.setImgUrl(img.getImg_url());
         offerImg.setId(UuidUtil.getRandomUuid());
         offerImg.setType(img.getType());
         offerImg.setCreateTime(new Date());
         offerImg.setOfferSpecId(offerSpec.getId());
         offerImg.setOrder(img.getOrder());//排序
         offerImgMapper.insertSelective(offerImg);
     }
     RspProductId rspProductId = new RspProductId();
        rspProductId.setId(offerSpec.getId());
       return rspProductId;
    }




    @Override
    public RspPage<RepAllProduct> findProductBySelect(PageSelect pageSelect) {
        RspPage<RepAllProduct> rspPage = new RspPage<RepAllProduct>();
        Page page = PageHelper.startPage(pageSelect.getPage_num(),pageSelect.getPage_size(),true);
        List<RepAllProduct> list = offerSpecMapper.findAllProductBySelect(pageSelect);
        rspPage.setData_list(list);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    @Override
    public RspPageMiniStore<RspOfferInfo> offerPage(com.vma.push.business.dto.req.Page pageSelect, String eneterpriseId,String staffId) {
        RspPageMiniStore<RspOfferInfo> rspPage = new RspPageMiniStore<RspOfferInfo>();
        Page page = PageHelper.startPage(pageSelect.getPage_num(),pageSelect.getPage_size(),true);
        List<RspOfferInfo> list=offerSpecMapper.offerPage(eneterpriseId,staffId);
        rspPage.setData_list(list);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        //获取企业名字 员工名字  员工折扣
        RspStaffDiscount rspStaffDiscount=staffMapper.staffEnterDiscount(eneterpriseId,staffId);
        if(rspStaffDiscount!=null){
            rspPage.setDiscount(rspStaffDiscount.getDiscount());
            rspPage.setStaff_name(rspStaffDiscount.getStaff_name());
            rspPage.setEnterprise_name(rspStaffDiscount.getEnterprise_name());
            rspPage.setHead_icon(rspStaffDiscount.getHead_icon());
        }
        return rspPage;

    }

    /**
     * chanpingliebiao
     * @param reqProductPage
     * @return
     */
    @Override
    public RspProdPage enterpriseProduct(ReqProductPage reqProductPage) {
        RspProdPage rspPage = new RspProdPage();
        Page page = PageHelper.startPage(reqProductPage.getPage_num(),reqProductPage.getPage_size(),true);
        List<RspStaffProduct> list = offerSpecMapper.enterpriseProduct(reqProductPage.getEnterprise_id());
        for (RspStaffProduct p:list){
            List <RspImage> rspImages = offerSpecMapper.selectImg(p.getId());
            p.setImgs(rspImages);
            p.setSale_num(orderMapper.saleCount(p.getId()));
            Long num =clickActionLogMapper.findNum(reqProductPage.getEnterprise_id(),ActionCode.ACTION_CODE_1016,p.getId());//获取浏览量
            p.setView_num(num.intValue());
        }

        Integer count = orderMapper.findCount(reqProductPage.getUser_id());//查看未支付的订单数
        rspPage.setOrderTotal(count);
        rspPage.setData_list(list);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    @Override
    public Integer staffProduct(String enid, String userid) {
        return  offerSpecMapper.staffProduct(enid,userid);
    }

    /**
     * 根据id查商品
     * @param id
     * @return
     */
    @Override
    public RepSpecAndImg selectProductById(String id,String enterprise_id) {
        RepSpecAndImg repSpecAndImg = offerSpecMapper.selectProductById(id);
        repSpecAndImg.setSale_num(orderMapper.saleCount(repSpecAndImg.getId()));//获取该产品销售数量
        Long num =clickActionLogMapper.findNum(enterprise_id,ActionCode.ACTION_CODE_1016,id);//获取浏览量
        //Long num =clickActionLogMapper.findNum(reqProductPage.getEnterprise_id(),ActionCode.ACTION_CODE_1016,p.getId());//获取浏览量
        repSpecAndImg.setView_num(num.intValue());
        List <RspImage> list = offerSpecMapper.selectImg(id);
        repSpecAndImg.setImg_url(list);
        return repSpecAndImg;
    }


    /**
     * 收货地址列表
     * @return
     */
    @Override
    public RspPage<RspUserAddress> getAddress(ReqUserAddress reqUserAddress) {
        RspPage<RspUserAddress> rspPage = new RspPage<RspUserAddress>();
        Page page = PageHelper.startPage(reqUserAddress.getPage_num(),reqUserAddress.getPage_size(),true);
        List<RspUserAddress> list = userAddressMapper.getAddress(reqUserAddress);
        rspPage.setData_list(list);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    /**
     * 删除收货地址
     * @param reqDelAddress
     */
    @Override
    public void DelAddress(ReqDelAddress reqDelAddress) {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(reqDelAddress.getId());
        userAddress.setStatus(reqDelAddress.getStatus());
        userAddressMapper.updateByPrimaryKeySelective(userAddress);

    }

    /**
     * 添加收货地址
     * @param addAddress
     */
    @Override
    public void addAddress(ReqAddAddress addAddress) {
        UserAddress userAddress = addAddress.toUserAddress();
        String province_code=userAddressMapper.findProvinceCode(addAddress.getProvince_name());
        String province_id = userAddressMapper.findProvinceId(province_code);//获取省级的id
        userAddress.setProvinceCode(province_code);
        String city_code = userAddressMapper.findCityCode(addAddress.getCity_name(),province_id);//城市code
        if(StringUtils.isEmpty(city_code)){
            CityInfo compare = cityInfoMapper.selectCityByPID(Integer.valueOf(province_id));
            Integer compareId = Integer.valueOf(compare.getCityCode());
            CityInfo cityInfo = new CityInfo();
            cityInfo.setCreateTime(new Date());
            cityInfo.setCityCode(String.valueOf(compareId+100));
            cityInfo.setCityName(addAddress.getCity_name());
            cityInfo.setProvinceId(Integer.valueOf(province_id));
            cityInfo.setStatus(1);
            cityInfoMapper.insertSelective(cityInfo);
            userAddress.setCityCode(cityInfo.getCityCode());

        }else {
            userAddress.setCityCode(city_code);

        }
        String city_id = userAddressMapper.findCityId(addAddress.getCity_name(),province_id);//城市id
        String area_code = userAddressMapper.findAreaCode(addAddress.getArea_name(),city_id);

        if(StringUtils.isEmpty(area_code)){
            AreaInfo compare = areaInfoMapper.selectAreaByPID(Integer.valueOf(province_id));
            Integer compareId = Integer.valueOf(compare.getAreaCode());
            AreaInfo areaInfo = new AreaInfo();
            areaInfo.setCreateTime(new Date());
            areaInfo.setAreaCode(String.valueOf(compareId+1));
            areaInfo.setAreaName(addAddress.getArea_name());
            areaInfo.setProvinceId(Integer.valueOf(province_id));
            areaInfo.setCityId(Integer.valueOf(city_id));
            areaInfoMapper.insertSelective(areaInfo);
            userAddress.setAreaCode(areaInfo.getAreaCode());
        }else {
            userAddress.setAreaCode(area_code);

        }

        List<UserAddress> list = userAddressMapper.findByUserId(addAddress.getUser_id());
        if(null == list || list.size() ==0){
            userAddress.setIsDefault(1);
        }
        userAddressMapper.insertSelective(userAddress);
    }

    /**
     * 收货地址详情
     * @param id
     * @return
     */
    @Override
    public RspDetailAddress Detail(String id) {

        return userAddressMapper.detail(id);
    }

    /**
     * 修改收货地址信息
     * @param reqUpdateAddress
     */
    @Override
    public void updateAddress(ReqUpdateAddress reqUpdateAddress) {
        userAddressMapper.updateByPrimaryKeySelective(reqUpdateAddress.toUserAddress());
    }

    /**
     * 修改是否为默认
     * @param reqUpdateDefault
     */
    @Override
    @Transactional
    public void updateDefault(ReqUpdateDefault reqUpdateDefault) {
        userAddressMapper.updateAllDefault(reqUpdateDefault.getUser_id());
        UserAddress userAddress = new UserAddress();
        userAddress.setId(reqUpdateDefault.getId());
        userAddress.setIsDefault(reqUpdateDefault.getIs_default());
        userAddressMapper.updateByPrimaryKeySelective(userAddress);

    }

    /**
     * 更新产品
     * @param reqUpdateProduct
     */
    @Override
    public void updateProduct(ReqUpdateProduct reqUpdateProduct) {
        offerSpecMapper.deleteImg(reqUpdateProduct.getId());//先删除图片然后再添加
          OfferSpec offerSpec = new OfferSpec();
          OfferImg offerImg = new OfferImg();
          offerSpec.setOfferName(reqUpdateProduct.getOffer_name());
          offerSpec.setOfferPrice(reqUpdateProduct.getOffer_price());
          offerSpec.setCreateStaffId(reqUpdateProduct.getId());
          offerSpec.setId(reqUpdateProduct.getId());
          offerSpec.setUpdateTime(new Date());
          offerSpecMapper.updateByPrimaryKeySelective(offerSpec);

           List <ReqImage> reqImages = reqUpdateProduct.getReq_images();
           for (ReqImage reqImage:reqImages){
               offerImg.setImgUrl(reqImage.getImg_url());
               offerImg.setId(UuidUtil.getRandomUuid());
               offerImg.setType(reqImage.getType());
               offerImg.setCreateTime(new Date());
               offerImg.setOfferSpecId(offerSpec.getId());
               offerImg.setOrder(reqImage.getOrder());//排序
               offerImgMapper.insertSelective(offerImg);

           }
    }

    @Override
    public RspPage<RspRecOffer> recList(String staffId, com.vma.push.business.dto.req.Page pageParam) {
        RspPage<RspRecOffer> rspPage=new RspPage<>();
        Page page = PageHelper.startPage(pageParam.getPage_num(),pageParam.getPage_size(),true);
        List<RspRecOffer> list=offerSpecMapper.recList(staffId);
        rspPage.setData_list(list);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    @Override
    public RspPage<RspOfferList> offerList(ReqOfferList reqOfferList) {
        RspPage<RspOfferList> rspPage=new RspPage<>();
        Page page = PageHelper.startPage(reqOfferList.getPage_num(),reqOfferList.getPage_size(),true);
        //商品id 商品名称 创建时间 上架时间 销量 --佣金类型  佣金金额 佣金比例 规格类型
        List<RspOfferList> lists=offerSpecMapper.offerList(reqOfferList);
        for(RspOfferList list:lists){
            if (list.getType()==1){//多规格 图片 单价 库存 佣金
                List<RspNormsInfo> normsInfos=offerSpecMapper.normsInfo2(list.getId());
                if(normsInfos!=null){
                    Integer num=0;//库存
                    for(RspNormsInfo n:normsInfos){
                        num=num+n.getOffer_leave();
                        if(n.getIs_default()==1){
                            list.setOffer_price(n.getOffer_price());//设置单价
                            list.setNorms_pic(n.getNorms_pic());//设置图片
                        }
                    }
                    list.setOffer_leave(num);//库存
                }

                //
            }else{//单规格
                //得到  图片 单价 库存 佣金
                RspNormsInfo normsInfo=offerSpecMapper.normsInfo(list.getId());
                if (normsInfo!=null){
                    list.setOffer_price(normsInfo.getOffer_price());//设置单价
                    list.setNorms_pic(normsInfo.getNorms_pic());//设置图片
                    list.setOffer_leave(normsInfo.getOffer_leave());//库存
                }
            }

        }
        rspPage.setData_list(lists);
        rspPage.setTotal_num(page.getTotal());
        rspPage.setPage_num(page.getPageNum());
        rspPage.setPage_size(page.getPageSize());
        return rspPage;
    }

    @Transactional
    @Override
    public void offerAdd(ReqAddOffer reqAddOffer) {
        //添加商品
        OfferSpec offerSpec=new OfferSpec();
        offerSpec.setId(UuidUtil.getRandomUuid()); //id
        offerSpec.setCode(UuidUtil.getRandomUuid());//商品编号
        offerSpec.setOfferName(reqAddOffer.getName());//名字
        offerSpec.setCreateTime(new Date());//创建时间
        offerSpec.setEnterpriseId(reqAddOffer.getEnterprise_id());//企业id
        offerSpec.setStatus(reqAddOffer.getStatus()); //是否上架
        if (reqAddOffer.getStatus()==1){
            offerSpec.setOnsaleTime(new Date());//上架时间
        }
        offerSpec.setType(reqAddOffer.getType());//规格类型
        offerSpec.setIsPayOnline(reqAddOffer.getIs_pay_online());//是否显示支付
        offerSpec.setExtractType(reqAddOffer.getExtract_type());//提成方式
        offerSpec.setExtractPer(reqAddOffer.getExtract_per());//提成百分比
        offerSpec.setExtractValue(reqAddOffer.getExtract_value());//提成金额
        offerSpec.setOfferSale(0);//销量
        //添加产品成功后添加产品图片，规格
        if(offerSpecMapper.insertSelective(offerSpec)>0){
            //添加产品图片
            OfferImg offerImg=new OfferImg();
            offerImg.setOfferSpecId(offerSpec.getId());//企业id
            for (RspOfferImg img:reqAddOffer.getImgs()){
                offerImg.setId(UuidUtil.getRandomUuid());//id
                offerImg.setCreateTime(new Date());//创建时间
                offerImg.setOrder(img.getSort());//排序
                offerImg.setType(img.getType());//类型
                offerImg.setImgUrl(img.getUrl());//图片路劲
                offerImg.setStatus(1);//状态
                offerImgMapper.insertSelective(offerImg);
            }
            //添加规格
            OfferNorms offerNorms=new OfferNorms();
            offerNorms.setOfferId(offerSpec.getId());
            for (RspOfferNorms norms:reqAddOffer.getNorms()){
                offerNorms.setId(UuidUtil.getRandomUuid());//id
                offerNorms.setCreateTime(new Date());//创建时间
                offerNorms.setIsDefault(norms.getIs_default());//是否默认
                offerNorms.setName(norms.getName());//规格名字
                offerNorms.setOfferPrice(norms.getPrice());//价格
                offerNorms.setNormsType(norms.getType());//规格类型呢
                offerNorms.setNormsPic(norms.getUrl());//图片路劲
                offerNorms.setOfferLeave(norms.getNum());//库存
                offerNorms.setStatus(1);
                offerNormsMapper.insertSelective(offerNorms);
            }
        }
    }

    @Override
    public ReqEditOffer offerDetail(String id) {
        ReqEditOffer offer =offerSpecMapper.offerDetail(id);
        if (offer!=null){
            offer.setNorms(offerSpecMapper.normsDetail(id));
            offer.setImgs(offerSpecMapper.imgDetail(id));
        }
        return offer;
    }

    @Transactional
    @Override
    public void offerEdit(ReqEditOffer reqEditOffer) {
        //获取原先商品信息
        OfferSpec offerSpecOld=offerSpecMapper.selectByPrimaryKey(reqEditOffer.getId());
        offerSpecOld.setStatus(3);
        offerSpecMapper.updateByPrimaryKeySelective(offerSpecOld);

        //重新添加一条产品记录
        OfferSpec offerSpec=new OfferSpec();
        offerSpec.setId(UuidUtil.getRandomUuid()); //id
        offerSpec.setCode(offerSpecOld.getCode());//商品编号
        offerSpec.setOfferName(reqEditOffer.getName());//名字
        offerSpec.setCreateTime(new Date());//创建时间
        offerSpec.setEnterpriseId(offerSpecOld.getEnterpriseId());//企业id
        offerSpec.setStatus(reqEditOffer.getStatus()); //是否上架
        offerSpec.setOfferSale(offerSpecOld.getOfferSale());//销量
        if (reqEditOffer.getStatus()==1){
            offerSpec.setOnsaleTime(new Date());//上架时间
        }
        offerSpec.setType(reqEditOffer.getType());//规格类型
        offerSpec.setIsPayOnline(reqEditOffer.getIs_pay_online());//是否显示支付
        offerSpec.setExtractType(reqEditOffer.getExtract_type());//提成方式
        offerSpec.setExtractPer(reqEditOffer.getExtract_per());//提成百分比
        offerSpec.setExtractValue(reqEditOffer.getExtract_value());//提成金额
        //添加产品成功后添加产品图片，规格
        if(offerSpecMapper.insertSelective(offerSpec)>0){
            //添加产品图片
            OfferImg offerImg=new OfferImg();
            offerImg.setOfferSpecId(offerSpec.getId());//企业id
            for (RspOfferImg img:reqEditOffer.getImgs()){
                offerImg.setId(UuidUtil.getRandomUuid());//id
                offerImg.setCreateTime(new Date());//创建时间
                offerImg.setOrder(img.getSort());//排序
                offerImg.setType(img.getType());//类型
                offerImg.setImgUrl(img.getUrl());//图片路劲
                offerImg.setStatus(1);//状态
                offerImgMapper.insertSelective(offerImg);
            }
            //添加规格
            OfferNorms offerNorms=new OfferNorms();
            offerNorms.setOfferId(offerSpec.getId());
            for (RspOfferNorms norms:reqEditOffer.getNorms()){
                offerNorms.setId(UuidUtil.getRandomUuid());//id
                offerNorms.setCreateTime(new Date());//创建时间
                offerNorms.setIsDefault(norms.getIs_default());//是否默认
                offerNorms.setName(norms.getName());//规格名字
                offerNorms.setOfferPrice(norms.getPrice());//价格
                offerNorms.setNormsType(norms.getType());//规格类型呢
                offerNorms.setNormsPic(norms.getUrl());//图片路劲
                offerNorms.setOfferLeave(norms.getNum());//库存
                offerNorms.setStatus(1);
                offerNormsMapper.insertSelective(offerNorms);
            }
        }

        /*//编辑商品
        OfferSpec offerSpec=new OfferSpec();
        offerSpec.setId(reqEditOffer.getId());//id
        offerSpec.setOfferName(reqEditOffer.getName());//名字
        offerSpec.setStatus(reqEditOffer.getStatus()); //是否上架
        if (reqEditOffer.getStatus()==0){
            offerSpec.setOnsaleTime(new Date());//上架时间
        }
        offerSpec.setType(reqEditOffer.getType());//规格类型
        offerSpec.setIsPayOnline(reqEditOffer.getIs_pay_online());//是否显示支付
        offerSpec.setExtractType(reqEditOffer.getExtract_type());//提成方式
        offerSpec.setExtractPer(reqEditOffer.getExtract_type());//提成百分比
        offerSpec.setExtractValue(reqEditOffer.getExtract_value());//提成金额
        offerSpecMapper.updateByPrimaryKeySelective(offerSpec);
        //删除原先的图片
        offerSpecMapper.deleteImg(reqEditOffer.getId());
        //插入新的图片
        OfferImg offerImg=new OfferImg();
        offerImg.setOfferSpecId(offerSpec.getId());//企业id
        for (RspOfferImg img:reqEditOffer.getImgs()){
            offerImg.setId(UuidUtil.getRandomUuid());//id
            offerImg.setCreateTime(new Date());//创建时间
            offerImg.setOrder(img.getSort());//排序
            offerImg.setType(img.getType());//类型
            offerImg.setImgUrl(img.getUrl());//图片路劲
            offerImg.setStatus(1);//状态
            offerImgMapper.insertSelective(offerImg);
        }
        //删除原先的规格
        RspDelNorms rspDelNorms=new RspDelNorms();
        rspDelNorms.setOffer_id(reqEditOffer.getId());
        rspDelNorms.setType(reqEditOffer.getType());
        offerSpecMapper.delNorms(rspDelNorms);
        //添加规格
        OfferNorms offerNorms=new OfferNorms();
        offerNorms.setOfferId(offerSpec.getId());
        for (RspOfferNorms norms:reqEditOffer.getNorms()){
            offerNorms.setId(UuidUtil.getRandomUuid());//id
            offerNorms.setCreateTime(new Date());//创建时间
            offerNorms.setIsDefault(norms.getIs_default());//是否默认
            offerNorms.setName(norms.getName());//规格名字
            offerNorms.setOfferPrice(norms.getPrice());//价格
            offerNorms.setNormsType(norms.getType());//规格类型呢
            offerNorms.setNormsPic(norms.getUrl());//图片路劲
            offerNorms.setOfferLeave(norms.getNum());//库存
            offerNorms.setStatus(1);
            offerNormsMapper.insertSelective(offerNorms);
        }*/
    }

    @Override
    public void changeStatus(List<String> ids, int type) {
        ReqChangStatus reqChangStatus=new ReqChangStatus();
        reqChangStatus.setStatus(type);
        reqChangStatus.setOnsale_time(new Date());
        for(String id:ids){
            reqChangStatus.setId(id);
            offerSpecMapper.changeStatus(reqChangStatus);
        }


    }

    @Override
    public RspOffInfo offerInfo(String id,String staffid) {
        RspOffInfo rspOffInfo=offerSpecMapper.offerInfo(id);//商品信息
        if (rspOffInfo!=null){
            rspOffInfo.setImgs(offerSpecMapper.imgInfo(id));//商品图片信息
            RspStaffDiscount2 rspStaffDiscount2=staffMapper.staffDiscount(staffid);
            if (rspStaffDiscount2!=null){//默认规格信息
                rspOffInfo.setStaff_name(rspStaffDiscount2.getStaff_name());
                rspOffInfo.setHead_icon(rspStaffDiscount2.getHead_icon());
                rspOffInfo.setDiscount(rspStaffDiscount2.getDiscount());
            }
        }
        return rspOffInfo;
    }

    /**
     * 获取默认的收货地址
     * @param user_id
     * @return
     */
    @Override
    public RspDefaultAddress getDefaul(String user_id) {
        return userAddressMapper.getDefaul(user_id);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(OfferSpec record) {
        return 0;
    }

    @Override
    public int insertSelective(OfferSpec record) {
        return 0;
    }

    @Override
    public OfferSpec selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(OfferSpec record) {

        return offerSpecMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OfferSpec record) {
        return 0;
    }

    @Override
    public void addShopCart(ReqAddCart reqAddCart) {

        ShoppingCart shoppingCart1=shoppingCartMapper.getcount(reqAddCart.getOffer_id(),reqAddCart.getNorms_id(),reqAddCart.getUser_id());
        if (shoppingCart1==null){
            ShoppingCart shoppingCart=new ShoppingCart();
            shoppingCart.setUserId(reqAddCart.getUser_id());
            shoppingCart.setStaffId(reqAddCart.getStaff_id());
            shoppingCart.setCreateTime(new Date());
            shoppingCart.setId(UuidUtil.getRandomUuid());
            shoppingCart.setNormsId(reqAddCart.getNorms_id());
            shoppingCart.setOfferId(reqAddCart.getOffer_id());
            shoppingCart.setNum(reqAddCart.getOffer_num());
            shoppingCart.setStatus(1);
            shoppingCartMapper.insertSelective(shoppingCart);
        }else{
            Integer num=shoppingCart1.getNum()+reqAddCart.getOffer_num();
            shoppingCart1.setNum(num);
            shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart1);
        }
    }

    /**
     * 修改订单状态为已收货
     * @param id
     */
    @Override
    public void updateOrderStatus(String id) {
        Order order = new Order();
        order.setId(id);
        order.setStatus(6);
        orderMapper.updateByPrimaryKeySelective(order);
    }
}
