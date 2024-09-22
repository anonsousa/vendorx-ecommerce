package com.antoniosousa.ecommerce.controller;

import com.antoniosousa.ecommerce.domain.dtos.user.UserRegisterRequestDto;
import com.antoniosousa.ecommerce.domain.dtos.user.UserRegisterResponseDto;
import com.antoniosousa.ecommerce.domain.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> register(@RequestBody @Valid UserRegisterRequestDto user) {
        UserRegisterResponseDto userDto = userService.registerUser(user);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userDto.getId())
                .toUri())
                .body(userDto);
    }
}
