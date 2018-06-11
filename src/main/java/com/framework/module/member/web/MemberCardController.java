package com.framework.module.member.web;

import com.framework.module.member.domain.MemberCard;
import com.framework.module.member.service.MemberCardService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "会员卡管理")
@RestController
@RequestMapping("/api/memberCard")
public class MemberCardController extends AbstractCrudController<MemberCard> {
    private final MemberCardService memberCardService;
    @Override
    protected CrudService<MemberCard> getService() {
        return memberCardService;
    }

    @Autowired
    public MemberCardController(MemberCardService memberCardService) {
        this.memberCardService = memberCardService;
    }
}
