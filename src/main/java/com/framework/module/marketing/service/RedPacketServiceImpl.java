package com.framework.module.marketing.service;

import com.framework.module.marketing.domain.RedPacket;
import com.framework.module.marketing.domain.RedPacketRepository;
import com.framework.module.marketing.service.RedPacketService;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class RedPacketServiceImpl extends AbstractCrudService<RedPacket> implements RedPacketService {
    private final RedPacketRepository redPacketRepository;
    @Override
    protected PageRepository<RedPacket> getRepository() {
        return redPacketRepository;
    }

    @Autowired
    public RedPacketServiceImpl(RedPacketRepository redPacketRepository) {
        this.redPacketRepository = redPacketRepository;
    }
}
