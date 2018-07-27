package com.framework.module.orderform.service;

import com.framework.module.orderform.domain.OrderForm;
import com.framework.module.orderform.web.ApplyRejectParam;
import com.framework.module.orderform.web.OrderFormResult;
import com.framework.module.orderform.web.RejectParam;
import com.framework.module.orderform.web.SendOutParam;
import com.kratos.common.CrudService;
import com.kratos.common.PageResult;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface OrderFormService extends CrudService<OrderForm> {
    /**
     * 下单
     * @param orderForm 详情请见{@link OrderForm}参数内有注释
     */
    OrderForm makeOrder(OrderForm orderForm);

    /**
     * 获取订单的数量
     * @param memberId 会员
     * @return 订单的数量{UN_PAY: 10, PAYED: 20}形式
     */
    Map<String,Integer> getOrderCounts(String memberId);

    /**
     * 支付
     * @param orderForm 订单参数
     */
    OrderForm pay(OrderForm orderForm) ;

    /**
     * 发货
     * @param sendOutParam 发货参数
     */
    OrderForm saveShippingInfo(SendOutParam sendOutParam);

    /**
     * 确认收货
     * @param id 订单id
     */
    OrderForm receive(String id);

    /**
     * 申请退货
     * @param applyRejectParam 参数
     */
    OrderForm applyReject(ApplyRejectParam applyRejectParam);

    /**
     * 退款
     * @param rejectParam 参数
     */
    OrderForm reject(RejectParam rejectParam);

    /**
     * 获取今日销售额
     * @return 今日销售额
     */
    Double getTodaySale();

    /**
     * 获取本月销售额
     * @return 本月销售额
     */
    Double getMonthSale();

    /**
     * 获取每日销售额
     * @return 每日销售额
     */
    List<Map<String, Object>> getEverydaySale();

    /**
     * 已支付
     * @param outTradeNo 订单id
     */
    void payed(String outTradeNo);

    PageResult<OrderFormResult> findAllTranslated(PageRequest pageRequest, Map<String, String[]> param);

    List<OrderFormResult> findAllTranslated(Map<String, String[]> param);
}
