package com.antoniosousa.ecommerce.domain.services;

import com.antoniosousa.ecommerce.domain.dtos.user.UserRegisterRequestDto;
import com.antoniosousa.ecommerce.domain.dtos.user.UserRegisterResponseDto;
import com.antoniosousa.ecommerce.domain.entities.User;
import com.antoniosousa.ecommerce.domain.entities.enums.AccountStatus;
import com.antoniosousa.ecommerce.domain.mapper.UserMapper;
import com.antoniosousa.ecommerce.domain.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserRegisterResponseDto registerUser(UserRegisterRequestDto user) {
        User userEntity = UserMapper.INSTANCE.toEntity(user);

        userEntity.setAccountStatus(AccountStatus.PENDING);

        var userSaved = userRepository.save(userEntity);

        return UserMapper.INSTANCE.toUserRegisterResponseDto(userSaved);
    }
}
