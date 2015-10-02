package com.gt.bmf.dao.impl;

import com.gt.bmf.common.page.PageList;
import com.gt.bmf.dao.OrderDao;
import com.gt.bmf.dao.OrderItemDao;
import com.gt.bmf.pojo.Order;
import com.gt.bmf.pojo.OrderItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 15-8-24.
 */
@Repository("orderItemDao")
public class OrderItemDaoImpl extends BmfBaseDaoImpl<OrderItem> implements OrderItemDao {

    @Override
    public List<OrderItem> findItemByOrderId(String orderId) {
        return super.find("from OrderItem where orderId=? order by deliveryDate desc,orderId desc",orderId);
    }

    @Override
    public PageList findOrderItemPageData(Map<String, String> params, Integer pageNum, Integer pageSize) {
        String hql ="select a.trackId,a.status,a.deliveryDate,c.name,a.orderId,b.orderTime,b.orderName" +
                ",a.id,a.tag from OrderItem a, Order b ,Product c where a.orderId=b.id and a.productId=c.id";
        List<Object> paramList = new ArrayList<Object>();
        if(StringUtils.isNotBlank(params.get("orderName"))) {
            hql+=" and b.orderName = ?";
            paramList.add(params.get("orderName").trim());
        }
        if(StringUtils.isNotBlank(params.get("orderId"))) {
            hql+=" and b.id like ?";
            paramList.add("%"+params.get("orderId").trim()+"%");
        }
        hql+=" order by a.deliveryDate desc, b.id desc";
        return  super.findPageData(hql,pageNum,pageSize,paramList.toArray());
    }

    @Override
    public void updateOrderItemTag(String orderId, boolean b) {
        super.executeByHQL("update OrderItem set tag =? where orderId=?",b,orderId);
    }

    @Override
    public void updateOrderItemTagByItemId(Long itemId, boolean b) {
        super.executeByHQL("update OrderItem set tag =? where id=?",b,itemId);
    }
}
