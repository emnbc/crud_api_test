package com.emnbc.crud_api_test.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}