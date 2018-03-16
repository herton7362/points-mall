package com.framework.module.member.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("快速积分")
public class FastIncreasePointParam {
    @ApiModelProperty(value = "会员主键")
    private String memberId;
    @ApiModelProperty(value = "积分")
    private Integer point;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
