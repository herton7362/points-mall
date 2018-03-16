package com.framework.module.marketing.web;

import com.framework.module.marketing.domain.RedPacket;
import com.framework.module.marketing.service.RedPacketService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "红包管理")
@RestController
@RequestMapping("/api/redPacket")
public class RedPacketController extends AbstractCrudController<RedPacket> {
    private final RedPacketService redPacketService;
    @Override
    protected CrudService<RedPacket> getService() {
        return redPacketService;
    }

    @Autowired
    public RedPacketController(RedPacketService redPacketService) {
        this.redPacketService = redPacketService;
    }
}
