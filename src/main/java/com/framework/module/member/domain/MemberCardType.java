package com.framework.module.member.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@ApiModel("会员卡类型")
public class MemberCardType extends BaseEntity {
    @ApiModelProperty(value = "类型名称")
    @Column(length = 10)
    private String name;
    @ApiModelProperty(value = "折扣")
    @Column(length = 3, precision = 3, scale = 2)
    private Double discount;
    @ApiModelProperty(value = "备注")
    @Column(length = 100)
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
