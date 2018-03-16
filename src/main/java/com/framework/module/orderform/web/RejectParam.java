package com.framework.module.orderform.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("退款参数")
public class RejectParam {
    @ApiModelProperty("订单id")
    private String id;
    @ApiModelProperty("退款金额")
    private Double returnedMoney;
    @ApiModelProperty("退款余额")
    private Double returnedBalance;
    @ApiModelProperty(value = "退款积分")
    private Integer returnedPoint;
    @ApiModelProperty("退款备注")
    private String returnedRemark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getReturnedMoney() {
        return returnedMoney;
    }

    public void setReturnedMoney(Double returnedMoney) {
        this.returnedMoney = returnedMoney;
    }

    public Double getReturnedBalance() {
        return returnedBalance;
    }

    public void setReturnedBalance(Double returnedBalance) {
        this.returnedBalance = returnedBalance;
    }

    public Integer getReturnedPoint() {
        return returnedPoint;
    }

    public void setReturnedPoint(Integer returnedPoint) {
        this.returnedPoint = returnedPoint;
    }

    public String getReturnedRemark() {
        return returnedRemark;
    }

    public void setReturnedRemark(String returnedRemark) {
        this.returnedRemark = returnedRemark;
    }
}
