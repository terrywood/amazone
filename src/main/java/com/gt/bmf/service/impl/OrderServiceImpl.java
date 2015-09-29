package com.gt.bmf.service.impl;

import com.gt.bmf.common.page.PageList;
import com.gt.bmf.dao.*;
import com.gt.bmf.pojo.GfQueryLog;
import com.gt.bmf.pojo.Order;
import com.gt.bmf.pojo.OrderItem;
import com.gt.bmf.pojo.Product;
import com.gt.bmf.service.GfQueryLogService;
import com.gt.bmf.service.OrderService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("orderService")
public class OrderServiceImpl extends BmfBaseServiceImpl<Order> implements OrderService {
    SimpleDateFormat dateformat1=new SimpleDateFormat("MMMM dd,yyyy", Locale.ENGLISH);
    SimpleDateFormat dateformat2=new SimpleDateFormat("EEE, MMMM dd,yyyy", Locale.ENGLISH);
    static String url="https://www.amazon.co.jp";

    private String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36";
	private OrderDao orderDao;
    @Autowired
	private OrderItemDao orderItemDao;
    @Autowired
	private ProductDao productDao;
    @Autowired
	@Qualifier("orderDao")
	@Override
	public void setBmfBaseDao(BmfBaseDao<Order> bmfBaseDao) {
		this.bmfBaseDao = bmfBaseDao;
		this.orderDao = (OrderDao) bmfBaseDao;
	}

    @PostConstruct
    public void init(){

    }


    @Override
    public void loadOrders(String cookie, String page) throws ParseException, IOException, InterruptedException {
        Document doc = Jsoup.parse(orderList(cookie, null));
        //Document doc = Jsoup.parse(new File("d:/abcd.html"),"UTF-8");

        Elements ListDiv = doc.getElementsByClass("a-box-group");
        for (Element element :ListDiv) {
            Elements links = element.getElementsByClass("value");
            String orderDate = links.get(0).text().trim();
            String orderName = links.get(2).text().trim();
            String orderId = links.get(3).text().trim();
            //System.out.println("orderId-"+orderId);
            Order order = orderDao.get(orderId);
            if(order ==null){
                order = new Order();
                order.setCreateTime(new Date());
                order.setId(orderId);
                order.setOrderName(orderName);
                order.setOrderTime(dateformat1.parse(orderDate));
                orderDao.save(order);
            }

            List<OrderItem> list = new ArrayList<OrderItem>();
            Elements shipments = element.getElementsByClass("shipment");
            for (Element shipment : shipments) {
                String status =shipment.getElementsByTag("span").get(0).text();
                if(status.equals("On the way")|| status.equals("Shipped") || status.equals("Delivered")) {
                    System.out.println("href-------------------------------------------begin");
                /*   Elements as =shipment.getElementsByTag("a");
                    for(Element el : as){
                        System.out.println(el.html());
                    }*/

                    String href = shipment.getElementsByClass("a-button-text").get(0).attr("href");
                    String shipmentId = StringUtils.substringAfterLast(href,"shipmentId=");
                    Date delivery = null ;
                    Element product =null;
                    if(status.equals("Delivered")){
                           String as =shipment.getElementsByTag("span").get(1).text();
                           if(as.startsWith("Delivered on")){
                               delivery = dateformat2.parse( StringUtils.remove(as,"Delivered on:").trim());
                           }else{
                               System.out.println("can not get status by shipment id["+shipmentId+"]");
                           }

                        product = shipment.getElementsByTag("a").get(2);
                    }else{
                        delivery =dateformat2.parse(shipment.getElementsByTag("span").get(2).text());
                        product = shipment.getElementsByTag("a").get(3);
                    }

                    System.out.println("href-------------------------------------------end");
                    String productLink = product.attr("href");
                    String productCode = StringUtils.substringBetween(productLink, "product/", "/");
                    if(productDao.get(productCode)==null){
                        Element image = product.child(0);
                        String productName = image.attr("title");
                        Product model =new Product();
                        model.setId(productCode);
                        model.setName(productName);
                        model.setImage(image.attr("src"));
                        productDao.save(model);
                    }
                    OrderItem item = orderItemDao.findUnique("from OrderItem where shipmentId=?",shipmentId);
                    if(item==null){
                        item = new OrderItem();
                        item.setOrderId(orderId);
                        item.setShipmentId(shipmentId);
                        item.setDeliveryDate(delivery);
                        item.setShipmentLink(href);
                        item.setStatus(status);
                        item.setCreateTime(new Date());
                        item.setUpdateTime(new Date());
                        item.setProductId(productCode);
                        list.add(item);
                    }else{
                        item.setUpdateTime(new Date());
                        item.setStatus(status);
                        item.setDeliveryDate(delivery!=null?delivery:null);
                        orderItemDao.update(item);
                    }
                }
            }

            shipTrack(list,cookie);
        }
    }

    @Override
    public PageList findPageData(String orderId, String status, String productId, String orderName, Date orderTime, String tag, Date deliveryDate, int pageNum, int pageSize) {
        return null;
    }

    public  String  shipTrack(List<OrderItem> list,String cookie) throws IOException, InterruptedException {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        CloseableHttpClient  httpclient = HttpClients.custom().setConnectionManager(cm) .build();
        try {
            GetThread[] threads = new GetThread[list.size()];
            for(int i=0;i<list.size();i++){
                OrderItem item =list.get(i);
                HttpGet httpget = new HttpGet(url+item.getShipmentLink());
                httpget.addHeader("Cookie",cookie);
                httpget.addHeader("User-Agent",userAgent);
                threads[i] = new GetThread(httpclient, httpget, i + 1,item);
            }
            for (int j = 0; j < threads.length; j++) {
                threads[j].start();
            }
            for (int j = 0; j < threads.length; j++) {
                threads[j].join();
            }

            for (int j = 0; j < threads.length; j++) {
                OrderItem entity = threads[j].getOrderItem();
                if(entity.getTrackId()!=null){
                    orderItemDao.save(entity);
                }
            }
        } finally {
            httpclient.close();
        }


        return null;
    }

    public  String  orderList(String cookie,String url) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
        CloseableHttpResponse response = null;
        Document doc;
        try {
            String _url = url!=null?url:"https://www.amazon.co.jp/gp/your-account/order-history?orderFilter=last30";
            HttpPost httpPost = new HttpPost(_url);
            httpPost.addHeader("Cookie", cookie);
            httpPost.addHeader("User-Agent", userAgent);

            response = httpclient.execute(httpPost);
            String responseBody = IOUtils.toString(response.getEntity().getContent(), Consts.UTF_8);
            System.out.print(response.getStatusLine().getStatusCode());
            return responseBody;
        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }


    static class GetThread extends Thread {

        private  OrderItem orderItem;
        private final CloseableHttpClient httpClient;
        private final HttpContext context;
        private final HttpGet httpget;
        private final int id;
        public GetThread(CloseableHttpClient httpClient, HttpGet httpget, int id, OrderItem orderItem) {
            this.httpClient = httpClient;
            this.context = new BasicHttpContext();
            this.httpget = httpget;
            this.id = id;
            this.orderItem = orderItem;

        }

        public OrderItem getOrderItem() {
            return orderItem;
        }

        public void setOrderItem(OrderItem orderItem) {
            this.orderItem = orderItem;
        }

        /**
         * Executes the GetMethod and prints some status information.
         */
        @Override
        public void run() {
            try {
                CloseableHttpResponse response = httpClient.execute(httpget, context);
                try {
                     System.out.println(id + " - get executed");
                     String responseBody = IOUtils.toString(response.getEntity().getContent(), Consts.UTF_8);
                     Document doc = Jsoup.parse(responseBody);

                    Elements ListDiv = doc.getElementsByClass("ship-track-grid-subtext");
                    for (Element element :ListDiv) {
                        String trackId = StringUtils.substringAfterLast(element.text(),"#:").trim();
                        orderItem.setTrackId(trackId);
                    }

                } catch (Exception e) {
                   e.printStackTrace();
                } finally {
                    response.close();
                }
            } catch (Exception e) {
                System.out.println(id + " - error: " + e);
            }
        }

    }
}
