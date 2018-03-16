package com.framework.module.orderform.service;

import com.framework.module.member.domain.Member;
import com.framework.module.orderform.domain.Cart;
import com.framework.module.orderform.domain.CartItem;
import com.kratos.common.CrudService;

public interface CartService extends CrudService<Cart> {
    /**
     * 添加商品
     * @param cart 需要{@link Member}属性，以及 {@link CartItem} 属性
     */
    void addProduct(Cart cart) throws Exception;

    /**
     * 删除购物车项
     * @param id 购物车项id
     */
    void deleteItem(String id);

    /**
     * 增加购物车项数量
     * @param id 购物车项id
     */
    void increaseItemCount(String id);

    /**
     * 增加购物车项数量
     * @param id 购物车项id
     */
    void reduceItemCount(String id);
}
