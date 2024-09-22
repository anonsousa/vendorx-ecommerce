package com.antoniosousa.ecommerce.domain.dtos.user;

import com.antoniosousa.ecommerce.domain.entities.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponseDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private AccountStatus accountStatus;
    private LocalDateTime createdAt;
}
