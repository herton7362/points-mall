package com.framework.module.alibaba;

import com.framework.module.orderform.service.OrderFormService;
import com.kratos.common.utils.StringUtils;
import com.kratos.kits.alipay.AliPayAPI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Api(value = "支付宝接口")
@RestController
@RequestMapping("/ali")
public class AliController {
    private final Logger LOG = LoggerFactory.getLogger(AliController.class);
    private final OrderFormService orderFormService;
    private final AliPayAPI aliPayAPI;

    /**
     * 支付成功通知
     */
    @ApiOperation(value="支付成功通知")
    @RequestMapping(value = "/pay/notify", method = RequestMethod.POST)
    public void payNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String out_trade_no = request.getParameter("out_trade_no");
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = new HashMap<>();
        LOG.info("********************** 支付宝【订单】支付回调，订单【" + out_trade_no + "】，开始 **********************");
        try {
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++)
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                params.put(name, valueStr);
                LOG.info("支付宝【订单】支付回调，订单【" + out_trade_no + "】，参数：" + name + "， 值：" + valueStr);
            }

            // TODO 参数校验

            // 支付宝交易号,该交易在支付宝系统中的交易流水号
            String trade_no = request.getParameter("trade_no");
            // 买家支付宝用户号
            String buyer_id = request.getParameter("buyer_id");
            // 交易金额
            String total_amount = request.getParameter("total_amount");
            // 支付时间
            String gmt_payment = request.getParameter("gmt_payment");
            // 交易状态
            String trade_status = request.getParameter("trade_status");
            if(StringUtils.isBlank(trade_no)) {
                LOG.info("支付宝【订单】支付回调，订单【" + out_trade_no + "】，支付宝交易号错误");
                messageFail(response);
                return;
            } else if(StringUtils.isBlank(buyer_id)) {
                LOG.info("支付宝【订单】支付回调，订单【" + out_trade_no + "】，买家支付宝用户号错误");
                messageFail(response);
                return;
            } else if(StringUtils.isBlank(total_amount)) {
                LOG.info("支付宝【订单】支付回调，订单【" + out_trade_no + "】，订单金额格式错误");
                messageFail(response);
                return;
            } else if(StringUtils.isBlank(trade_status)) {
                LOG.info("支付宝【订单】支付回调，订单【" + out_trade_no + "】，订单交易状态错误");
                messageFail(response);
                return;
            }
            LOG.info("支付宝【订单】支付回调，订单【" + out_trade_no + "】，开始验证 " );
            boolean tag = aliPayAPI.verifyPayNotification(params);
            LOG.info("支付宝【订单】支付回调，订单【" + out_trade_no + "】，verify tag " + tag);
            if(tag) {
                if("TRADE_SUCCESS".equals(params.get("trade_status"))) { // 支付
                    orderFormService.payed(out_trade_no);
                } else if("TRADE_CLOSED".equals(params.get("trade_status"))) { // 退款
                    // TODO
                }

                messageSuccess(response);
            } else {
                LOG.info("支付宝【订单】支付回调，订单【" + out_trade_no + "】，verify：失败");
                messageFail(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
             LOG.info("支付宝【订单】支付回调，订单【" + out_trade_no + "】，发生异常：" + e.getMessage());
            messageFail(response);
        } finally {
            LOG.info("********************** 支付宝【订单】支付回调，订单【" + out_trade_no + "】，结束 **********************");
            LOG.info("");
        }
    }

    private void messageFail(HttpServletResponse response) throws IOException {
        response.getWriter().print("fail");
    }

    private void messageSuccess(HttpServletResponse response) throws IOException {
        response.getWriter().print("success");
    }


    @Autowired
    public AliController(
            OrderFormService orderFormService,
            AliPayAPI aliPayAPI
    ) {
        this.orderFormService = orderFormService;
        this.aliPayAPI = aliPayAPI;
    }
}
