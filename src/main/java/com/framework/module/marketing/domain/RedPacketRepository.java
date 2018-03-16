package com.framework.module.marketing.domain;

import com.framework.module.marketing.domain.RedPacket;
import com.kratos.common.PageRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RedPacketRepository extends PageRepository<RedPacket> {
    @Query("select r from RedPacket r where r.minAmount <= ?1 and r.startDate <= ?2 and r.endDate >= ?2 and r.logicallyDeleted=false")
    List<RedPacket> matchRedPackets(Double amount, Long now);
}
