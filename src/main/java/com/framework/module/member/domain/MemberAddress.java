package com.framework.module.member.domain;

import com.kratos.entity.BaseEntity;
import com.kratos.module.address.domain.Address;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 会员地址
 * @author tang he
 * @since 1.0.0
 */
@Entity
@ApiModel("会员地址")
public class MemberAddress extends BaseEntity {
    @ApiModelProperty("收货人")
    @Column(length = 50)
    private String name;
    @ApiModelProperty("电话")
    @Column(length = 20)
    private String tel;
    @ApiModelProperty("详细地址")
    @Column(length = 500)
    private String detailAddress;
    @ApiModelProperty("邮政编码")
    @Column(length = 20)
    private String postalCode;
    @ApiModelProperty("所属会员")
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;
    @ApiModelProperty("地址")
    @ManyToOne(fetch = FetchType.EAGER)
    private Address address;
    @ApiModelProperty("默认地址")
    private Boolean defaultAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Boolean getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(Boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
