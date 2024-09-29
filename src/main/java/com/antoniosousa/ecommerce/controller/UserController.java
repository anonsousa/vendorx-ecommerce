package com.antoniosousa.ecommerce.controller;

import com.antoniosousa.ecommerce.domain.dtos.user.UserRegisterRequestDto;
import com.antoniosousa.ecommerce.domain.dtos.user.UserRegisterResponseDto;
import com.antoniosousa.ecommerce.domain.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserRegisterResponseDto> register(@Valid @RequestBody UserRegisterRequestDto user) {
        UserRegisterResponseDto userDto = userService.registerUser(user);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(userDto.getId())
                        .toUri())
                .body(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRegisterResponseDto> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserRegisterResponseDto>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUserById(@RequestParam Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
