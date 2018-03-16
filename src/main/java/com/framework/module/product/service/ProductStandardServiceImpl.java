package com.framework.module.product.service;

import com.framework.module.product.domain.ProductStandard;
import com.framework.module.product.domain.ProductStandardItem;
import com.framework.module.product.domain.ProductStandardItemRepository;
import com.framework.module.product.domain.ProductStandardRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ProductStandardServiceImpl extends AbstractCrudService<ProductStandard> implements ProductStandardService {
    private final ProductStandardRepository productStandardRepository;
    private final ProductStandardItemRepository productStandardItemRepository;

    @Override
    protected PageRepository<ProductStandard> getRepository() {
        return productStandardRepository;
    }

    @Override
    public ProductStandard save(ProductStandard productStandard) throws Exception {
        final ProductStandard newProductStandard = super.save(productStandard);
        List<ProductStandardItem> items = productStandard.getItems();
        items.forEach(productStandardItem -> {
            productStandardItem.setProductStandard(newProductStandard);
            productStandardItemRepository.save(productStandardItem);
        });
        return newProductStandard;
    }

    @Autowired
    public ProductStandardServiceImpl(
            ProductStandardRepository productStandardRepository,
            ProductStandardItemRepository productStandardItemRepository
    ) {
        this.productStandardRepository = productStandardRepository;
        this.productStandardItemRepository = productStandardItemRepository;
    }
}
