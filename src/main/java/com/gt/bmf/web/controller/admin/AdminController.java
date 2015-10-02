package com.gt.bmf.web.controller.admin;

import com.gt.bmf.BmfConstants;
import com.gt.bmf.common.page.PageList;
import com.gt.bmf.pojo.Order;
import com.gt.bmf.pojo.OrderItem;
import com.gt.bmf.service.OrderService;
import com.gt.bmf.service.ProductService;
import com.gt.bmf.util.NUIResponseUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
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
    public String orderList(
                            //@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pager.offset", defaultValue = "0") Integer offset,
                            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                            @RequestParam Map<String,String> params,
                            HttpServletRequest request) {


        Integer pageNum = offset/pageSize +1;
        PageList<Order> pageList = orderService.findPageData(params,pageNum, pageSize);

        request.setAttribute("pageList", pageList);
        request.setAttribute("params", params);
        request.setAttribute("products", productService.findAll());
        return "/admin/order/list";
    }


    @RequestMapping(value = "/myip", method = RequestMethod.GET)
    public String myip( HttpServletRequest request) {
        return "/admin/order/myip";
    }
    @RequestMapping(value = "/orderDetail", method = RequestMethod.GET)
    public String orderDetail(
            @RequestParam(value = "orderId", defaultValue = "") String orderId,
            HttpServletRequest request) {
        List<OrderItem> list = orderService.findItemByOrderId(orderId);
        request.setAttribute("list",list);
        return "/admin/order/orderDetail";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkout1(
                            HttpServletRequest request) {
        return "/admin/order/checkout";
    }


    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkout(
                            @RequestParam(value = "cookie", defaultValue = "") String cookie,
                            @RequestParam(value = "page", defaultValue = "1") Integer page,

                            HttpServletRequest request) {

        String[] urls =new String[]{
                "https://www.amazon.co.jp/gp/css/order-history/ref=nav__gno_yam_yrdrs"
                ,
                "https://www.amazon.co.jp/gp/your-account/order-history/ref=oh_aui_pagination_1_2?ie=UTF8&orderFilter=months-6&search=&startIndex=10"
                ,
                "https://www.amazon.co.jp/gp/your-account/order-history/ref=oh_aui_pagination_2_3?ie=UTF8&orderFilter=months-6&search=&startIndex=20"
                ,
                "https://www.amazon.co.jp/gp/your-account/order-history/ref=oh_aui_pagination_3_4?ie=UTF8&orderFilter=months-6&search=&startIndex=30"
                ,
                "https://www.amazon.co.jp/gp/your-account/order-history/ref=oh_aui_pagination_4_5?ie=UTF8&orderFilter=months-6&search=&startIndex=40"
                ,
                "https://www.amazon.co.jp/gp/your-account/order-history/ref=oh_aui_pagination_5_6?ie=UTF8&orderFilter=months-6&search=&startIndex=50"
        };

       // String cookie ="skin=noskin; x-wl-uid=1L+/QeZkey77UYHyTGjpDdZJKOxJQclMVk6P3X6ooFaWPUdL9X/15VkB+ZnR8vGoP7my+q77OkWXs9lyxxdVID8bdnHIhVmfwgOBpqSCl8aLuCbt7sv7ygmdZFvxYdIB8ssJznDJBpM8=; session-id-time=2082726001l; session-id=375-9577787-6026354; csm-hit=s-V5ANMGR9G8JV6DDPFMJ6|1443687049033; ubid-acbjp=378-2988962-1779238; session-token=\"MQ3bkbqE1/9RACQ8+eYtshbRHDGV1N6tlhFVz5R7vFpG2PlbbCJXmdNlMv13unMscBTV5o6X66x95wLuFisA4oEReMu0nG2ioTdcrB/6KVy6k2tQbQa3CFpYqFyWkXMUeXY6TTL3VDJam3YOpwAPixjezihhYMVej/kfbwvfHnck2chYS1i3S1zlaGuV8NEgu7brw1DqRz0ufSirTFUjOw5YfaeE9NIuQ4qnxFfdtbU=\"; x-acbjp=\"9CDzMc9tONdF1VTwpiR75lUBVySEdh70@xlnPOnrYd2Fd5B1bYBZ2yMa9kpRmlt2\"; at-acbjp=\"5|3PrQg9iHyNMyJ0rJ80pwWEQsNVArBHvxlw+n7UpzDbtIENispjtrCbjoseLSFRxp1y21YIXnq9VnOsiVwVdeR/4IKS8K2orejtQ/9OYiEg8368FbuD5ywRIILJz3rBzNisJgbk64LXp0HEBc0GYIWn+OJNwTY9cJd/FipUpqANOEWOI9xEw+aoZJuzmKokIf9295h2Rmm+rNyCFtI5E1U6DnQ60Dn5z4ef7OZMzvtpfDnFqSy+dx/gySYMmnJrwIYh0Xc9ZDJ1vu50KMhr2moXEvPrYYqzRIW4/nfJO1ln0=\"; sess-at-acbjp=\"ny6oXB+BbMlkDuEMM51ZrMta+hT5lCnASce6JRtNzt8=\"; lc-acbjp=en_JP; s_cc=true; s_nr=1443682664674-New; s_vnum=1875682640830%26vn%3D1; s_dslv=1443682664689; s_sq=%5B%5BB%5D%5D; s_ppv=41; a-ogbcbff=1";

        int i =1;
        for(String url :urls)    {
            if(i>page)break;
            try {
                orderService.loadOrders(cookie,url);
            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            i++;
        }
        return "redirect:orderList.do";
    }


}
