package com.clarence.product.service;

import com.clarence.product.daoobject.ProductInfo;

import java.util.List;

public interface ProduceService {
    /**
     * 查询所有在架商品列表
     */
    List<ProductInfo> findUpAll();
}
