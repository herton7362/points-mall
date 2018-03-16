package com.framework.module.marketing.web;

import com.framework.module.auth.MemberThread;
import com.framework.module.marketing.domain.Coupon;
import com.framework.module.marketing.service.CouponService;
import com.framework.module.member.domain.Member;
import com.framework.module.member.domain.MemberCoupon;
import com.framework.module.member.service.MemberService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import com.kratos.module.auth.domain.Module;
import com.kratos.module.auth.service.OauthClientDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "优惠券管理")
@RestController
@RequestMapping("/api/coupon")
public class CouponController extends AbstractCrudController<Coupon> {
    private final CouponService couponService;
    private final OauthClientDetailsService oauthClientDetailsService;
    private final MemberService memberService;
    @Override
    protected CrudService<Coupon> getService() {
        return couponService;
    }

    /**
     * 保存
     */
    @ApiOperation(value="保存")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Coupon> save(@RequestBody Coupon coupon) throws Exception {
        coupon = couponService.save(coupon);
        return new ResponseEntity<>(coupon, HttpStatus.OK);
    }

    /**
     * 获取未获取的优惠券
     */
    @ApiOperation(value="获取未获取的优惠券")
    @RequestMapping(value = "/unClaimed", method = RequestMethod.GET)
    public ResponseEntity<List<Coupon>> getUnClaimed() throws Exception {
        List<Coupon> coupons = couponService.getUnClaimed(MemberThread.getInstance().get().getId());
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    /**
     * 获取当前用户优惠券数量
     */
    @ApiOperation(value="获取当前用户优惠券数量")
    @RequestMapping(value = "/count/{memberId}", method = RequestMethod.GET)
    public ResponseEntity<Integer> count(@PathVariable String memberId) throws Exception {
        Member member = memberService.findOne(memberId);
        Integer count = 0;
        for (MemberCoupon memberCoupon : member.getCoupons()) {
            if(!memberCoupon.getUsed()) {
                count ++;
            }
        }
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    /**
     * 获取当前用户优惠券
     */
    @ApiOperation(value="获取当前用户优惠券")
    @RequestMapping(value = "/member/{memberId}", method = RequestMethod.GET)
    public ResponseEntity<List<MemberCoupon>> getMemberCoupons(@PathVariable String memberId) throws Exception {
        Member member = memberService.findOne(memberId);
        return new ResponseEntity<>(member.getCoupons(), HttpStatus.OK);
    }

    /**
     * 领取优惠券
     */
    @ApiOperation(value="领取优惠券")
    @RequestMapping(value = "/claim", method = RequestMethod.POST)
    public ResponseEntity<?> claim(@RequestBody Coupon coupon) throws Exception {
        couponService.claim(MemberThread.getInstance().get().getId(), coupon);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    public CouponController(
            CouponService couponService,
            OauthClientDetailsService oauthClientDetailsService,
            MemberService memberService
    ) {
        this.couponService = couponService;
        this.oauthClientDetailsService = oauthClientDetailsService;
        this.memberService = memberService;
    }
}
