package com.antoniosousa.ecommerce.domain.dtos.user;

import com.antoniosousa.ecommerce.domain.entities.enums.AccountStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRegisterResponseDto {
    private String username;
    private String email;
    private String password;
    private AccountStatus accountStatus;
    private LocalDateTime createdAt;
}
