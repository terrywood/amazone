package com.gt.bmf.dao.impl;

import com.gt.bmf.dao.OrderDao;
import com.gt.bmf.dao.ProductDao;
import com.gt.bmf.pojo.Product;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 15-8-24.
 */
@Repository("productDao")
public class ProductDaoImpl extends BmfBaseDaoImpl<Product> implements ProductDao {

}
