package com.framework.module.orderform.service;

import com.framework.module.orderform.domain.Cart;
import com.framework.module.orderform.domain.CartItem;
import com.framework.module.orderform.domain.CartItemRepository;
import com.framework.module.orderform.domain.CartRepository;
import com.kratos.common.AbstractCrudService;
import com.kratos.common.PageRepository;
import com.kratos.exceptions.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class CartServiceImpl extends AbstractCrudService<Cart> implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    @Override
    protected PageRepository<Cart> getRepository() {
        return cartRepository;
    }

    @Override
    public void addProduct(final Cart cart) throws Exception {
        if(cart.getMember() == null || StringUtils.isBlank(cart.getMember().getId())) {
            throw new BusinessException("会员不能为空");
        }
        List<Cart> carts = cartRepository.findAll((Root<Cart> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicate = new ArrayList<>();
            predicate.add(criteriaBuilder.equal(root.get("member"), cart.getMember()));
            return criteriaBuilder.and(predicate.toArray(new Predicate[]{}));
        });
        if(carts == null || carts.isEmpty()) {
            cartRepository.save(cart);
            List<CartItem> cartItems = cart.getItems();
            CartItem cartItem = cartItems.get(0);
            cartItem.setCart(cart);
            cartItemRepository.save(cartItem);
        } else {
            Cart oldCart = carts.get(0);
            List<CartItem> oldCartItems = oldCart.getItems();
            List<CartItem> cartItems = cart.getItems();
            CartItem cartItem = cartItems.get(0);
            oldCartItems = oldCartItems
                    .stream()
                    .filter(item -> item.getProduct().getId().equals(cartItem.getProduct().getId()))
                    .collect(Collectors.toList());

            if (oldCartItems != null && !oldCartItems.isEmpty()) {
                CartItem oldCartItem = oldCartItems.get(0);
                oldCartItem.setCount(oldCartItem.getCount() + cartItem.getCount());
            } else {
                cartItem.setCart(oldCart);
                cartItemRepository.save(cartItem);
            }
        }
    }

    @Override
    public void deleteItem(String id) {
        cartItemRepository.delete(id);
    }

    @Override
    public void increaseItemCount(String id) {
        CartItem cartItem = cartItemRepository.findOne(id);
        cartItem.setCount(cartItem.getCount() + 1);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void reduceItemCount(String id) {
        CartItem cartItem = cartItemRepository.findOne(id);
        if(cartItem.getCount() > 0) {
            cartItem.setCount(cartItem.getCount() - 1);
            cartItemRepository.save(cartItem);
        } else {
            cartItemRepository.delete(id);
        }

    }

    @Override
    public void delete(String id) throws Exception {
        cartRepository.delete(id);
    }

    @Autowired
    public CartServiceImpl(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }
}