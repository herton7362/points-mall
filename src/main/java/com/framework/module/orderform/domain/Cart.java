package com.framework.module.orderform.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.framework.module.member.domain.Member;
import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

/**
 * 购物车
 * @author tang he
 * @since 1.0.0
 */
@Entity
@ApiModel("购物车")
public class Cart extends BaseEntity {
    @ApiModelProperty(value = "会员")
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;
    @ApiModelProperty(value = "购物车条目")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private List<CartItem> items;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
