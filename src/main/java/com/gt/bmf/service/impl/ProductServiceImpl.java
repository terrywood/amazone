package com.gt.bmf.service.impl;

import com.gt.bmf.dao.BmfBaseDao;
import com.gt.bmf.dao.OrderDao;
import com.gt.bmf.dao.ProductDao;
import com.gt.bmf.pojo.Order;
import com.gt.bmf.pojo.Product;
import com.gt.bmf.service.OrderService;
import com.gt.bmf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 15-9-23.
 */
@Service("productService")
public class ProductServiceImpl extends BmfBaseServiceImpl<Product> implements ProductService {
    private ProductDao productDao;

    @Autowired
    @Qualifier("productDao")
    @Override
    public void setBmfBaseDao(BmfBaseDao<Product> bmfBaseDao) {
        this.bmfBaseDao = bmfBaseDao;
        this.productDao = (ProductDao) bmfBaseDao;
    }


}
