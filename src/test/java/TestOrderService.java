import com.gt.bmf.service.GfQueryLogService;
import com.gt.bmf.service.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestOrderService {

    private static OrderService service;


    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/bmf_applicationContext.xml");
        service = (OrderService) ctx.getBean("orderService");

        String cookie ="s_vnum=1874392441801%26vn%3D1; lc-acbjp=en_JP; s_nr=1442392748846-New; s_dslv=1442392748849; x-wl-uid=1eMkZN799eSp+TinbTcUVpov48zABWIV0zDBMiUgbZBA9Tx9yX/lLhcTtDRUbO5rgTu9ED2OoiMAMXgvC9W+rDlmy5q8oSOxafCh4M/Lw1NYiirfE/NQ8va4OQQ9Aj8gwLvReFkIRDus=; session-token=\"LLzBUwErQwMfUIlZquZPQzMaC3puzrE0xz/g34m6nB/a0drs4vEPe0HoNvwfmJTpuWugExygNojZbrcTSz3PzkOPDp3ixUmoyEbr0hsvsgBUIywPNwAymsyKUMkxb/RuvmMNYYhkqMsZLTOogmRg96mhmJjWoua6g1qaRH5Uk+99RI6Tzr2jUGP5OM9S1/KGySu8pR3OE/ucZIRt6ftM5Z1Qrd07lu29pL5mfeBzEPC1rw/HtP00OxmOyHux69bZeXW9s6SyF1d6/c9b/mLWCw==\"; x-acbjp=\"XwQHrfQ4mqKjbpT12?j2dKoEGFoewwCCd7qJbFPwjpc26kW7QQiV?ZNbu6eR3Fvo\"; at-acbjp=\"5|dxETDSuISSHCD5K1rpys6NehHWoBhrznWN2MvNVBfThv77MZxV3YI+bUJ6g/QihCBj7I0IcWygbK9/6ucR2cjlkOKmy+kgGt6KePFunLAlbukPxZ9L9fLEfsNLgdLnhpjhxmgt95AAQUTIbDXMCh6wmgwfuAb8owqRGgpb7QpS4yhfLJjs0GnC4BP3zjviRld4DkdvEge34pqX+LlEIeyaHdSvkFEfkX+8ddvZ94pWoCYirM3XTEeQZZAfNjlhZyVA703quCNHg9xIm2HgWqROsHxELxb8J74ZGWLOZyv1E=\"; sess-at-acbjp=\"R0ExPAFeCNjXuqe0rpKGgRWbOTRE1pzpYiriUIiigxA=\"; skin=noskin; csm-hit=M5VHEJAPDM61Z8EGGSKZ+s-M5VHEJAPDM61Z8EGGSKZ|1442916337282; ubid-acbjp=375-5374284-2108249; session-id-time=2082726001l; session-id=376-7550029-1988420";
        service.loadOrders(cookie,null);

        System.exit(0);

	}

    public void testCreateDB(){


    }

}
