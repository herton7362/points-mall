package com.framework.module.product.service;

import com.framework.module.product.domain.Product;
import com.kratos.common.CrudService;

public interface ProductService extends CrudService<Product> {
    /**
     * 查询总数
     * @return 总数
     */
    Long count();
}
