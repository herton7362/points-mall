package com.framework.module.recharge.service;

public interface RechargeService {

    /**
     * 给会员充值
     * @param memberId 会员id
     * @param amount 金额
     */
    void recharge(String memberId, Double amount) throws Exception;
}
