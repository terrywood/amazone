package com.gt.bmf.dao;

import com.gt.bmf.common.page.PageList;
import com.gt.bmf.pojo.GfQueryLog;
import com.gt.bmf.pojo.Order;

import java.util.Map;

public interface OrderDao extends BmfBaseDao<Order> {
    public PageList<Order> findPageData(Map<String, String> params, int pageNum, int pageSize);
}
