package com.framework.module.recharge.web;

import com.framework.module.recharge.service.RechargeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "充值管理")
@RestController
@RequestMapping("/api/recharge")
public class RechargeController {
    private final RechargeService rechargeService;

    /**
     * 充值
     */
    @ApiOperation(value="充值")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> recharge(@RequestBody RechargeParam rechargeParam) throws Exception {
        rechargeService.recharge(rechargeParam.getMemberId(), rechargeParam.getAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    public RechargeController(RechargeService rechargeService) {
        this.rechargeService = rechargeService;
    }
}
