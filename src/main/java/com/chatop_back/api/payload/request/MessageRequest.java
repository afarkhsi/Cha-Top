package com.chatop_back.api.payload.request;

import lombok.Data;

@Data
public class MessageRequest {
    private String message;
    private Long user_id;
    private Long rental_id;
}