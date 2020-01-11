package com.clarence.order.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * name: 指定访问应用的接口
 */
@FeignClient(name = "product")
public interface ProductClient {
    @GetMapping("/product/list")
    String listProduct();
}
