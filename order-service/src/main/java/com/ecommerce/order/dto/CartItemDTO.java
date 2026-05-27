package com.ecommerce.order.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
