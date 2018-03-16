package com.framework.module.auth;

import com.kratos.common.AbstractLoginController;
import com.kratos.common.AbstractLoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Api("登录相关")
@RestController
public class LoginController extends AbstractLoginController {
    @Autowired
    public LoginController(AbstractLoginService loginService) {
        super(loginService);
    }
}
