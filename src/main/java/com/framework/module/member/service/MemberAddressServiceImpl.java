package com.framework.module.member.service;

import com.framework.module.member.domain.MemberAddress;
import com.framework.module.member.domain.MemberAddressRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MemberAddressServiceImpl extends AbstractCrudService<MemberAddress> implements MemberAddressService {
    private final MemberAddressRepository memberAddressRepository;
    @Override
    protected PageRepository<MemberAddress> getRepository() {
        return memberAddressRepository;
    }

    @Override
    public MemberAddress save(MemberAddress memberAddress) throws Exception {
        if(memberAddress.getMember() == null) {
            throw new BusinessException("会员不能为空");
        }
        memberAddressRepository.clearDefaultAddress(memberAddress.getMember().getId());
        memberAddress.setDefaultAddress(true);
        return super.save(memberAddress);
    }

    @Override
    public void changeDefaultAddress(String id) {
        MemberAddress memberAddress = memberAddressRepository.findOne(id);
        memberAddress.setDefaultAddress(true);
        memberAddressRepository.clearDefaultAddress(memberAddress.getMember().getId());
        memberAddressRepository.save(memberAddress);
    }

    @Autowired
    public MemberAddressServiceImpl(MemberAddressRepository memberAddressRepository) {
        this.memberAddressRepository = memberAddressRepository;
    }
}
