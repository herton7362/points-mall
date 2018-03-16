package com.framework.module.recharge.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("充值参数")
public class RechargeParam {
    @ApiModelProperty(value = "会员id")
    private String memberId;
    @ApiModelProperty(value = "充值金额")
    private Double amount;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
