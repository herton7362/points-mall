package com.framework.module.product.service;

import com.framework.module.product.domain.ProductCategory;
import com.framework.module.product.domain.ProductCategoryRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ProductCategoryServiceImpl extends AbstractCrudService<ProductCategory> implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    @Override
    protected PageRepository<ProductCategory> getRepository() {
        return productCategoryRepository;
    }

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }
}
