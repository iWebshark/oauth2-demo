package com.oauth.demo.service;

import com.oauth.demo.exception.UserNotFoundException;
import com.oauth.demo.model.User;
import com.oauth.demo.persistence.repository.UserRepository;
import com.oauth.demo.service.converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserConverter converter;
    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username).map(converter::convertBack);
    }

    @Override
    public boolean hasUserWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll().stream().map(converter::convertBack).toList();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(converter::convertBack);
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User validateAndGetUserByEmail(String email) {
        return getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with email %s not found", email)));
    }

    @Override
    public User saveUser(User user) {
        return converter.convertBack(userRepository.save(converter.convert(user)));
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(converter.convert(user));
    }
}
