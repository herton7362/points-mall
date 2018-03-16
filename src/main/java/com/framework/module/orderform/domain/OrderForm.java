package com.framework.module.orderform.domain;

import com.framework.module.marketing.domain.Coupon;
import com.framework.module.member.domain.Member;
import com.framework.module.member.domain.MemberAddress;
import com.kratos.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 * @author tang he
 * @since 1.0.0
 */
@Entity
@ApiModel("订单")
public class OrderForm extends BaseEntity {
    @ApiModelProperty(value = "会员")
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;
    @ApiModelProperty(value = "优惠券")
    @ManyToOne(fetch = FetchType.EAGER)
    private Coupon coupon;
    @ApiModelProperty(value = "会员收货地址")
    @ManyToOne(fetch = FetchType.EAGER)
    private MemberAddress deliverToAddress;
    @ApiModelProperty(value = "订单号，系统自动生成")
    @Column(length = 20)
    private String orderNumber;
    @ApiModelProperty(value = "现金支付")
    @Column(length = 11, precision = 2)
    private Double cash;
    @ApiModelProperty(value = "储值支付")
    @Column(length = 11, precision = 2)
    private Double balance;
    @ApiModelProperty(value = "积分支付")
    private Integer point;
    @ApiModelProperty(value = "订单状态")
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ApiModelProperty(value = "订单条目")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderForm")
    private List<OrderItem> items;
    @ApiModelProperty(value = "买家留言")
    @Column(length = 200)
    private String remark;
    @ApiModelProperty(value = "运单号")
    @Column(length = 36)
    private String shippingCode;
    @ApiModelProperty(value = "发货日期")
    private Long shippingDate;
    @ApiModelProperty(value = "配送状态")
    @Column(length = 20)
    private String shippingStatus;
    @ApiModelProperty(value = "结单日期")
    private Long finishedDate;
    @ApiModelProperty(value = "支付状态")
    @Column(length = 20)
    private PaymentStatus paymentStatus;
    @ApiModelProperty(value = "退款金额")
    @Column(length = 11, precision = 2)
    private Double returnedMoney;
    @ApiModelProperty(value = "退款余额")
    @Column(length = 11, precision = 2)
    private Double returnedBalance;
    @ApiModelProperty(value = "退款积分")
    private Integer returnedPoint;
    @ApiModelProperty(value = "退款备注")
    @Column(length = 500)
    private String returnedRemark;
    @ApiModelProperty(value = "申请退款备注")
    @Column(length = 500)
    private String applyRejectRemark;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public MemberAddress getDeliverToAddress() {
        return deliverToAddress;
    }

    public void setDeliverToAddress(MemberAddress deliverToAddress) {
        this.deliverToAddress = deliverToAddress;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Long getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Long finishedDate) {
        this.finishedDate = finishedDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
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

    public String getApplyRejectRemark() {
        return applyRejectRemark;
    }

    public void setApplyRejectRemark(String applyRejectRemark) {
        this.applyRejectRemark = applyRejectRemark;
    }

    public void addItem(OrderItem item) {
        if(items == null) {
            items = new ArrayList<>();
        }
        item.setOrderForm(this);
        items.add(item);
    }

    public enum PaymentStatus {
        UN_PAY("待支付"), PAYED("已支付");
        private String displayName;
        PaymentStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum OrderStatus {
        UN_PAY("待支付"),
        PAYED("已支付"),
        DELIVERED("已发货"),
        RECEIVED("已收货"),
        APPLY_REJECTED("申请退货"),
        REJECTED("已退货"),
        CANCEL("已取消");
        private String displayName;
        OrderStatus(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }
}
