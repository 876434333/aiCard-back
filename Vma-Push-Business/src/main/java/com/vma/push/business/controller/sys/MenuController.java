package com.vma.push.business.controller.sys;

import com.vma.push.business.common.ControllerExceptionHandler;
import com.vma.push.business.dto.rsp.menu.RspMenu;
import com.vma.push.business.service.IMenuService;
import com.vma.push.business.util.UserDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by chenzui on 2018/5/8.
 */
@RestController
@RequestMapping("/v1.0")
@Api(value = "菜单", description = "菜单", tags = {"MenuController"})
public class MenuController extends ControllerExceptionHandler{
    private Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    private IMenuService menuService;

    @ApiOperation(value = "查询菜单",notes = "查询菜单")
    @RequestMapping(value = "/sys/menu/list",method = RequestMethod.GET)
    public List<RspMenu> list(HttpServletRequest request){
        LOG.info("查询菜单");
        String staffId = UserDataUtil.getStaffId(request);
        return menuService.list(staffId);
    }
}
