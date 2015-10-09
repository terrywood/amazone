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

        String cookie ="x-wl-uid=1JjmltevWFXgMv9jtPPOpkiD9QIYaWfz7VEWjZ7mluMmeM2x4jTfWhWKqdRyyyuBQwpt67gf4K+njg+Wvznv9QVRIjwZFcofkGEAxhcN7BIX7mbqF4DLy+azhcZ+DINzfBwDlR/d6w1k=; session-id-time=2082726001l; session-id=377-5397409-7673260; ubid-acbjp=375-4021783-7376212; session-token=\"zmK9NMZHWh19lL6J3KjSuQ/K4ln8krN6gE1/h9hr+v7aqTc5E2jHH1JE6L3qsZmsf80Rd7SOPCX8S8NjIq1rkji8Hh2YDPG/pSaSo+1+dZHoTzCS2w6IZh01sw61mBJtOThUkAS7yWoxuaFLVnSZUmcBBFSHZHAtYJvFMWIxXi5zrrKjPoRkNXwVFOYF6razXmb9v+OoBjSjx9StZKMkI9d6om3W/9bPR1wKF7QKjLOi9mfDe57lvnL0mlawnfw7mVAV66vqvfO6QkjExNsebg==\"; csm-hit=49QEEZERQ97KJ76TK1JW+s-VYC77R1MREGWTT8A5WDQ|1444401516617; x-acbjp=3tEqZWfwOSOUJGQRhUDY60fLalNr66THAGPdgck0I1lMNgvcnXAa6uKW1k9wXuH4; at-acbjp=5|xGCfoLXjnXKiq3QIeyP3WdoRrZn6p+cY2RV4zBrE6qMM5Wii4umJM0PVe0oUS7QyFrn42vPlpEp5fa/4fh1wjw1IiCd4LsxqGQu7RTkBROpi/pIU09rGjSlHhzIM2gmq9BL7eLUC56Bgv2nYSJZfsJfs99x1kxB+vjNNhVji7pme2YPiO0ekgfpwwmhwGvinRTnkR9cTt6nJu9ngcPb+5l4D9nl9iV0oCZrzjAbVNKEc3TsB4NClV7KSD1T2iQyVHzgqUqcFXqVqN4fLtkLLnMA4Ljqp3RN7; s_nr=1444380091217-New; s_vnum=1876380071936%26vn%3D1; s_dslv=1444380091243; lc-acbjp=en_JP; sess-at-acbjp=\"wbHFgNMNk1Ysw/Dzsfg9oCZ8Z+rRkb+VAa0+j6xG8jU=\"";

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
