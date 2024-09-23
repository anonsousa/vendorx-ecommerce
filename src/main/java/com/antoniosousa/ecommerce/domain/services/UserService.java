package com.antoniosousa.ecommerce.domain.services;

import com.antoniosousa.ecommerce.domain.dtos.user.UserRegisterRequestDto;
import com.antoniosousa.ecommerce.domain.dtos.user.UserRegisterResponseDto;
import com.antoniosousa.ecommerce.domain.entities.User;
import com.antoniosousa.ecommerce.domain.entities.enums.AccountStatus;
import com.antoniosousa.ecommerce.domain.mapper.UserMapper;
import com.antoniosousa.ecommerce.domain.repositories.UserRepository;
import com.antoniosousa.ecommerce.infra.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final NotificationRabbitMQService notificationRabbitMQService;
    private final String exchange;

    public UserService(UserRepository userRepository,
                       NotificationRabbitMQService notificationRabbitMQService,
                       @Value("${rabbitmq.notification.exchange}") String exchange) {
        this.userRepository = userRepository;
        this.notificationRabbitMQService = notificationRabbitMQService;
        this.exchange = exchange;
    }

    @Transactional
    public UserRegisterResponseDto registerUser(UserRegisterRequestDto user) {
        User userEntity = UserMapper.INSTANCE.toEntity(user);
        userEntity.setAccountStatus(AccountStatus.PENDING);

        var userSaved = userRepository.save(userEntity);

        UserRegisterResponseDto response = UserMapper.INSTANCE.toUserRegisterResponseDto(userSaved);
        notifyRabbit(response);

        return response;
    }

    @Transactional(readOnly = true)
    public UserRegisterResponseDto findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("User not founbd!"));
        return UserMapper.INSTANCE.toUserRegisterResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserRegisterResponseDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.INSTANCE.toResponseDtos(users);
    }

    @Transactional
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("User not founbd!"));

        userRepository.delete(user);
    }


    private void notifyRabbit(UserRegisterResponseDto user) {
        try {
            notificationRabbitMQService.notify(user, exchange);
            userRepository.updateIntegratedById(user.getId(), true);
        } catch (RuntimeException e) {
            userRepository.updateIntegratedById(user.getId(), false);
        }
    }















}

