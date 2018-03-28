package com.framework.module.wechat;

import com.framework.module.orderform.service.OrderFormService;
import com.kratos.common.utils.XmlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@Api(value = "微信接口")
@RestController
@RequestMapping("/wechat")
public class WechatController {
    private final Logger LOG = LoggerFactory.getLogger(WechatController.class);
    private final OrderFormService orderFormService;

    /**
     * 支付成功通知
     */
    @ApiOperation(value="支付成功通知")
    @RequestMapping(value = "/pay/notify", method = RequestMethod.POST)
    public void payNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletInputStream in = request.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String reqData = "";
        String itemStr = "";// 作为输出字符串的临时串，用于判断是否读取完毕
        while (null != (itemStr = reader.readLine())) {
            reqData += itemStr;
        }
        LOG.debug("pay notify result: {}", reqData);
        String outTradeNo = (String) XmlUtils.xmltoMap(reqData).get("out_trade_no");
        orderFormService.payed(outTradeNo);
        String resultMsg = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";
        PrintWriter out = response.getWriter();
        out.write(resultMsg);
        out.flush();
        out.close();
    }

    @Autowired
    public WechatController(OrderFormService orderFormService) {
        this.orderFormService = orderFormService;
    }
}
