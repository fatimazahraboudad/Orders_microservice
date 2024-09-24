package com.project.order_service.feignClient;

import com.project.order_service.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/api/inventory")
    public List<InventoryResponse> isInStock(@RequestParam("skuCode") List<String> skuCode);

}
