package com.project.order_service.dto;

import com.project.order_service.model.OderLineItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private List<OderLineItemsDto> oderLineItemsDto;


}
