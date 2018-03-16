package com.framework.module.orderform.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("发货参数")
public class SendOutParam {
    @ApiModelProperty("订单主键")
    private String id;
    @ApiModelProperty("运单号")
    private String shippingCode;
    @ApiModelProperty("发货日期")
    private Long shippingDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public Long getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Long shippingDate) {
        this.shippingDate = shippingDate;
    }
}
