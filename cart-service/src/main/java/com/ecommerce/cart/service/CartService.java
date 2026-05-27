package com.ecommerce.cart.service;


import com.ecommerce.cart.dto.CartItemRequest;
import com.ecommerce.cart.model.CartItem;
import com.ecommerce.cart.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartItemRepository cartItemRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        // In a real microservice, we would call product-service to validate stock and get price
        // For now, we assume product is valid and price is provided or we just store it
        
        Long uId = Long.valueOf(userId);
        Long pId = request.getProductId();

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(uId, pId);
        if (existingCartItem != null){
            // Update Quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            // In a real app, we'd fetch the latest price from product-service
            // existingCartItem.setPrice(latestPrice.multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            // Create new Cart Item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(uId);
            cartItem.setProductId(pId);
            cartItem.setQuantity(request.getQuantity());
            // cartItem.setPrice(...); // Should be fetched from product-service
            cartItemRepository.save(cartItem);
        }

        return true;


    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        cartItemRepository.deleteByUserIdAndProductId(Long.valueOf(userId), productId);
        return true;
    }


    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findByUserId(Long.valueOf(userId));
    }

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(Long.valueOf(userId));
    }
}
