package com.clarence.product.service.impl;

import com.clarence.product.ProductApplicationTests;
import com.clarence.product.daoobject.ProductCategory;
import com.clarence.product.service.CategoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@Component
public class CategoryServiceImplTest extends ProductApplicationTests {
    @Autowired
    private CategoryService categoryService;

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> byCategoryTypeIn = categoryService.findByCategoryTypeIn(Arrays.asList(11, 12));
        System.out.println("===========" + byCategoryTypeIn.size());
        assertTrue(byCategoryTypeIn.size() > 0);
    }
}