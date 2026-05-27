package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderItemDTO;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.dto.CartItemDTO;
import com.ecommerce.order.model.*;
import com.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    // This would eventually be a Feign client or RestTemplate call to cart-service
    public Optional<OrderResponse> createOrder(String userId, List<CartItemDTO> cartItems) {
        if(cartItems.isEmpty()){
            return Optional.empty();
        }

        Long uId = Long.valueOf(userId);

        // Calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(item -> item.getPrice() != null ? item.getPrice() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal:: add);
        // Create order
        Order order = new Order();
        order.setUserId(uId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                ))
                .toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        return Optional.of(mapToOrderResponse(savedOrder));


    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getId(),
                                orderItem.getProductId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice() != null ? orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())) : BigDecimal.ZERO
                                ))
                                .toList(),
                                order.getCreatedAt()
        );
    }
}
