package com.framework.module.member.domain;

import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@ApiModel("会员等级")
public class MemberLevel extends BaseEntity {
    @ApiModelProperty(required = true, value = "会员等级名称")
    @Column(length = 30)
    private String name;
    @ApiModelProperty(required = true, value = "所需积分")
    private Integer needPoint;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNeedPoint() {
        return needPoint;
    }

    public void setNeedPoint(Integer needPoint) {
        this.needPoint = needPoint;
    }
}
