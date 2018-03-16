package com.framework.module.member.service;

import com.framework.module.member.domain.MemberAddress;
import com.kratos.common.CrudService;

public interface MemberAddressService extends CrudService<MemberAddress> {
    /**
     * 修改默认地址
     * @param id 会员地址id
     */
    void changeDefaultAddress(String id);
}
