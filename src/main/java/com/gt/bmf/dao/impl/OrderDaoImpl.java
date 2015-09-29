package com.gt.bmf.dao.impl;

import com.gt.bmf.dao.GfQueryLogDao;
import com.gt.bmf.dao.OrderDao;
import com.gt.bmf.pojo.GfQueryLog;
import com.gt.bmf.pojo.Order;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 15-8-24.
 */
@Repository("orderDao")
public class OrderDaoImpl extends BmfBaseDaoImpl<Order> implements OrderDao {

}
