import com.gt.bmf.pojo.OrderItem;
import com.gt.bmf.service.GfQueryLogService;
import com.gt.bmf.service.OrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;


public class TestSqliteService {

    private static OrderService service;

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/bmf_applicationContext.xml");
        service = (OrderService) ctx.getBean("orderService");
        String s1="$(\"#zfbaccount\").val(\"rikyse@163.com\");$(\"#zfbname\").val(\"谢绮芬\");$(\"input[type=checkbox]\").attr(\"checked\",true);var tracks =['$TRACKS$'];";
        String script="$(\"#zfbaccount\").val(\"rikyse@163.com\");$(\"#zfbname\").val(\"谢绮芬\");\n" +
                "var tracks =['$TRACKS$'];\n" +
                "for(var i=0;i<tracks.length;i++){\n" +
                "\t$('input[name=\"productexpno[]\"]').eq(i).val(tracks[i]);\n" +
                "\t$('select[name=goodsid'+i+']').find(\"option:contains('$ALIAS$')\").prop(\"selected\",true);\n" +
                "\t$(\"input[type=checkbox]\").eq(i).prop(\"checked\",true);\n" +
                "}\n";
        List<String> ts =new ArrayList<String>();
        List<OrderItem> list=  service.findItemByOrderId("503-6566913-3528622");
        String alias  = list.get(0).getProduct().getAliasName();
        script = StringUtils.replace(script,"$TRACKS$",script)  ;

        for(int i=0;i<list.size();i++){
            OrderItem obj =list.get(i);
            String trackId = obj.getTrackId();
            ts.add(trackId);
            if(ts.size()>=4){
                String ss =StringUtils.join(ts,"','");
                System.out.println(ss);

                ss = StringUtils.replace(script,"$TRACKS$",ss)  ;
                ts =new ArrayList<String>();
            }
        }
        String ss =StringUtils.join(ts,"','");
        System.out.println(ss);

	}

    public void testCreateDB(){


    }

}
