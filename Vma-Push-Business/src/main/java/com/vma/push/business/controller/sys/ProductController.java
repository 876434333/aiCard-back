package com.vma.push.business.controller.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dto.RspProdPage;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.dto.req.*;
import com.vma.push.business.entity.OfferSpec;
import com.vma.push.business.service.IProductService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/4/23 17:12
 */
@RestController
@RequestMapping("/v1.0")
@Api(value = "产品管理API", description = "管理后台--产品管理", tags = {"ProductController"})
public class ProductController  extends ControllerExceptionHandler {
    private Logger LOG = Logger.getLogger(this.getClass());
    @Autowired
    private IProductService productService;


    /**
     * 模糊查询(包含分页列表)
     * @param pageSelect
     * @return
     */
    @ApiOperation(value = "条件查询列表", notes = "条件查询列表")
    @RequestMapping(value = "/product/pageSelect", method = RequestMethod.POST)
    @ApiImplicitParam(name = "pageSelect",value = "查看产品列表",dataType = "PageSelect")
    public RspPage<RepAllProduct> findProductBySelect(@RequestBody PageSelect pageSelect , HttpServletRequest request){
        pageSelect.setEnterprise_id(UserDataUtil.getEnterpriseId(request));
        RspPage<RepAllProduct> repAllProductRspPage = productService.findProductBySelect(pageSelect);
        return repAllProductRspPage ;
    }

    /**
     * 通过企业id 获取用户的发布的产品列表  分页
     * @param reqProductPage
     * @return
     */
    @ApiOperation(value = "获取用户的所在企业发布的产品列表", notes = "获取用户的所在发布的产品列表")
    @RequestMapping(value = "/product/staffProduct", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqProductPage",value = "分页信息",dataType = "ReqProductPage")
    public RspProdPage staffProduct(@RequestBody ReqProductPage reqProductPage , HttpServletRequest request){
        reqProductPage.setEnterprise_id(UserDataUtil.getEnterpriseId(request));//UserDataUtil.getEnterpriseId(request);
        RspProdPage repAllProductRspPage = productService.enterpriseProduct(reqProductPage);
        return repAllProductRspPage ;
    }
    /**
     * 根据id查看产品
     * @param id  产品id
     * @return
     */
    @ApiOperation(value = "查看产品", notes = "查看产品")
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "查看产品参数",paramType = "path",required = true,dataType = "String")
    public RepSpecAndImg getProductById(@PathVariable String id,HttpServletRequest request){
        LOG.info("根据id查询产品数量");
        String enterprise_id = UserDataUtil.getEnterpriseId(request);
        RepSpecAndImg product =  productService.selectProductById(id,enterprise_id);
        return product;
    }


//
    /**
     * 添加产品
     * @param
     */
    @ApiOperation(value = "添加产品", notes = "添加产品")
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqSpecAndImg",value = "添加产品",required = true,dataType = "ReqSpecAndImg")
    public RspProductId addProduct(@RequestBody ReqSpecAndImg reqSpecAndImg, HttpServletRequest request){
        LOG.info("添加产品到数据库");
       return productService.addProduct(reqSpecAndImg,request);
    }

    /**
     * 修改产品
     * @param
     */
   @ApiOperation(value = "修改产品",notes = "修改产品")
   @RequestMapping(value = "/product",method = RequestMethod.PUT)
   @ApiImplicitParam(name = "reqUpdateProduct",required = true,value = "修改产品",dataType = "ReqUpdateProduct")
   public void updateProduct(@RequestBody ReqUpdateProduct reqUpdateProduct){
     LOG.info("修改指定产品");
     productService.updateProduct(reqUpdateProduct);
   }

    @ApiOperation(value = "修改状态",notes = "修改状态")
    @RequestMapping(value = "/product/status",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateStatus",required = true,value = "修改状态",dataType = "ReqUpdateStatus")
     public void updateStatus(@RequestBody ReqUpdateStatus reqUpdateStatus){
        OfferSpec offerSpec = new OfferSpec();
        offerSpec.setId(reqUpdateStatus.getId());
        offerSpec.setStatus(reqUpdateStatus.getStatus());
        productService.updateByPrimaryKeySelective(offerSpec);

     }

}
