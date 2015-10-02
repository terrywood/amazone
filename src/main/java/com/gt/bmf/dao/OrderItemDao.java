package com.gt.bmf.dao;

import com.gt.bmf.pojo.Order;
import com.gt.bmf.pojo.OrderItem;

import java.util.List;

public interface OrderItemDao extends BmfBaseDao<OrderItem> {
      List<OrderItem> findItemByOrderId(String orderId);
}
