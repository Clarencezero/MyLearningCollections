package com.clarence.product.service.impl;

import com.clarence.product.ProductApplicationTests;
import com.clarence.product.daoobject.ProductInfo;
import com.clarence.product.service.ProduceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.junit.Assert.*;

@Component
public class ProduceServiceImplTest extends ProductApplicationTests {

    @Autowired
    ProduceService produceService;
    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = produceService.findUpAll();
        System.out.println("集合大小: " + upAll.size());
        assertTrue(upAll.size() > 0);
    }
}