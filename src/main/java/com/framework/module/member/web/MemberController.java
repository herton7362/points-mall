package com.framework.module.member.web;

import com.framework.module.member.domain.Member;
import com.framework.module.member.service.MemberService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "会员管理")
@RestController
@RequestMapping("/api/member")
public class MemberController extends AbstractCrudController<Member> {
    private final MemberService memberService;
    @Override
    protected CrudService<Member> getService() {
        return memberService;
    }

    /**
     * 根据会员卡获取会员
     */
    @ApiOperation(value="根据会员卡获取会员")
    @RequestMapping(value = "/cardNo/{cardNo}", method = RequestMethod.GET)
    public ResponseEntity<Member> getOneByCardNo(@PathVariable String cardNo) throws Exception {
        Member member = memberService.findOneByCardNo(cardNo);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    /**
     * 快速积分
     */
    @ApiOperation(value="快速积分")
    @RequestMapping(value = "/fastIncreasePoint", method = RequestMethod.POST)
    public ResponseEntity<?> fastIncreasePoint(@RequestBody FastIncreasePointParam param) throws Exception {
        memberService.fastIncreasePoint(param.getMemberId(), param.getPoint());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 储值扣费
     */
    @ApiOperation(value="储值扣费")
    @RequestMapping(value = "/deductBalance", method = RequestMethod.POST)
    public ResponseEntity<?> deductBalance(@RequestBody DeductBalanceParam param) throws Exception {
        memberService.deductBalance(param.getMemberId(), param.getAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 获取会员优惠卷
     */
    @ApiOperation(value="获取会员优惠卷")
    @RequestMapping(value = "/coupon/{memberId}", method = RequestMethod.GET)
    public ResponseEntity<List<CouponResult>> getCoupons(@PathVariable String memberId) throws Exception {
        Member member = memberService.findOne(memberId);
        final List<CouponResult> coupons = new ArrayList<>();
        if(member.getCoupons() != null) {
            member.getCoupons().forEach(memberCoupon -> {
                CouponResult couponResult = new CouponResult();
                BeanUtils.copyProperties(memberCoupon.getCoupon(), couponResult);
                coupons.add(couponResult);
            });
        }
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    /**
     * 查询总数
     */
    @ApiOperation(value="查询总数")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResponseEntity<Long> count() throws Exception {
        return new ResponseEntity<>(memberService.count(), HttpStatus.OK);
    }

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}