package com.framework.module.marketing.service;

import com.framework.module.marketing.domain.Coupon;
import com.kratos.common.CrudService;

import java.util.List;

public interface CouponService extends CrudService<Coupon> {
    /**
     * 获取未获取的优惠券
     * @param memberId 会员id
     * @return 获取到的优惠券
     */
    List<Coupon> getUnClaimed(String memberId) throws Exception;

    /**
     * 检验当前优惠券是否可用
     * @param couponId 优惠券id
     * @param amount 消费金额
     */
    void validCouponUseAble(String couponId, Double amount) throws Exception;

    /**
     * 使用优惠券，会将优惠券状态改为已使用
     * @param couponId 优惠券id
     * @param memberId 会员id
     * @param amount 优惠前金额
     * @return 优惠后的金额
     */
    Double useCoupon(String couponId, String memberId, Double amount) throws Exception;

    /**
     * 领取优惠券
     * @param memberId 会员id
     * @param coupon 优惠券
     */
    void claim(String memberId, Coupon coupon) throws Exception;
}
