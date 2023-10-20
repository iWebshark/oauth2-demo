package com.oauth.demo.service.converter;

import com.oauth.demo.model.User;
import com.oauth.demo.persistence.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter <User, UserEntity> {

    @Override
    public UserEntity convert(User value) {
        return UserEntity.builder()
                .id(value.getId())
                .username(value.getUsername())
                .email(value.getEmail())
                .role(value.getRole())
                .firstname(value.getFirstname())
                .lastname(value.getLastname())
                .password(value.getPassword())
                .provider(value.getProvider())
                .build();
    }

    @Override
    public User convertBack(UserEntity value) {
        return User.builder()
                .id(value.getId())
                .username(value.getUsername())
                .email(value.getEmail())
                .role(value.getRole())
                .firstname(value.getFirstname())
                .lastname(value.getLastname())
                .password(value.getPassword())
                .provider(value.getProvider())
                .build();
    }
}
