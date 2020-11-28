package com.vma.push.business.controller.company;

import com.vma.push.business.dto.req.*;
import com.vma.push.business.dto.req.website.ReqWebSite;
import com.vma.push.business.dto.rsp.*;
import com.vma.push.business.entity.OfferSpec;
import com.vma.push.business.entity.Staff;
import com.vma.push.business.service.*;
import com.vma.push.business.util.GsonUtil;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huxiangqiang on 2018/7/13.
 */
@RestController(value = "miniManageController")
@RequestMapping("/V1.0")
@Api(value = "企业后台", description = "小程序管理", tags = {"miniManageController"})
public class miniManageController {

    private Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    private IWebsiteService websiteService;
    @Autowired
    private IStaffInfoService staffInfoService;
    @Autowired
    private IProductService productService;
    @Autowired
    private ICircleCompanyService circleCompanyService;
    @Autowired
    private ICircleCommentLogService circleCommentLogService;

    @ApiOperation(value = "根据enterprise_id查询",notes = "查询")
    @RequestMapping(value = "/company/mini/website",method = RequestMethod.GET)
    public List<RspWebsite> findWebsiteByEnterprise(HttpServletRequest request){
        String id = UserDataUtil.getCustomId(request);
        List<RspWebsite> websiteList = websiteService.findWebsiteByEnterprise(id);
        for(int i=0;i<websiteList.size();i++){
            if(websiteList.get(i).getType()==4){
                Map<String,Object> textContent = GsonUtil.fromJson(websiteList.get(i).getText_content(), Map.class);
                Map<String,Object> dataContent =  GsonUtil.fromJson(GsonUtil.toJson(textContent.get("data")), Map.class);
                Map<String,Object> employeeContent =  GsonUtil.fromJson(GsonUtil.toJson(dataContent.get("employee")), Map.class);
                List valueContent = GsonUtil.fromJson(GsonUtil.toJson(employeeContent.get("value")), List.class);
                List<Staff> staffList = new ArrayList<Staff>();
                for(int j=0;j<valueContent.size();j++){
                    Staff staff = new Staff();
                    staff = staffInfoService.selectByPrimaryKey(GsonUtil.fromJson(GsonUtil.toJson(valueContent.get(j)), Staff.class).getId());
                    staffList.add(staff);
                }
                employeeContent.put("value",staffList);
                dataContent.put("employee",employeeContent);
                textContent.put("data",dataContent);
                websiteList.get(i).setText_content(GsonUtil.toJson(textContent));
            }
        }
        return websiteList;
    }


    @ApiOperation(value = "官网管理",notes = "官网管理")
    @RequestMapping(value = "/company/mini/website",method = RequestMethod.POST)
    @ApiImplicitParam(value = "public class ReqWebSite {\n" +
            "    String type;\n" +
            "    String text_content;\n"+
            "    String config;\n" +
            "}",name = "reqWebSites",dataType ="List<ReqWebSite>",required = true)
    public void website(@RequestBody List<ReqWebSite> reqWebSites, HttpServletRequest request){
        LOG.info("官网模版参数");
        String enterpriseId = UserDataUtil.getCustomId(request);
        websiteService.add(reqWebSites,enterpriseId);

    }

    /**
     * 模糊查询(包含分页列表)
     * @param pageSelect
     * @return
     */
    @ApiOperation(value = "条件查询列表", notes = "条件查询列表")
    @RequestMapping(value = "/company/mini/product/list", method = RequestMethod.GET)
    @ApiImplicitParam(name = "list",value = "查看产品列表",dataType = "lisy")
    public RspPage<RepAllProduct> findProductBySelect(@RequestBody PageSelect pageSelect , HttpServletRequest request){
        pageSelect.setEnterprise_id(UserDataUtil.getCustomId(request));
        RspPage<RepAllProduct> repAllProductRspPage = productService.findProductBySelect(pageSelect);
        return repAllProductRspPage;
    }

    /**
     * 根据id查看产品
     * @param id  产品id
     * @return
     */
    @ApiOperation(value = "查看产品", notes = "查看产品")
    @RequestMapping(value = "/company/mini/product/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "查看产品参数",paramType = "path",required = true,dataType = "String")
    public RepSpecAndImg getProductById(@PathVariable String id, HttpServletRequest request){
        LOG.info("根据id查询产品数量");
        String enterprise_id = UserDataUtil.getCustomId(request);
        RepSpecAndImg product =  productService.selectProductById(id,enterprise_id);
        return product;
    }


//
    /**
     * 添加产品
     * @param
     */
    @ApiOperation(value = "添加产品", notes = "添加产品")
    @RequestMapping(value = "/company/mini/product", method = RequestMethod.POST)
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
    @RequestMapping(value = "/company/mini/product",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateProduct",required = true,value = "修改产品",dataType = "ReqUpdateProduct")
    public void updateProduct(@RequestBody ReqUpdateProduct reqUpdateProduct){
        LOG.info("修改指定产品");
        productService.updateProduct(reqUpdateProduct);
    }

    @ApiOperation(value = "修改状态",notes = "修改状态")
    @RequestMapping(value = "/company/mini/product/status",method = RequestMethod.PUT)
    @ApiImplicitParam(name = "reqUpdateStatus",required = true,value = "修改状态",dataType = "ReqUpdateStatus")
    public void updateStatus(@RequestBody ReqUpdateStatus reqUpdateStatus){
        OfferSpec offerSpec = new OfferSpec();
        offerSpec.setId(reqUpdateStatus.getId());
        offerSpec.setStatus(reqUpdateStatus.getStatus());
        productService.updateByPrimaryKeySelective(offerSpec);

    }

    @ApiOperation(value = "查找朋友圈", notes = "查找朋友圈")
    @RequestMapping(value = "/company/mini/circle/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqSelectCircle",value = "查询条件 需要传session",required = true,dataType = "ReqSelectCircle")
    public RspPage<RspAllCircle> CirclePage(@RequestBody ReqSelectCircle reqSelectCircle, HttpServletRequest request){
        LOG.info("获取所有朋友圈");
        reqSelectCircle.setEnterprise_id(UserDataUtil.getCustomId(request));
        return circleCompanyService.circlePage(reqSelectCircle);
        //return circleCompanyService.getAll(page.getPage_num(),page.getPage_size());
    }

    @ApiOperation(value = "公司动态的评论数据", notes = "公司动态的评论数据")
    @RequestMapping(value = "/company/mini/circle/comment", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqCircleComment",value = "查询条件 需要传session",required = true,dataType = "ReqCircleComment")
    public RspCirComFormCompanyPage CircleCommentPage(@RequestBody ReqCircleComment reqCircleComment, HttpServletRequest request){
        LOG.info("公司动态的评论数据");
        reqCircleComment.setEmployee_id(UserDataUtil.getCustomId(request));
        return circleCommentLogService.getAllComment(reqCircleComment);
        //return circleCompanyService.getAll(page.getPage_num(),page.getPage_size());
    }

    @ApiOperation(value = "获取朋友圈详情",notes = "获取朋友圈详情")
    @RequestMapping(value = "/company/mini/circle/{id}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "id",value = "朋友圈id",required = true,dataType = "String",paramType = "path")
    public RspCircleDetail circleDetail(@PathVariable("id")  String id){
        LOG.info("获取朋友圈详情");
        return circleCompanyService.circleDetail(id);
    }

    @ApiOperation(value = "删除朋友圈",notes = "删除朋友圈")
    @RequestMapping(value = "/company/mini/circle/{id}",method = RequestMethod.DELETE)
    @ApiImplicitParam(name = "id",value = "朋友圈id",required = true,dataType = "String",paramType = "path")
    //不区分企业跟个人
    public void delCircle(@PathVariable("id")  String id){
        LOG.info("删除朋友圈");
        circleCompanyService.delCircle(id);
    }

    @ApiOperation(value = "发布公司朋友圈",notes = "发布公司朋友圈")
    @RequestMapping(value = "/company/mini/circle",method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqAddCircle",value = "发布公司朋友圈 需要传session_id",required = true,dataType = "ReqAddCircle")
    public void sendCircle(@RequestBody ReqAddCircle reqAddCircle, HttpServletRequest request){
        LOG.info("发布公司朋友圈");
        reqAddCircle.setEmployee_id(UserDataUtil.getCustomId(request));//UserDataUtil.getStaffId(request);
        reqAddCircle.setEnterprise_id(UserDataUtil.getCustomId(request));
        circleCompanyService.sendCircle(reqAddCircle,1);
    }
    @ApiOperation(value = "删除评论",notes = "删除评论")
    @RequestMapping(value = "/company/mini/Comment/{id}",method = RequestMethod.DELETE)
    @ApiImplicitParam(name = "id",value = "评论记录id",required = true,dataType = "String",paramType = "path")
    //不去分企业跟个人
    public void delComment(@PathVariable("id")  String id){
        LOG.info("删除评论");
        circleCommentLogService.delComment(id);
    }

}
