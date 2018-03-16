package com.framework.module.recharge.service;

import com.framework.module.marketing.domain.RedPacket;
import com.framework.module.marketing.domain.RedPacketRepository;
import com.framework.module.auth.MemberThread;
import com.kratos.module.auth.service.OauthClientDetailsService;
import com.framework.module.member.service.MemberService;
import com.framework.module.record.domain.OperationRecord;
import com.framework.module.record.service.OperationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class RechargeServiceImpl implements RechargeService {
    private final OperationRecordService operationRecordService;
    private final MemberService memberService;
    private final RedPacketRepository redPacketRepository;
    private final OauthClientDetailsService oauthClientDetailsService;

    @Override
    public void recharge(String memberId, Double amount) throws Exception {
        Double reward = calculateReward(amount);
        Assert.notNull(reward, "reward is empty");
        record(memberId, amount, reward);
        memberService.increaseBalance(memberId,
                new BigDecimal(amount).add(new BigDecimal(reward)).doubleValue());
    }

    /**
     * 计算奖金有多少如红包
     * @return 得出的奖金
     */
    private Double calculateReward(Double amount) {
        List<RedPacket> redPackets = redPacketRepository.matchRedPackets(amount, new Date().getTime());
        BigDecimal reward = new BigDecimal(0D);
        for (RedPacket redPacket : redPackets) {
            reward = new BigDecimal(redPacket.getAmount()).add(reward);
        }
        return reward.doubleValue();
    }

    /**
     * 记录充值记录
     * @param memberId 会员id
     * @param amount 充值金额
     * @param extAmount 赠送金额
     */
    private void record(String memberId, Double amount, Double extAmount) throws Exception {
        OperationRecord rechargeRecord = new OperationRecord();
        rechargeRecord.setMember(memberService.findOne(memberId));
        rechargeRecord.setBusinessType(OperationRecord.BusinessType.RECHARGE.name());
        rechargeRecord.setClientId(MemberThread.getInstance().getClientId());
        rechargeRecord.setIpAddress(MemberThread.getInstance().getIpAddress());
        rechargeRecord.setContent(String.format("会员充值金额%s，其中赠送金额为%s", amount, extAmount));
        operationRecordService.save(rechargeRecord);
    }

    @Autowired
    public RechargeServiceImpl(
            OperationRecordService operationRecordService,
            MemberService memberService,
            RedPacketRepository redPacketRepository,
            OauthClientDetailsService oauthClientDetailsService
    ) {
        this.operationRecordService = operationRecordService;
        this.memberService = memberService;
        this.redPacketRepository = redPacketRepository;
        this.oauthClientDetailsService = oauthClientDetailsService;
    }
}
