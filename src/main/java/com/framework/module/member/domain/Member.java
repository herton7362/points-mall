package com.framework.module.member.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kratos.entity.BaseUser;
import com.kratos.module.attachment.domain.Attachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

/**
 * 会员
 * @author tang he
 * @since 1.0.0
 */
@Entity
@ApiModel("会员")
public class Member extends BaseUser {
    @ApiModelProperty(value = "姓名")
    @Column(length = 50)
    private String name;
    @ApiModelProperty(value = "会员卡")
    @Column(length = 20)
    private String cardNo;
    @ApiModelProperty(value = "性别 0-女，1-男")
    @Column(length = 2)
    private String gender;
    @ApiModelProperty(value = "生日")
    private Long birthday;
    @ApiModelProperty(value = "身份证")
    @Column(length = 20)
    private String idCard;
    @ApiModelProperty(value = "微信openid")
    @Column(length = 36)
    private String openid;
    @ApiModelProperty(value = "余额")
    @Column(length = 11, precision = 2)
    private Double balance;
    @ApiModelProperty(value = "积分")
    private Integer point = 0;
    @ApiModelProperty(value = "可用积分")
    private Integer salePoint = 0;
    @ApiModelProperty(value = "区域")
    @Column(length = 20)
    private String address;
    @ApiModelProperty(value = "优惠券")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    @JsonIgnore
    private List<MemberCoupon> coupons;
    @ApiModelProperty(value = "头像")
    @ManyToOne
    private Attachment headPhoto;

    public Member() {
        setUserType(BaseUser.UserType.MEMBER.name());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getSalePoint() {
        return salePoint;
    }

    public void setSalePoint(Integer salePoint) {
        this.salePoint = salePoint;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<MemberCoupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<MemberCoupon> coupons) {
        this.coupons = coupons;
    }

    public Attachment getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(Attachment headPhoto) {
        this.headPhoto = headPhoto;
    }
}
