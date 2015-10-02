package com.gt.bmf.service;

import com.gt.bmf.common.page.PageList;
import com.gt.bmf.pojo.GfQueryLog;
import com.gt.bmf.pojo.Order;
import com.gt.bmf.pojo.OrderItem;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderService extends BmfBaseService<Order> {
    public  void  loadOrders(String cookie,String page) throws ParseException, IOException, InterruptedException;

   // public PageList findPageData(String orderId,String status,String productId,String orderName,Date orderTime,String tag, Date deliveryDate, int pageNum, int pageSize);
    public List<OrderItem> findItemByOrderId(String orderId);
    public OrderItem findItemByItemId(Long itemId);
    public PageList<Order> findPageData(Map<String,String> params, Integer pageNum, Integer pageSize);
    public PageList findOrderItemPageData(Map<String,String> params, Integer pageNum, Integer pageSize);

    void updateOrderItem(OrderItem item);

    void updateOrderItemTag(String orderId, boolean b);

    void updateOrderItemTagByItemId(Long itemId, boolean b);
}
