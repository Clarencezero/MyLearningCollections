package com.clarence.product.repository;

import com.clarence.product.daoobject.ProductCategory;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProductCategoryRepository extends Repository<ProductCategory, Integer> {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
