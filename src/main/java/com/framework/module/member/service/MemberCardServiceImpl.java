package com.framework.module.member.service;

import com.framework.module.member.domain.Member;
import com.framework.module.member.domain.MemberCard;
import com.framework.module.member.domain.MemberCardRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.exceptions.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MemberCardServiceImpl extends AbstractCrudService<MemberCard> implements MemberCardService {
    private final MemberCardRepository memberCardRepository;
    private final MemberService memberService;
    @Override
    protected PageRepository<MemberCard> getRepository() {
        return memberCardRepository;
    }

    @Override
    public MemberCard save(MemberCard memberCard) throws Exception {
        Member member = memberService.findOneByCardNo(memberCard.getCardNumber());
        if(member != null && ((StringUtils.isBlank(memberCard.getId())
                && memberCard.getMember().getId().equals(member.getId())) ||
                !memberCard.getMember().getId().equals(member.getId()))) {
            throw new BusinessException("会员卡号重复，请使用其他会员卡号");
        }
        return super.save(memberCard);
    }

    @Lazy
    @Autowired
    public MemberCardServiceImpl(
            MemberCardRepository memberCardRepository,
            MemberService memberService
    ) {
        this.memberCardRepository = memberCardRepository;
        this.memberService = memberService;
    }
}
