package com.framework.module.product.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kratos.entity.BaseEntity;
import com.kratos.module.attachment.domain.Attachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 商品规格条目
 * @author tang he
 * @since 1.0.0
 */
@Entity
@ApiModel("商品规格条目")
public class ProductStandardItem extends BaseEntity {
    @ApiModelProperty(value = "商品规格")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private ProductStandard productStandard;
    @ApiModelProperty(value = "名称")
    @Column(length = 50)
    private String name;

    public ProductStandard getProductStandard() {
        return productStandard;
    }

    public void setProductStandard(ProductStandard productStandard) {
        this.productStandard = productStandard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
