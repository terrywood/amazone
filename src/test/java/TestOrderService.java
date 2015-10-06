import com.gt.bmf.service.GfQueryLogService;
import com.gt.bmf.service.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;


public class TestOrderService {

    private static OrderService service;


    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/bmf_applicationContext.xml");
        service = (OrderService) ctx.getBean("orderService");

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

        String cookie ="skin=noskin; lc-acbjp=en_JP; x-wl-uid=1WT6ELOKhe/JdxnbpPBisWZcTC+YxHG9W5lxuBTgfal/dzPVyM21fRqtcMC5f0Lyp4svTBCs8BBbKNcE1HRdJiQBiKmSnpDP5hJA8oUkwPXugDJ5f9L4gcoaMRwPWAN9Mev5jb/i7GDo=; csm-hit=s-H38FVJ1PJA7WM2VSQGEE|1444145728311; a-ogbcbff=1; session-token=\"3H3FCuAHhm3hmRbXFJfIX3K9dQBWyv6NstRdHV0SAqx5bOwOF8M/6X10fjAlpzUwZtTe42D2fh7sTnMPs0Hu3TbksAwo3yUz6rPnbAwB/Vfps8pb5LjLR1iTYZL4mIC0DfPKgsoi5nktZJ9M3ddlcGavEudjRT7aEdwYX3LF84tWtEghOZSJGvikNvF4Phx7mavB2sxXoPLbDJCjVjpbGpcWbYW06A6jug4iWjqnd4t2FLjjTXS1iYGxDGVwcpDQ9pkMlU44FYWSlkUodkzipw==\"; x-acbjp=pQzlpSGVLbZ2tPGESArAGF4MQrKP0iXy1sH4RHeQ9h1uSuEcszFDfAtjCxXNaj1D; at-acbjp=\"5|BFc9sDzI8d/ts+p9sk4dAN/wYCMm7C3kH/aiTRiNgDDcbsMAelSE0jppt7ev7EbLQLWhLPbSEOdFQzaEYNUk3IRyAqDa3paxlInMVFo7iyAofMC3jNsKBYIF920r4JKYf0NuiasKhv0RgWhBAMi5zOXmZyXee0QYyV0C/v1lFuTQfjV0slu1WwIf+wI87LnmuSjGsIKd5gWWtgzDIKd+FYZw8PnKCDjaZqgGh3wFS6TE0J7qwona6CL3LxVVmNg/19h88O0eJCxFSX8bh5xK/x28VoZySqFy3ttDORKXcoU=\"; sess-at-acbjp=\"K9JP/mTY9Uzc6AtgnoBq2uVtKhPygS4jMOX1WYYpmBQ=\"; ubid-acbjp=378-5163145-6836369; session-id-time=2082726001l; session-id=378-6234317-2784502";

        for(String url :urls)
        service.loadOrders(cookie,url);


       // service.loadOrders(cookie,"https://www.amazon.co.jp/gp/css/order-history/ref=nav__gno_yam_yrdrs");


 /*       service.loadOneOrder(cookie,"250-6151182-0937433");
        Map<String,String> params =new HashMap<String,String>();*/
      //  service.findOrderItemPageData(params,1,10);
        System.exit(0);

	}

    public void testCreateDB(){


    }

}
