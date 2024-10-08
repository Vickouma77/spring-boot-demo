package com.micro.product_service.dto;

public record ProductResponse(
        String id,
        String name,
        String description,
        String price
){}