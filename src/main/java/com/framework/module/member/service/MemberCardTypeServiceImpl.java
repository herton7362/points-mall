package com.framework.module.member.service;

import com.framework.module.member.domain.MemberCardType;
import com.framework.module.member.domain.MemberCardTypeRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MemberCardTypeServiceImpl extends AbstractCrudService<MemberCardType> implements MemberCardTypeService {
    private final MemberCardTypeRepository memberCardTypeRepository;
    @Override
    protected PageRepository<MemberCardType> getRepository() {
        return memberCardTypeRepository;
    }

    @Autowired
    public MemberCardTypeServiceImpl(MemberCardTypeRepository memberCardTypeRepository) {
        this.memberCardTypeRepository = memberCardTypeRepository;
    }
}
