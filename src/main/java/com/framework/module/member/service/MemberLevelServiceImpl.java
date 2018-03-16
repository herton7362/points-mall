package com.framework.module.member.service;

import com.framework.module.member.domain.Member;
import com.framework.module.member.domain.MemberLevel;
import com.framework.module.member.domain.MemberLevelRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class MemberLevelServiceImpl extends AbstractCrudService<MemberLevel> implements MemberLevelService {
    private final MemberLevelRepository memberLevelRepository;
    private final MemberService memberService;
    @Override
    protected PageRepository<MemberLevel> getRepository() {
        return memberLevelRepository;
    }

    @Override
    public MemberLevel getMemberMemberLevel(String memberId) throws Exception {
        Member member = memberService.findOne(memberId);
        List<MemberLevel> memberLevels = (List<MemberLevel>) memberLevelRepository.findAll();
        Optional<MemberLevel> optional = memberLevels
                .stream()
                .filter(memberLevel -> memberLevel.getNeedPoint() < member.getPoint())
                .sorted(Comparator.comparing(MemberLevel::getNeedPoint).reversed())
                .findFirst();
        return optional.isPresent()? optional.get(): null;
    }

    @Autowired
    public MemberLevelServiceImpl(
            MemberLevelRepository memberLevelRepository,
            MemberService memberService
    ) {
        this.memberLevelRepository = memberLevelRepository;
        this.memberService = memberService;
    }
}
