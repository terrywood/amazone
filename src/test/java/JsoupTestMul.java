import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JsoupTestMul {
    static String url="https://www.amazon.co.jp";
    static String gfCookie =  "x-wl-uid=1r7tE5GIv55k9NsPxC39VwZezh8//ivO6V2GOBqh1ZcJ0s4Kh9W1G+PR6S3tvybYK1AbTZ8ebz0ixqrRbw1VCYMgzUTfKfgGKc+XlNE3uw53bxBDeu7t0z+iwnMe+mDCIWXvIBR1CEsU=; session-id-time=2082726001l; session-id=377-4429857-4896621; csm-hit=s-AMCPG2THQEFN719AGY0A|1442768018298; ubid-acbjp=378-7376082-4095319; session-token=\"lDjEW2tITXjGGCfAW58KQZNMaxV6YTOKGAmkpI1KaXKj9/WhiCjhddCrArE2g2f687YPyw6o2xCPCCWO/yHGccNIumZczYhGTQP4jK2aLBQqKm4A/s2IxrU4wxOZiuxXgFFeGbXkfnGiFbYc28hSByxEoJY0ftB4Q2DQk9wl8e6uRcUh0Qy5ZgEQp2pameL18HeaQzpkhFkTjz4JWji/F1wqAkyE0K5NGIzIGIEtV5I=\"; x-acbjp=\"XkJ?sdoxlkqYofwCUFx4YYskNvmNnent4RFHgQx7DPZzvt1p2XtcEhwai7KUF?fP\"; at-acbjp=\"5|FOQKTp5uUgfJ8Q9czoCye77JuofBFnzH1kz1NWF1fw4Oqm9qw8cND3OKkKTB+GfMisW2RAVnrEcTkmzSXGVYzxRyPn77+XAATrLu4KIAn1/knjFYQGGXn5Dk8e2XXTyxvCiBFAhF7yu8bhoYaIyAD+9NL8rTpGOhZD8S+pRnFwT47/UN14BrOzJnjq43BgOszZTi/MZRAc4ubOczFs7DVi9UaFexlqdVEBE1+p84a2XSbY+LLxNy+57RZ66+6cy6Mdtgj7hgHJ/4iocpZiBT0VKo9bGwwGIQ+BN6R39VKzs=\"; sess-at-acbjp=\"HTvpRhBE4ssOA/Ak82ebMSCj310HfIoPpxC4pJc2iMM=\"; lc-acbjp=en_JP; a-ogbcbff=1";
    static String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36";


    public static void main(String[] args) throws Exception {


    }

    /**
     * 获取指定HTML 文档指定的body
     * @throws java.io.IOException
     */
    private static void test2() throws IOException {

    }
    private static void test() throws IOException {
        File file = new File("d:\\wuzhiming_p2.csv");
        Writer writer = new FileWriter(file);
        CSVWriter csvWriter = new CSVWriter(writer, ',');



        JsoupTestMul jsoupTest = new JsoupTestMul();

        //Document doc = Jsoup.parse(new File("d:/orderList.html"),"UTF-8");
        Document doc = Jsoup.parse(jsoupTest.orderList());
            Elements ListDiv = doc.getElementsByClass("a-box-group");
            for (Element element :ListDiv) {
                //System.out.println(element.html());
                Elements links = element.getElementsByClass("value");
                String orderData = links.get(0).text().trim();
                String orderId = links.get(3).text().trim();
                System.out.println("orderData->"+orderData);
                System.out.println("orderId->"+orderId);
                Elements shipments = element.getElementsByClass("shipment");
                for (Element shipment : shipments) {
                    String text1 =shipment.getElementsByTag("span").get(0).text();

                    System.out.println(text1);
                    if(text1.equals("On the way")|| text1.equals("Shipped") || text1.equals("Delivered")) {
                       String href = shipment.getElementsByTag("a").get(1).attr("href");
                    //   String href2= shipment.getElementsByTag("a").get(3).text();
                       String name = shipment.getElementsByTag("a").get(4).text();
                       String trackId= jsoupTest.shipTrack(href);
                        System.out.println(name+","+trackId);
                        String [] record = new String[4];
                        record[0] =orderId;
                        record[1] =orderData;
                        record[2] =name;
                        record[3] =trackId;
                        csvWriter.writeNext(record);
                    }
                  //  System.out.println(text1);
                }
            }
            csvWriter.close();
            System.out.println("OK-----");
    }



    public  String  shipTrack(String link) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
        CloseableHttpResponse response = null;
        Document doc;
        try {

            HttpPost httpPost = new HttpPost(url+link);
            httpPost.addHeader("Cookie", gfCookie);
            httpPost.addHeader("User-Agent", userAgent);
    /*        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);*/
           // httpPost.setEntity(entity);
            response = httpclient.execute(httpPost);
            String responseBody = IOUtils.toString(response.getEntity().getContent(), Consts.UTF_8);
            System.out.print(response.getStatusLine().getStatusCode());
           // System.out.print(responseBody);
            doc = Jsoup.parse(responseBody);
            Elements ListDiv = doc.getElementsByClass("ship-track-grid-subtext");
            for (Element element :ListDiv) {
               return StringUtils.substringAfterLast(element.text(),"#:").trim();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }

    public  String  orderList() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
        CloseableHttpResponse response = null;
        Document doc;
        try {

            HttpPost httpPost = new HttpPost("https://www.amazon.co.jp/gp/your-account/order-history?opt=ab&digitalOrders=1&unifiedOrders=1&returnTo=&__mk_ja_JP=%E3%82%AB%E3%82%BF%E3%82%AB%E3%83%8A&orderFilter=last30");
            httpPost.addHeader("Cookie", gfCookie);
            httpPost.addHeader("User-Agent", userAgent);
    /*        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);*/
           // httpPost.setEntity(entity);
            response = httpclient.execute(httpPost);
            String responseBody = IOUtils.toString(response.getEntity().getContent(), Consts.UTF_8);
            System.out.print(response.getStatusLine().getStatusCode());
            return responseBody;
        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }


}