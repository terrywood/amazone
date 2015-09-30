package com.gt.bmf.web.controller.admin;

import com.gt.bmf.BmfConstants;
import com.gt.bmf.common.page.PageList;
import com.gt.bmf.pojo.Order;
import com.gt.bmf.service.OrderService;
import com.gt.bmf.service.ProductService;
import com.gt.bmf.util.NUIResponseUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


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
    public String orderList(String content,
                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                            @RequestParam Map<String,Object> params,
                            HttpServletRequest request) {

        System.out.println("params->"+params);
        PageList<Order> pageList = orderService.findPageData(pageNum, pageSize);

        request.setAttribute("pageList", pageList);
        request.setAttribute("products", productService.findAll());
        return "/admin/order/list";
    }


}
