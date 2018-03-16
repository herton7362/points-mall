package com.framework.module.orderform.domain;

import com.kratos.common.PageRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderFormRepository extends PageRepository<OrderForm> {
    @Query("select count(o) from OrderForm o where o.status = ?1 and o.member.id = ?2")
    Integer countByStatusAndMemberId(OrderForm.OrderStatus orderStatus, String memberId);
    @Query(value = "SELECT SUM(p.price * oi.count) FROM order_item oi LEFT JOIN order_form of ON oi.order_form_id = of.id LEFT JOIN product p ON oi.product_id = p.id WHERE of.payment_status = 1 AND of.logically_deleted = 0 AND of.created_date >= unix_timestamp(CURRENT_DATE()) * 1000", nativeQuery = true)
    Double getTodaySale();
    @Query(value = "SELECT SUM(p.price * oi.count) FROM order_item oi LEFT JOIN order_form of ON oi.order_form_id = of.id LEFT JOIN product p ON oi.product_id = p.id WHERE of.payment_status = 1 AND of.logically_deleted = 0 AND of.created_date >= unix_timestamp(CURRENT_DATE()) * 1000 - 1000*60*60*24*30", nativeQuery = true)
    Double getMonthSale();
    @Query(value = "SELECT SUM(p.price * oi.count) FROM order_item oi LEFT JOIN order_form of ON oi.order_form_id = of.id LEFT JOIN product p ON oi.product_id = p.id WHERE of.payment_status = 1 AND of.logically_deleted = 0 AND of.created_date >= ?1 AND of.created_date <= ?2", nativeQuery = true)
    Double getSaleByDate(Long startDate, Long endDate);
}
