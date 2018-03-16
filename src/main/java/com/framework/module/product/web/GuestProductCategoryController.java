package com.framework.module.product.web;

import com.framework.module.product.domain.ProductCategory;
import com.framework.module.product.service.ProductCategoryService;
import com.kratos.common.AbstractReadController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "游客产品接口，无权限过滤")
@RestController
@RequestMapping("/productCategory")
public class GuestProductCategoryController extends AbstractReadController<ProductCategory> {
    private final ProductCategoryService productCategoryService;
    @Override
    protected CrudService<ProductCategory> getService() {
        return productCategoryService;
    }

    @Autowired
    public GuestProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }
}
