package com.framework.module.member.service;

import com.kratos.common.CrudService;
import com.framework.module.member.domain.Member;

public interface MemberService extends CrudService<Member> {
    /**
     * 根据登录名获取会员
     * @param loginName 登录名
     * @return {@link Member}
     */
    Member findOneByLoginName(String loginName);
    /**
     * 根据会员卡获取会员
     * @param cardNo 会员卡号
     * @return {@link Member}
     */
    Member findOneByCardNo(String cardNo);

    /**
     * 快速积分
     * @param id 会员id
     * @param point 增加的积分
     */
    void fastIncreasePoint(String id, Integer point) throws Exception;

    /**
     * 增加余额
     * @param id 会员id
     * @param balance 增加的余额
     */
    void increaseBalance(String id, Double balance) throws Exception;

    /**
     * 储值扣费
     * @param memberId 会员id
     * @param amount 扣除的余额
     */
    void deductBalance(String memberId, Double amount) throws Exception;

    /**
     * 查询总数
     * @return 会员总数
     */
    Long count();
}
