package com.framework.module.orderform.web;

import com.framework.module.orderform.domain.OrderForm;
import com.framework.module.orderform.service.OrderFormService;
import com.kratos.common.AbstractCrudController;
import com.kratos.common.CrudService;
import com.kratos.kits.wechat.WeChatAPI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "订单管理")
@RestController
@RequestMapping("/api/orderForm")
public class OrderFormController extends AbstractCrudController<OrderForm> {
    private final OrderFormService orderFormService;
    private final WeChatAPI weChatAPI;
    @Override
    protected CrudService<OrderForm> getService() {
        return orderFormService;
    }

    /**
     * 下订单
     */
    @ApiOperation(value="下订单")
    @RequestMapping(value = "/makeOrder", method = RequestMethod.POST)
    public ResponseEntity<OrderForm> makeOrder(@RequestBody OrderForm orderForm) throws Exception {
        return new ResponseEntity<>(orderFormService.makeOrder(orderForm), HttpStatus.OK);
    }

    /**
     * 支付
     */
    @ApiOperation(value="支付")
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResponseEntity<OrderForm> pay(@RequestBody OrderForm orderForm) throws Exception {
        return new ResponseEntity<>(orderFormService.pay(orderForm), HttpStatus.OK);
    }

    /**
     * 获取订单数量
     */
    @ApiOperation(value="获取订单数量")
    @RequestMapping(value = "/count/{memberId}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Integer>> getOrderCounts(@PathVariable String memberId) throws Exception {
        return new ResponseEntity<>(orderFormService.getOrderCounts(memberId), HttpStatus.OK);
    }

    /**
     * 获取订单所有状态及对应编码
     */
    @ApiOperation(value="获取订单所有状态及对应编码")
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, String>>> getOrderStatus() throws Exception {
        List<Map<String, String>> result = new ArrayList<>();
        Map<String, String> map;
        OrderForm.OrderStatus[] orderStatuses = OrderForm.OrderStatus.values();
        for (OrderForm.OrderStatus orderStatus : orderStatuses) {
            map = new HashMap<>();
            map.put("id", orderStatus.name().toLowerCase());
            map.put("text", orderStatus.getDisplayName());
            result.add(map);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 发货
     */
    @ApiOperation(value="发货")
    @RequestMapping(value = "/sendOut", method = RequestMethod.POST)
    public ResponseEntity<OrderForm> sendOut(@RequestBody SendOutParam sendOutParam) throws Exception {
        return new ResponseEntity<>(orderFormService.saveShippingInfo(sendOutParam), HttpStatus.OK);
    }

    /**
     * 确认收货
     */
    @ApiOperation(value="确认收货")
    @RequestMapping(value = "/receive/{id}", method = RequestMethod.POST)
    public ResponseEntity<OrderForm> receive(@PathVariable String id) throws Exception {
        return new ResponseEntity<>(orderFormService.receive(id), HttpStatus.OK);
    }

    /**
     * 申请退货
     */
    @ApiOperation(value="申请退货")
    @RequestMapping(value = "/applyReject", method = RequestMethod.POST)
    public ResponseEntity<OrderForm> applyReject(@RequestBody ApplyRejectParam applyRejectParam) throws Exception {
        return new ResponseEntity<>(orderFormService.applyReject(applyRejectParam), HttpStatus.OK);
    }

    /**
     * 申请退货
     */
    @ApiOperation(value="申请退货")
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public ResponseEntity<OrderForm> reject(@RequestBody RejectParam rejectParam) throws Exception {
        return new ResponseEntity<>(orderFormService.reject(rejectParam), HttpStatus.OK);
    }

    /**
     * 今日销售额
     */
    @ApiOperation(value="今日销售额")
    @RequestMapping(value = "/todaySale", method = RequestMethod.GET)
    public ResponseEntity<Double> getTodaySale() throws Exception {
        Double result = orderFormService.getTodaySale();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 本月销售额
     */
    @ApiOperation(value="本月销售额")
    @RequestMapping(value = "/monthSale", method = RequestMethod.GET)
    public ResponseEntity<Double> getMonthSale() throws Exception {
        Double result = orderFormService.getMonthSale();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 每日销售额
     */
    @ApiOperation(value="每日销售额")
    @RequestMapping(value = "/everydaySale", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> getEverydaySale() throws Exception {
        List<Map<String, Object>> result = orderFormService.getEverydaySale();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 获取预付订单
     */
    @ApiOperation(value="获取预付订单")
    @RequestMapping(value = "/unified", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> unified(@RequestBody OrderForm orderForm, HttpServletRequest request) throws Exception {
        return new ResponseEntity<>(weChatAPI.makeAppUnifiedOrder(orderForm.getOrderNumber(), request, ((Double)(orderForm.getCash()*100D)).intValue()), HttpStatus.OK);
    }

    @Autowired
    public OrderFormController(
            OrderFormService orderFormService,
            WeChatAPI weChatAPI
    ) {
        this.orderFormService = orderFormService;
        this.weChatAPI = weChatAPI;
    }
}
