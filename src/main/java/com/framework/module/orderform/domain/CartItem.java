package com.framework.module.orderform.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.framework.module.product.domain.Product;
import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@ApiModel("购物车项")
public class CartItem extends BaseEntity {
    @ApiModelProperty(value = "购物车")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Cart cart;
    @ApiModelProperty(value = "商品")
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    @ApiModelProperty(value = "数量")
    private Integer count;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
