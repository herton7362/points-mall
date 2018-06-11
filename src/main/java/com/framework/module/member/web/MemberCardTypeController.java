package com.framework.module.member.web;

import com.framework.module.member.domain.MemberCardType;
import com.framework.module.member.service.MemberCardTypeService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "会员卡类型管理")
@RestController
@RequestMapping("/api/memberCardType")
public class MemberCardTypeController extends AbstractCrudController<MemberCardType> {
    private final MemberCardTypeService memberCardTypeService;
    @Override
    protected CrudService<MemberCardType> getService() {
        return memberCardTypeService;
    }

    @Autowired
    public MemberCardTypeController(MemberCardTypeService memberCardTypeService) {
        this.memberCardTypeService = memberCardTypeService;
    }
}
