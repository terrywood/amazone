import com.alibaba.druid.support.json.JSONUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by noside on 15-8-15.
 */
public class QuickStart {

    public static void main(String[] args) throws Exception {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
        CloseableHttpResponse response = null;
        try {
            String url = "https://www.amazon.co.jp/gp/your-account/order-history?orderFilter=last30";
            String gfCookie = "skin=noskin; x-wl-uid=1r1xOPk9AcZ1SzoBDeYjDAM0/ude/GFk0sP9Tv+86XOqFg9zcSZ3BKz7alTsVb2r2UMc3FQBCRMgeBoeOq7dMpQ4X+WGbBsxjUMj4ll/jxFxv2WSWwxXUyTS28Acnjpn1vXvPvbikl0k=; lc-acbjp=en_JP; s_cc=true; s_vnum=1874756952256%26vn%3D1; s_ppv=51; s_nr=1442756954697-New; s_dslv=1442756954701; s_sq=%5B%5BB%5D%5D; session-token=\"s10Kbcm3w4m91PRrIL7E/c8D5bEBA8dPGyc2CibZDD31rnxwG9eKRwjm7USbyP0n9JD4XBwNAm5akHZCrr7EtBNMswp0indCzIhzsAOM3pkAAPdJgrdX/AiLOJW+F0njxjW7ok2It+1ZDfm1xqVvPPTHTuRZVb/0wmc3kNSkNwPsK90ETY89kE8rXOdeGwdSPqxnvWg+37iOh3ReGAtEJfK90Lh2U6lhopAdp3lJSuFuxeerv+qrlP/LdWVkygxhC1kbfbF6ZXJUhsvzdbKcLg==\"; x-acbjp=\"rKjpYm2oxDj8gM2xHKitebahB?gctTPLPmwSlmGIqaU84C6BTDgsK0Vt9FfQNRvw\"; at-acbjp=\"5|c1PLejws5DB7zwBW4TqEjakOYPRnmgFLy4eHkEbDugl3tCVRoueS/pSY4FxaTZ/q0XrIgsZNPd6wPSQgtTUtMaMZ8Tqj28bRESK2xfsBQ7xTUmYHLLRn2ToIFOtJAoBPxbigVyfDVpWfnCoMRYLfnl/du2SZfa1/M7ibWFXyUGcR3ZrnSj9CMSZ5pG72WuB08h5DtoMCK8KmWgbhPvaJtTrcc36EfxmmkE0eA3TfMF36W+DJP+etRu4qdQabE26Lpv2ZfpArq2DgujTH05X78PrRQ0ZYNMlvNo0EFjqh5fg=\"; sess-at-acbjp=\"Q4fe/kSFhbz7rcdy5MQQWDVJLXB2KihS1Z7N+T6rEic=\"; csm-hit=5SQ93TYHE80C1TGESFTE+s-BDHSCB35MDH5VKFV5GV3|1442758788767; ubid-acbjp=377-1637633-7468801; session-id-time=2082726001l; session-id=377-8473977-8134601";
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Cookie", gfCookie);
            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            httpPost.setEntity(entity);
            response = httpclient.execute(httpPost);
            String responseBody = IOUtils.toString(response.getEntity().getContent(), Consts.UTF_8);
            System.out.print(response.getStatusLine().getStatusCode());
            System.out.print(responseBody);

           // paresData(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }


    public static  void paresData(String data){
        Document doc = Jsoup.parse(data);
        Elements ListDiv = doc.getElementsByClass("a-box-group");
        for (Element element :ListDiv) {
            //System.out.println(element.html());
            Elements links = element.getElementsByClass("value");
            String orderData = links.get(0).text().trim();
            String orderId = links.get(3).text().trim();
            System.out.println("orderData->"+orderData);
            System.out.println("orderId->"+orderId);

        }
    }

}
