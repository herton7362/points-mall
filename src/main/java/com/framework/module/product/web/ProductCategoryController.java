package com.framework.module.product.web;

import com.framework.module.product.domain.ProductCategory;
import com.framework.module.product.service.ProductCategoryService;
import com.framework.module.product.service.ProductService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import com.kratos.common.PageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(value = "产品类别管理")
@RestController
@RequestMapping("/api/productCategory")
public class ProductCategoryController extends AbstractCrudController<ProductCategory> {
    private final ProductCategoryService productCategoryService;
    @Override
    protected CrudService<ProductCategory> getService() {
        return productCategoryService;
    }

    /**
     * 保存
     */
    @ApiOperation(value="保存")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ProductCategory> save(@RequestBody ProductCategory productCategory) {
        if(productCategory.getParent() != null && StringUtils.isNotBlank(productCategory.getParent().getId())) {
            productCategory.setParent(productCategoryService.findOne(productCategory.getParent().getId()));
        } else {
            productCategory.setParent(null);
        }
        productCategory = productCategoryService.save(productCategory);
        return new ResponseEntity<>(productCategory, HttpStatus.OK);
    }

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }
}
