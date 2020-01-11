package com.clarence.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
public enum ProductStatusEnum {
    UP(0, "在架"),
    DOWN(1, "下架");
    ;

    private Integer code;
    private String message;


}
