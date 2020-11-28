package com.vma.push.business.controller.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dto.req.Page;
import com.vma.push.business.dto.req.PageSelect;
import com.vma.push.business.dto.req.ReqOrderSelect;
import com.vma.push.business.dto.rsp.RepAllProduct;
import com.vma.push.business.dto.rsp.RspOrder;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.entity.Order;
import com.vma.push.business.service.IOrderService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ChenXiaoBin
 * 2018/5/2 21:25
 */
@RestController
@RequestMapping("/v1.0")
@Api(value = "订单管理API", description = "管理后台--订单管理", tags = {"OrderController"})
public class OrderController  extends ControllerExceptionHandler {
    @Autowired
    private IOrderService orderService;

    /**
     * 模糊条件查询
     * @param
     * @return
     */
    @ApiOperation(value = "条件查询列表", notes = "条件查询列表")
    @RequestMapping(value = "/order/page", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqOrderSelect",value = "查看订单列表",dataType = "ReqOrderSelect")
    public RspPage<RspOrder> findProductBySelect(@RequestBody ReqOrderSelect reqOrderSelect,HttpServletRequest request){
        String Enterprise_id = UserDataUtil.getEnterpriseId(request);
        reqOrderSelect.setEnterprise_id(Enterprise_id);
        return orderService.findOrderBySelect(reqOrderSelect);
    }
}
