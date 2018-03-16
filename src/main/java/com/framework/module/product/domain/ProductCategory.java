package com.framework.module.product.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

/**
 * 商品分类
 * @author tang he
 * @since 1.0.0
 */
@Entity
@ApiModel("商品分类")
public class ProductCategory extends BaseEntity {
    @ApiModelProperty(value = "上级分类")
    @ManyToOne(fetch = FetchType.EAGER)
    private ProductCategory parent;
    @ApiModelProperty(value = "商品分类名称")
    @Column(length = 50)
    private String name;
    @ApiModelProperty(value = "商品分类备注")
    @Column(length = 200)
    private String remark;
    @ApiModelProperty(value = "商品规格")
    @ManyToMany
    @JoinTable(name="product_category_product_standard",joinColumns={@JoinColumn(name="product_category_id")},inverseJoinColumns={@JoinColumn(name="product_standard_id")})
    private List<ProductStandard> productStandards;

    public ProductCategory getParent() {
        return parent;
    }

    public void setParent(ProductCategory parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ProductStandard> getProductStandards() {
        return productStandards;
    }

    public void setProductStandards(List<ProductStandard> productStandards) {
        this.productStandards = productStandards;
    }
}
