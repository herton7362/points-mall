package com.framework.module.product.web;

import com.framework.module.product.domain.Product;
import com.framework.module.product.service.ProductService;
import com.kratos.common.AbstractReadController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "游客产品接口，无权限过滤")
@RestController
@RequestMapping("/product")
public class GuestProductController extends AbstractReadController<Product> {
    private final ProductService productService;
    @Override
    protected CrudService<Product> getService() {
        return productService;
    }

    @Autowired
    public GuestProductController(ProductService productService) {
        this.productService = productService;
    }
}
