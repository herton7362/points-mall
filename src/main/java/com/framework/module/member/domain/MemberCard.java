package com.framework.module.member.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@ApiModel("会员卡")
public class MemberCard extends BaseEntity {
    @ApiModelProperty(value = "会员卡号")
    @Column(length = 20)
    private String cardNumber;
    @ApiModelProperty(value = "会员卡类型id")
    @ManyToOne(fetch = FetchType.EAGER)
    private MemberCardType memberCardType;
    @ApiModelProperty(value = "会员")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Member member;
    @ApiModelProperty(value = "储值余额")
    @Column(length = 11, precision = 13, scale = 2)
    private Double balance;
    @ApiModelProperty(value = "积分")
    @Column(length = 11)
    private Integer points;
    @ApiModelProperty(value = "折扣")
    @Column(length = 3, precision = 3, scale = 2)
    private Double discount;
    @ApiModelProperty(value = "过期日期")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date expireDate;
    @ApiModelProperty(value = "支付密码")
    private String password;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public MemberCardType getMemberCardType() {
        return memberCardType;
    }

    public void setMemberCardType(MemberCardType memberCardType) {
        this.memberCardType = memberCardType;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
