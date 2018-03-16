package com.framework.module.member.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("储值扣费")
public class DeductBalanceParam {
    @ApiModelProperty(value = "会员主键")
    private String memberId;
    @ApiModelProperty(value = "金额")
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
