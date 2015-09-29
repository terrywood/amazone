package com.gt.bmf.service;

import com.gt.bmf.common.page.PageList;
import com.gt.bmf.pojo.GfQueryLog;
import com.gt.bmf.pojo.Order;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public interface OrderService extends BmfBaseService<Order> {
    public  void  loadOrders(String cookie,String page) throws ParseException, IOException, InterruptedException;

    public PageList findPageData(String orderId,String status,String productId,String orderName,Date orderTime,String tag, Date deliveryDate, int pageNum, int pageSize);

}
