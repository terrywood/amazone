package com.gt.bmf.dao.impl;

import com.gt.bmf.common.page.PageList;
import com.gt.bmf.dao.GfQueryLogDao;
import com.gt.bmf.dao.OrderDao;
import com.gt.bmf.pojo.GfQueryLog;
import com.gt.bmf.pojo.Order;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 15-8-24.
 */
@Repository("orderDao")
public class OrderDaoImpl extends BmfBaseDaoImpl<Order> implements OrderDao {

    @Override
    public PageList<Order> findPageData(Map<String, String> params, int pageNum, int pageSize) {
       String hql ="from Order where 1=1";
        List<Object> paramList = new ArrayList<Object>();
        if(StringUtils.isNotBlank(params.get("orderName"))) {
            hql+=" and orderName = ?";
            paramList.add(params.get("orderName").trim());
        }
        if(StringUtils.isNotBlank(params.get("orderId"))) {
            hql+=" and id like ?";
            paramList.add("%"+params.get("orderId").trim()+"%");
        }
          hql+=" order by orderTime desc, id desc";
       return  super.findPageData(hql,pageNum,pageSize,paramList.toArray());
    }
}
