package com.framework.module.orderform.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("申请退货")
public class ApplyRejectParam {
    @ApiModelProperty("订单id")
    private String id;
    @ApiModelProperty("申请退货原因")
    private String applyRejectRemark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyRejectRemark() {
        return applyRejectRemark;
    }

    public void setApplyRejectRemark(String applyRejectRemark) {
        this.applyRejectRemark = applyRejectRemark;
    }
}
