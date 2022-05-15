package com.gmail.sendvi41.service;

import com.gmail.sendvi41.entity.MessageEntity;
import com.gmail.sendvi41.entity.UserEntity;
import com.gmail.sendvi41.exception.AuthException;
import com.gmail.sendvi41.exception.UserServiceException;
import com.gmail.sendvi41.repository.MessageRepository;
import com.gmail.sendvi41.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Override
    public UserEntity findUserByNameAndPassword(String name, String password) {
        Optional<UserEntity> user = userRepository.findByName(name);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        } else {
            throw new AuthException("User name or password entered incorrectly", BAD_REQUEST);
        }
    }

    @Override
    public MessageEntity addMessageForUser(String name, String message) {
        Optional<UserEntity> user = userRepository.findByName(name);
        if (user.isPresent()) {
            return messageRepository.save(MessageEntity.builder()
                    .message(message)
                    .user(user.get())
                    .creationDate(LocalDateTime.now())
                    .build());
        } else {
            throw new UserServiceException("User is not found", NOT_FOUND);
        }
    }
}
