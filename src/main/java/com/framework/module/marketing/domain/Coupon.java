package com.framework.module.marketing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.framework.module.member.domain.MemberCoupon;
import com.kratos.entity.BaseEntity;
import com.kratos.module.auth.domain.OauthClientDetails;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@ApiModel(value = "优惠券")
public class Coupon extends BaseEntity {
    @ApiModelProperty(required = true, value = "优惠类型")
    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private MarketingType marketingType = MarketingType.CASH_OFF;
    @ApiModelProperty("名称")
    @Column(length = 50)
    private String name;
    @ApiModelProperty(required = true, value = "面额")
    @Column(length = 11, precision = 2)
    private Double amount;
    @ApiModelProperty(required = true, value = "使用条件：满 ? 元，无门槛请输入0")
    @Column(length = 11, precision = 2)
    private Double minAmount;
    @ApiModelProperty(required = true, value = "开始时间")
    private Long startDate;
    @ApiModelProperty(required = true, value = "结束时间")
    private Long endDate;
    @ApiModelProperty(required = true, value = "获取方式")
    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private ObtainType obtainType;
    @ApiModelProperty(required = true, value = "备注")
    @Column(length = 500)
    private String remark;
    @ApiModelProperty(value = "会员")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coupon")
    @JsonIgnore
    private List<MemberCoupon> members;

    public MarketingType getMarketingType() {
        return marketingType;
    }

    public void setMarketingType(MarketingType marketingType) {
        this.marketingType = marketingType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public ObtainType getObtainType() {
        return obtainType;
    }

    public void setObtainType(ObtainType obtainType) {
        this.obtainType = obtainType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<MemberCoupon> getMembers() {
        return members;
    }

    public void setMembers(List<MemberCoupon> members) {
        this.members = members;
    }

    public enum MarketingType {
        CASH_OFF("满减"),
        DISCOUNT("折扣");
        private String displayName;

        MarketingType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum ObtainType {
        LOGIN("发放");
        private String displayName;

        ObtainType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
