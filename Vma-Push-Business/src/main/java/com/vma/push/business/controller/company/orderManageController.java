package com.vma.push.business.controller.company;

import com.vma.push.business.dto.req.ReqOrderSelect;
import com.vma.push.business.dto.rsp.RspOrder;
import com.vma.push.business.dto.rsp.RspPage;
import com.vma.push.business.service.IOrderService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by huxiangqiang on 2018/7/13.
 */
@RestController(value = "orderManageController")
@RequestMapping("/V1.0")
@Api(value = "企业后台", description = "订单管理", tags = {"orderManageController"})
public class orderManageController {

    @Autowired
    private IOrderService orderService;

    /**
     * 模糊条件查询
     * @param
     * @return
     */
    @ApiOperation(value = "条件查询列表", notes = "条件查询列表")
    @RequestMapping(value = "/company/order/list", method = RequestMethod.POST)
    @ApiImplicitParam(name = "reqOrderSelect",value = "查看订单列表",dataType = "ReqOrderSelect")
    public RspPage<RspOrder> findProductBySelect(@RequestBody ReqOrderSelect reqOrderSelect, HttpServletRequest request){
        String Enterprise_id = UserDataUtil.getCustomId(request);
        reqOrderSelect.setEnterprise_id(Enterprise_id);
        return orderService.findOrderBySelect(reqOrderSelect);
    }
}
