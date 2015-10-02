package com.gt.bmf.dao;

import com.gt.bmf.common.page.PageList;
import com.gt.bmf.pojo.Order;
import com.gt.bmf.pojo.OrderItem;

import java.util.List;
import java.util.Map;

public interface OrderItemDao extends BmfBaseDao<OrderItem> {
    public List<OrderItem> findItemByOrderId(String orderId);
    public PageList findOrderItemPageData(Map<String,String> params, Integer pageNum, Integer pageSize);
}
