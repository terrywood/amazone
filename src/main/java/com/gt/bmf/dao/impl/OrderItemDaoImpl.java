package com.gt.bmf.dao.impl;

import com.gt.bmf.dao.OrderDao;
import com.gt.bmf.dao.OrderItemDao;
import com.gt.bmf.pojo.Order;
import com.gt.bmf.pojo.OrderItem;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 15-8-24.
 */
@Repository("orderItemDao")
public class OrderItemDaoImpl extends BmfBaseDaoImpl<OrderItem> implements OrderItemDao {

}
