package com.framework.module.member.web;

import com.framework.module.member.domain.MemberLevel;
import com.framework.module.member.service.MemberLevelService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "会员等级管理")
@RestController
@RequestMapping("/api/memberLevel")
public class MemberLevelController extends AbstractCrudController<MemberLevel> {
    private final MemberLevelService memberLevelService;
    @Override
    protected CrudService<MemberLevel> getService() {
        return memberLevelService;
    }

    /**
     * 查询会员的等级
     */
    @ApiOperation(value="查询会员的等级")
    @RequestMapping(value = "/member/{memberId}", method = RequestMethod.GET)
    public ResponseEntity<MemberLevel> getMemberMemberLevel(@PathVariable String memberId) throws Exception {
        return new ResponseEntity<>(memberLevelService.getMemberMemberLevel(memberId), HttpStatus.OK);
    }

    @Autowired
    public MemberLevelController(MemberLevelService memberLevelService) {
        this.memberLevelService = memberLevelService;
    }
}
