package com.gmail.sendvi41.service;

import com.gmail.sendvi41.entity.UserEntity;
import com.gmail.sendvi41.exception.AuthException;
import com.gmail.sendvi41.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserEntity findUserByNameAndPassword(String name, String password) {
        Optional<UserEntity> user = userRepository.findByName(name);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        } else {
            throw new AuthException("User name or password entered incorrectly", BAD_REQUEST);
        }
    }
}
