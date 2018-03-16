package com.framework.module.auth;


import com.kratos.module.auth.UserThread;
import com.framework.module.member.domain.Member;

public class MemberThread extends UserThread<Member> {
    private static MemberThread instance;

    private MemberThread() {
    }

    public static MemberThread getInstance() {
        if(instance == null) {
            instance = new MemberThread();
        }
        return instance;
    }
    public Member get() {
        return super.get();
    }
}
