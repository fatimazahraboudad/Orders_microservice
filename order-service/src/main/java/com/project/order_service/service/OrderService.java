package com.project.order_service.service;

import com.project.order_service.dto.InventoryResponse;
import com.project.order_service.dto.OderLineItemsDto;
import com.project.order_service.dto.OrderRequest;
import com.project.order_service.event.OrderPlacedEvent;
import com.project.order_service.feignClient.InventoryClient;
import com.project.order_service.model.OderLineItems;
import com.project.order_service.model.Order;
import com.project.order_service.repositroy.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {


    private final WebClient.Builder webClient;
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;
    private final InventoryClient inventoryClient;

    public String placeorder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        System.out.println(orderRequest.getOderLineItemsDto());
        List<OderLineItems> orderLineItems = orderRequest.getOderLineItemsDto()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOderLineItem(orderLineItems);

        List<String> skuCodes = order.getOderLineItem().stream().map(OderLineItems::getSkuCode).toList();



        //call inventory service and place order if product is in stock
//        InventoryResponse[] inventoryResponses= webClient.build().get()
//                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
//                        .retrieve()
//                                .bodyToMono(InventoryResponse[].class)
//                                        .block();


//        boolean allProductInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        List<InventoryResponse> inventoryResponseList=inventoryClient.isInStock(skuCodes);
        boolean allProductInStock = inventoryResponseList.stream().allMatch(InventoryResponse::isInStock);
        if (allProductInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
            return "order saved successfully";
        }else{
            throw new IllegalArgumentException("order in stock not exist ");
        }
    }

    private OderLineItems mapToDto(OderLineItemsDto orderLineItemsDto) {
        OderLineItems orderLineItems = new OderLineItems();
        orderLineItems.setId(orderLineItemsDto.getId());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return  orderLineItems;
    }

}