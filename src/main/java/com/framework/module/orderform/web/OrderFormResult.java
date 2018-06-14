package com.framework.module.orderform.web;

import com.framework.module.member.domain.MemberCard;
import com.framework.module.orderform.domain.OrderForm;

public class OrderFormResult extends OrderForm {
    private MemberCard memberCard;

    public MemberCard getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(MemberCard memberCard) {
        this.memberCard = memberCard;
    }
}
