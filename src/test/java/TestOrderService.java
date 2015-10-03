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

        String cookie ="skin=noskin; x-wl-uid=1L+/QeZkey77UYHyTGjpDdZJKOxJQclMVk6P3X6ooFaWPUdL9X/15VkB+ZnR8vGoP7my+q77OkWXs9lyxxdVID8bdnHIhVmfwgOBpqSCl8aLuCbt7sv7ygmdZFvxYdIB8ssJznDJBpM8=; session-id-time=2082726001l; session-id=375-9577787-6026354; csm-hit=s-V5ANMGR9G8JV6DDPFMJ6|1443687049033; ubid-acbjp=378-2988962-1779238; session-token=\"MQ3bkbqE1/9RACQ8+eYtshbRHDGV1N6tlhFVz5R7vFpG2PlbbCJXmdNlMv13unMscBTV5o6X66x95wLuFisA4oEReMu0nG2ioTdcrB/6KVy6k2tQbQa3CFpYqFyWkXMUeXY6TTL3VDJam3YOpwAPixjezihhYMVej/kfbwvfHnck2chYS1i3S1zlaGuV8NEgu7brw1DqRz0ufSirTFUjOw5YfaeE9NIuQ4qnxFfdtbU=\"; x-acbjp=\"9CDzMc9tONdF1VTwpiR75lUBVySEdh70@xlnPOnrYd2Fd5B1bYBZ2yMa9kpRmlt2\"; at-acbjp=\"5|3PrQg9iHyNMyJ0rJ80pwWEQsNVArBHvxlw+n7UpzDbtIENispjtrCbjoseLSFRxp1y21YIXnq9VnOsiVwVdeR/4IKS8K2orejtQ/9OYiEg8368FbuD5ywRIILJz3rBzNisJgbk64LXp0HEBc0GYIWn+OJNwTY9cJd/FipUpqANOEWOI9xEw+aoZJuzmKokIf9295h2Rmm+rNyCFtI5E1U6DnQ60Dn5z4ef7OZMzvtpfDnFqSy+dx/gySYMmnJrwIYh0Xc9ZDJ1vu50KMhr2moXEvPrYYqzRIW4/nfJO1ln0=\"; sess-at-acbjp=\"ny6oXB+BbMlkDuEMM51ZrMta+hT5lCnASce6JRtNzt8=\"; lc-acbjp=en_JP; s_cc=true; s_nr=1443682664674-New; s_vnum=1875682640830%26vn%3D1; s_dslv=1443682664689; s_sq=%5B%5BB%5D%5D; s_ppv=41; a-ogbcbff=1";

      //  for(String url :urls)
      //  service.loadOrders(cookie,url);
        service.loadOrders(cookie,"https://www.amazon.co.jp/gp/css/order-history/ref=nav__gno_yam_yrdrs");

        Map<String,String> params =new HashMap<String,String>();
      //  service.findOrderItemPageData(params,1,10);
        System.exit(0);

	}

    public void testCreateDB(){


    }

}
