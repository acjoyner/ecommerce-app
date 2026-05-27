package com.ecommerce.order.controller;


import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.dto.CartItemDTO;
import com.ecommerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody List<CartItemDTO> cartItems){
        return  orderService.createOrder(userId, cartItems)
                .map(orderResponse -> new ResponseEntity<>(orderResponse, HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
