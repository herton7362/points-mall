package com.framework.module.member.service;

import com.framework.module.member.domain.MemberLevel;
import com.kratos.common.CrudService;

public interface MemberLevelService extends CrudService<MemberLevel> {
    /**
     * 获取会员的会员等级
     * @param memberId 会员id
     * @return 会员等级
     */
    MemberLevel getMemberMemberLevel(String memberId) throws Exception;
}
