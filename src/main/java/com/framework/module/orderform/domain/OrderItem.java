package com.framework.module.orderform.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.framework.module.product.domain.Product;
import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@ApiModel("订单项")
public class OrderItem extends BaseEntity{
    @ApiModelProperty(value = "订单")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private OrderForm orderForm;
    @ApiModelProperty(value = "购买的商品")
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    @ApiModelProperty(value = "购买的数量")
    private Integer count;

    public OrderForm getOrderForm() {
        return orderForm;
    }

    public void setOrderForm(OrderForm orderForm) {
        this.orderForm = orderForm;
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
