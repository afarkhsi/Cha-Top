package com.chatop_back.api.payload;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Builder
public class AuthSuccess {
    private String token;
    public AuthSuccess(String token) {
        this.token = token;
    }
}
