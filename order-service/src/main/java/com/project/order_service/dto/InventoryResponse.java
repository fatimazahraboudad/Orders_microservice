package com.project.order_service.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class InventoryResponse {
    private String skuCode;
    private boolean inStock;
}
