package com.gt.bmf.service.impl;

import com.gt.bmf.common.page.PageList;
import com.gt.bmf.dao.*;
import com.gt.bmf.exception.BmfBaseException;
import com.gt.bmf.pojo.Order;
import com.gt.bmf.pojo.OrderItem;
import com.gt.bmf.pojo.Product;
import com.gt.bmf.service.OrderService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("orderService")
public class OrderServiceImpl extends BmfBaseServiceImpl<Order> implements OrderService {
    SimpleDateFormat dateformat1=new SimpleDateFormat("MMMM d,yyyy", Locale.ENGLISH);
    SimpleDateFormat dateformat2=new SimpleDateFormat("EEE, MMMM d,yyyy", Locale.ENGLISH);
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



    public void loadOneOrder(String cookie, String orderId) throws IOException, InterruptedException {
        Document doc = Jsoup.parse(orderList(cookie, "https://www.amazon.co.jp/gp/your-account/order-details?ie=UTF8&orderID="+orderId));
        Elements ListDiv = doc.getElementsByClass("a-box-group");
        List<Order> orders = new ArrayList<Order>();
        for (Element element :ListDiv) {
    /*        Elements orderInfo = element.getElementsByClass("order-info");
            Elements value = orderInfo.get(0).getElementsByClass("value");
            String orderDate = value.get(0).text().trim();
            String orderName = value.get(2).text().trim();
            String orderId = value.get(3).text().trim();*/

            Order order = orderDao.get(orderId);
            if(order ==null){
                String  orderName = doc.getElementsByClass("displayAddressFullName").first().text();
                String  orderDate = doc.getElementsByClass("order-date-invoice-item").first().text();

                order = new Order();
                order.setCreateTime(new Date());
                order.setId(orderId);
                order.setOrderName(orderName);
                try {
                    order.setOrderTime(dateformat1.parse(StringUtils.removeStart(orderDate,"Ordered on ")));
                } catch (ParseException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                orderDao.save(order);
            }


             if(order.getTotalItem()==null|| order.getTotalItem()<1){
                 String link =  "/gp/css/summary/print.html/ref=od_aui_print_invoice?ie=UTF8&orderID="+orderId;
                 order.setOrderLink(link);
                 orders.add(order);
             }


            List<OrderItem> list = new ArrayList<OrderItem>();
            Elements shipments = element.getElementsByClass("shipment");
            for (Element shipment : shipments) {
                String status =shipment.getElementsByTag("span").get(0).text();
                if(status.equals("On the way")|| status.equals("Shipped") || status.equals("Delivered")) {
                   // System.out.println("href-------------------------------------------begin");
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
                               try {
                                   delivery = dateformat2.parse( StringUtils.remove(as,"Delivered on:").trim());
                               } catch (ParseException e) {
                                   e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                               }
                           }else{
                               System.out.println("can not get status by shipment id["+shipmentId+"]");
                           }
                    }else{
                        try {
                            delivery =dateformat2.parse(shipment.getElementsByTag("span").get(2).text());
                        } catch (ParseException e){
                           e.printStackTrace();
                        }
                    }
                  //  System.out.println("href-------------------------------------------end");
                    product = shipment.getElementsByClass("a-link-normal").get(0);
                    String productLink = product.attr("href");
                    String productCode = StringUtils.substringBetween(productLink, "product/", "/");

                    //product no need always fetch.
        /*            if(productDao.get(productCode)==null){
                        Element image = product.child(0);
                        String productName = image.attr("title");
                        Product model =new Product();
                        model.setId(productCode);
                        model.setName(productName);
                        model.setImage(image.attr("src"));
                        productDao.save(model);
                    }*/

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
            loadTrack(list, cookie);
        }

         loadOrderTotalItem(orders,cookie);
    }
    public void loadOrders(String cookie, String page) throws IOException, InterruptedException {
        Document doc = Jsoup.parse(orderList(cookie, page));
        //Document doc = Jsoup.parse(new File("d:/abcd.html"),"UTF-8");
        Elements ListDiv = doc.getElementsByClass("a-box-group");
        List<Order> orders = new ArrayList<Order>();
        for (Element element :ListDiv) {
            Elements orderInfo = element.getElementsByClass("order-info");
            Elements value = orderInfo.get(0).getElementsByClass("value");
            String orderDate = value.get(0).text().trim();
            String orderName = value.get(2).text().trim();
            String orderId = value.get(3).text().trim();





            Order order = orderDao.get(orderId);
            if(order ==null){
                order = new Order();
                order.setCreateTime(new Date());
                order.setId(orderId);
                order.setOrderName(orderName);
                try {
                    order.setOrderTime(dateformat1.parse(orderDate));
                } catch (ParseException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                orderDao.save(order);
            } else{
                if(order.getOrderTime()==null){
                    try {
                        order.setOrderName(orderName);
                        order.setOrderTime(dateformat1.parse(orderDate));
                        orderDao.update(order);
                    } catch (ParseException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

                    //System.out.println(order.getOrderTime());
                }



            }


             if(order.getTotalItem()==null|| order.getTotalItem()<1){
                 System.out.println("need update orderId["+orderId+"] orderDate["+orderDate+"]");
                 String link =  orderInfo.get(0).getElementsByTag("a").last().attr("href");
                 order.setOrderLink(link);
                 orders.add(order);
             }

            int k=0;
            List<OrderItem> list = new ArrayList<OrderItem>();
            Elements shipments = element.getElementsByClass("shipment");
            for (Element shipment : shipments) {
                k++;
                String status =shipment.getElementsByTag("span").get(0).text();
                if(status.equals("On the way")|| status.equals("Shipped") || status.equals("Delivered")) {

                    String href = shipment.getElementsByClass("a-button-text").get(0).attr("href");
                    String shipmentId = StringUtils.substringAfterLast(href,"shipmentId=");
                    Date delivery = null ;
                    Element product =null;

                    if(status.equals("Delivered")){
                           String as =shipment.getElementsByTag("span").get(1).text();
                           if(as.startsWith("Delivered on")){
                               try {
                                   delivery = dateformat2.parse( StringUtils.remove(as,"Delivered on:").trim());
                               } catch (ParseException e) {
                                   e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                               }
                           }else{
                               System.out.println("can not get status by shipment id["+shipmentId+"]");
                           }

                       // product = shipment.getElementsByTag("a").get(2);
                    }else{
                        try {
                            delivery =dateformat2.parse(shipment.getElementsByTag("span").get(2).text());
                        } catch (ParseException e){
                           e.printStackTrace();
                        }
                       // product = shipment.getElementsByTag("a").get(3);
                    }

                    product = shipment.getElementsByClass("a-link-normal").get(0);
                    System.out.println("href-------------------------------------------end");
                    System.out.println("orderId["+orderId+"]");
                    System.out.println("status["+status+"]");
                    System.out.println("k["+k+"]");

                    System.out.println(product.html());
                    String productLink = product.attr("href");
                    String productCode = StringUtils.substringBetween(productLink, "product/", "/");



                    //product no need always fetch.
                   if(k==1 && productDao.get(productCode)==null){
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
                        item.setProductId(productCode);
                        item.setUpdateTime(new Date());
                        item.setStatus(status);
                        item.setDeliveryDate(delivery!=null?delivery:null);
                        orderItemDao.update(item);
                    }
                }

            }
            loadTrack(list, cookie);
        }

         loadOrderTotalItem(orders,cookie);
    }

    @Override
    public List<OrderItem> findItemByOrderId(String orderId) {
        List<OrderItem> list = orderItemDao.findItemByOrderId(orderId);
        for(OrderItem obj:list){
            if(obj.getProductId()!=null)
            obj.setProduct(productDao.get(obj.getProductId()));
        }
        return  list;
    }

    @Override
    public OrderItem findItemByItemId(Long itemId) {
        return orderItemDao.get(itemId);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PageList<Order> findPageData(Map<String, String> params, Integer pageNum, Integer pageSize) {
        PageList<Order> orderPageList  =orderDao.findPageData(params,pageNum,pageSize);
        for(Order order : orderPageList.getData()){
            order.setOrderItems(findItemByOrderId(order.getId()));
        }
        return orderPageList;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PageList findOrderItemPageData(Map<String, String> params, Integer pageNum, Integer pageSize) {
        return orderItemDao.findOrderItemPageData(params, pageNum, pageSize);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateOrderItem(OrderItem item) {
        orderItemDao.update(item);
    }

    @Override
    public void updateOrderItemTag(String orderId, boolean b) {
        orderItemDao.updateOrderItemTag(orderId,b);
    }

    @Override
    public void updateOrderItemTagByItemId(Long itemId, boolean b) {
        orderItemDao.updateOrderItemTagByItemId(itemId, b);
    }


    public  String loadOrderTotalItem(List<Order> list, String cookie) throws IOException, InterruptedException {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        CloseableHttpClient  httpclient = HttpClients.custom().setConnectionManager(cm) .build();
        try {
            GetOrderThread[] threads = new GetOrderThread[list.size()];
            for(int i=0;i<list.size();i++){
                Order item =list.get(i);
                HttpGet httpget = new HttpGet(url+item.getOrderLink());
                httpget.addHeader("Cookie",cookie);
                httpget.addHeader("User-Agent",userAgent);
                threads[i] = new GetOrderThread(httpclient, httpget, i + 1,item);
            }
            for (int j = 0; j < threads.length; j++) {
                threads[j].start();
            }
            for (int j = 0; j < threads.length; j++) {
                threads[j].join();
            }

            for (int j = 0; j < threads.length; j++) {
                Order entity = threads[j].getOrder();
                if(entity.getTotalItem()!=null){
                    orderDao.saveOrUpdate(entity);
                }
            }
        } finally {
            httpclient.close();
        }

        return null;
    }
    public  String loadTrack(List<OrderItem> list, String cookie) throws IOException, InterruptedException {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        CloseableHttpClient  httpclient = HttpClients.custom().setConnectionManager(cm) .build();
        try {
            GetOrderItemThread[] threads = new GetOrderItemThread[list.size()];
            for(int i=0;i<list.size();i++){
                OrderItem item =list.get(i);
                HttpGet httpget = new HttpGet(url+item.getShipmentLink());
                httpget.addHeader("Cookie",cookie);
                httpget.addHeader("User-Agent",userAgent);
                threads[i] = new GetOrderItemThread(httpclient, httpget, i + 1,item);
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
            int code = (response.getStatusLine().getStatusCode());
            if(code==302){
                throw new BmfBaseException("302","cookie invalid");
            }
            return responseBody;
        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }


    static class GetOrderItemThread extends Thread {

        private  OrderItem orderItem;
        private final CloseableHttpClient httpClient;
        private final HttpContext context;
        private final HttpGet httpget;
        private final int id;
        public GetOrderItemThread(CloseableHttpClient httpClient, HttpGet httpget, int id, OrderItem orderItem) {
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

    static class GetOrderThread extends Thread {

        private  Order order;
        private final CloseableHttpClient httpClient;
        private final HttpContext context;
        private final HttpGet httpget;
        private final int id;
        public GetOrderThread(CloseableHttpClient httpClient, HttpGet httpget, int id, Order order) {
            this.httpClient = httpClient;
            this.context = new BasicHttpContext();
            this.httpget = httpget;
            this.id = id;
            this.order = order;

        }

        Order getOrder() {
            return order;
        }



        @Override
        public void run() {
            try {
                CloseableHttpResponse response = httpClient.execute(httpget, context);
                try {
                     System.out.println(id + " - get executed");
                     String responseBody = IOUtils.toString(response.getEntity().getContent(), Consts.UTF_8);
                     Document doc = Jsoup.parse(responseBody);
                     Integer total =-1;
                    Elements ListDiv = doc.getElementsByTag("input");
                    for (Element element :ListDiv) {
                         String value =element.attr("value");
                        // System.out.println("value---"+value);
                        total+=Integer.valueOf(value);
                    }
                    order.setTotalItem(total);
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
