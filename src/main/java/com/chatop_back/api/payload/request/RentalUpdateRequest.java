package com.chatop_back.api.payload.request;

import lombok.Data;

@Data
public class RentalUpdateRequest {
    private String name;
    private Double surface;
    private Double price;
    private String description;
}