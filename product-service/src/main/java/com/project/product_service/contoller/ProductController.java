package com.project.product_service.contoller;

import com.project.product_service.dto.ProductRequest;
import com.project.product_service.dto.ProductResponse;
import com.project.product_service.model.Product;
import com.project.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productDto){
        productService.createProduct(productDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllproduct(){
        return productService.getAllProducts();
    }
}
