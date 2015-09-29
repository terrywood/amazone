package com.gt.bmf.web.controller.admin;

import com.gt.bmf.BmfConstants;
import com.gt.bmf.service.OrderService;
import com.gt.bmf.service.ProductService;
import com.gt.bmf.util.NUIResponseUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Value("${jsp.admin.common.nuiresponse}")
    private String jspCommonNuiResponse;

    @Autowired
    protected OrderService orderService;
    @Autowired
    protected ProductService productService;



    @RequestMapping("/orderList")
    public String orderList(String content, HttpServletRequest request) {
        request.setAttribute("products",productService.findAll());
        return "/admin/order/list";
    }


}
