package com.framework.module.marketing.service;

import com.framework.module.auth.MemberThread;
import com.framework.module.marketing.domain.Coupon;
import com.framework.module.marketing.domain.CouponRepository;
import com.framework.module.member.domain.Member;
import com.framework.module.member.domain.MemberCoupon;
import com.framework.module.member.service.MemberService;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.exceptions.BusinessException;
import com.kratos.module.auth.UserThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class CouponServiceImpl extends AbstractCrudService<Coupon> implements CouponService {
    private final CouponRepository couponRepository;
    private final MemberService memberService;
    @Override
    protected PageRepository<Coupon> getRepository() {
        return couponRepository;
    }

    @Override
    public Coupon save(Coupon coupon) throws Exception {
        if(coupon.getClientId() == null) {
            coupon.setClientId(MemberThread.getInstance().getClientId());
        }
        if(coupon.getObtainType() == null) {
            coupon.setObtainType(Coupon.ObtainType.LOGIN);
        }
        return super.save(coupon);
    }

    @Override
    public List<Coupon> getUnClaimed(String memberId) throws Exception {
        final Member member = memberService.findOne(memberId);
        final List<MemberCoupon> coupons = member.getCoupons();
        final String clientId = MemberThread.getInstance().getClientId();
        // 匹配规则，查询活动期间内，当前登录系统，用户没有获取过的，有效的优惠券
        List<Coupon> newCoupons = couponRepository.findAll((Root<Coupon> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicate = new ArrayList<>();
            predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), new Date().getTime()));
            predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), new Date().getTime()));
            predicate.add(criteriaBuilder.equal(root.get("logicallyDeleted"), false));
            predicate.add(criteriaBuilder.equal(root.get("clientId"), clientId));
            return criteriaBuilder.and(predicate.toArray(new Predicate[]{}));
        });

        newCoupons = newCoupons
                .stream()
                .filter(coupon -> {
                    for (MemberCoupon memberCoupon : coupons) {
                        if(memberCoupon.getCoupon().getId().equals(coupon.getId())) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());
        return newCoupons;
    }

    @Override
    public void validCouponUseAble(String couponId, Double amount) throws Exception {
        Coupon coupon = couponRepository.findOne(couponId);
        String clientId = UserThread.getInstance().getClientId();
        if(!clientId.equals(coupon.getClientId())) {
            throw new BusinessException("优惠券不属于当前系统");
        }
        // 如果优惠券策略是满减
        if(coupon.getMarketingType() == Coupon.MarketingType.CASH_OFF) {
            if(coupon.getMinAmount() > amount) {
                throw new BusinessException("当前优惠券不可用");
            }
        }
    }

    @Override
    public Double useCoupon(final String couponId, String memberId, Double amount) throws Exception {
        validCouponUseAble(couponId, amount);
        Coupon coupon = couponRepository.findOne(couponId);
        Member member = memberService.findOne(memberId);
        BigDecimal newAmount = new BigDecimal(amount).subtract(new BigDecimal(coupon.getAmount()));
        List<MemberCoupon> coupons = member.getCoupons();
        coupons.forEach(memberCoupon -> {
            if(memberCoupon.getCoupon().getId().equals(couponId)) {
                memberCoupon.setUsed(true);
            }
        });
        return newAmount.doubleValue();
    }

    @Override
    public void claim(String memberId, Coupon coupon) throws Exception {
        Member member = memberService.findOne(memberId);
        List<MemberCoupon> memberCoupons = member.getCoupons();
        MemberCoupon memberCoupon = new MemberCoupon();
        memberCoupon.setUsed(false);
        memberCoupon.setMember(member);
        memberCoupon.setCoupon(coupon);
        memberCoupons.add(memberCoupon);
    }

    @Autowired
    public CouponServiceImpl(
            CouponRepository couponRepository,
            MemberService memberService
    ) {
        this.couponRepository = couponRepository;
        this.memberService = memberService;
    }
}
