package com.framework.module.member.web;

import com.framework.module.member.domain.MemberAddress;
import com.framework.module.member.service.MemberAddressService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "会员地址管理")
@RestController
@RequestMapping("/api/memberAddress")
public class MemberAddressController extends AbstractCrudController<MemberAddress> {
    private final MemberAddressService memberAddressService;
    @Override
    protected CrudService<MemberAddress> getService() {
        return memberAddressService;
    }

    /**
     * 修改默认地址
     */
    @ApiOperation(value="修改默认地址")
    @RequestMapping(value = "/defaultAddress/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> changeDefaultAddress(@PathVariable String id) throws Exception {
        memberAddressService.changeDefaultAddress(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    public MemberAddressController(MemberAddressService memberAddressService) {
        this.memberAddressService = memberAddressService;
    }
}
