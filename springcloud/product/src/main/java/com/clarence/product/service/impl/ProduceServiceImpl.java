package com.clarence.product.service.impl;

import com.clarence.product.daoobject.ProductInfo;
import com.clarence.product.enums.ProductStatusEnum;
import com.clarence.product.repository.ProductInfoRepository;
import com.clarence.product.service.ProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduceServiceImpl implements ProduceService {
    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }
}
