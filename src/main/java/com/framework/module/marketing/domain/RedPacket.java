package com.framework.module.marketing.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@ApiModel(value = "红包")
public class RedPacket extends BaseEntity {
    @ApiModelProperty(required = true, value = "金额")
    @Column(length = 11, precision = 2)
    private Double amount;
    @ApiModelProperty(required = true, value = "使用条件：满 ? 元，无门槛请输入0")
    @Column(length = 11, precision = 2)
    private Double minAmount;
    @ApiModelProperty(required = true, value = "开始时间")
    private Long startDate;
    @ApiModelProperty(required = true, value = "结束时间")
    private Long endDate;
    @ApiModelProperty(required = true, value = "备注")
    @Column(length = 500)
    private String remark;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
