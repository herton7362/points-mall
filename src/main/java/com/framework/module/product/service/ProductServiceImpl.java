package com.framework.module.product.service;

import com.framework.module.product.domain.Product;
import com.framework.module.product.domain.ProductRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ProductServiceImpl extends AbstractCrudService<Product> implements ProductService {
    private final ProductRepository productRepository;
    @Override
    protected PageRepository<Product> getRepository() {
        return productRepository;
    }

    @Override
    public Long count() {
        return productRepository.count(
                (Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)-> {
                    List<Predicate> predicate = new ArrayList<>();
                    predicate.add(criteriaBuilder.equal(root.get("logicallyDeleted"), false));
                    return criteriaBuilder.and(predicate.toArray(new Predicate[]{}));
                }
        );
    }

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
