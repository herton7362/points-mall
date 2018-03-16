package com.framework.module.auth;

import com.framework.module.member.domain.MemberRepository;
import com.kratos.entity.BaseUser;
import com.kratos.module.auth.JdbcUserDetailService;
import com.kratos.module.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class ExtendedJdbcUserDetailService extends JdbcUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Autowired
    public ExtendedJdbcUserDetailService(UserService adminService, MemberRepository memberRepository) {
        super(adminService);
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = super.loadUserByUsername(username);
        if(userDetails == null) {
            BaseUser user = memberRepository.findOneByLoginName(username);
            return new User(username, user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getUserType())));
        } else {
            return userDetails;
        }
    }
}
