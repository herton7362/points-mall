package com.framework.module.product.web;

import com.framework.module.product.domain.ProductStandard;
import com.framework.module.product.service.ProductStandardService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "产品规格管理")
@RestController
@RequestMapping("/api/productStandard")
public class ProductStandardController extends AbstractCrudController<ProductStandard> {
    private final ProductStandardService productStandardService;
    @Override
    protected CrudService<ProductStandard> getService() {
        return productStandardService;
    }

    @Autowired
    public ProductStandardController(ProductStandardService productStandardService) {
        this.productStandardService = productStandardService;
    }
}
