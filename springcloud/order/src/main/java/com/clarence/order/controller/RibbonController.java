package com.clarence.order.controller;

import com.clarence.order.VO.ResultVO;
import com.clarence.order.client.ProductClient;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.PrivateKey;

@RestController
public class RibbonController {
    public static final String PRODUCT_HOST_NAME = "PRODUCT";
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    // @Autowired
    // RestTemplate restTemplate;


    @Autowired
    ProductClient productClient;

    @GetMapping("getProductMsg2")
    public ResultVO getProductMsg2() {
        String s = productClient.listProduct();
        ResultVO resultVO = new ResultVO();
        resultVO.setData(s);
        return resultVO;
    }

    @GetMapping("getProductMsg")
    public ResultVO getProductMsg() {
        // 通过负载均衡查找 Eureka 里面的服务, 然后返回IP地址
        ResultVO resultVO = new ResultVO();

        // 第一种方式直接通过RestTemplate调用绝对URL

        // 第二种方式通过利用loadBalancerClient通过应用名称获取URL,然后再使用RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance instance = loadBalancerClient.choose(PRODUCT_HOST_NAME);
        String url = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/product/list");
        String forObject = restTemplate.getForObject(url, String.class);


        // 第三种方式
        // String forObject = restTemplate.getForObject("http://" + PRODUCT_HOST_NAME + "/product/list", String.class);

        resultVO.setData(forObject);

        return resultVO;

    }
}
