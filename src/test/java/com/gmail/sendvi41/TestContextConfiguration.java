package com.gmail.sendvi41;

import com.gmail.sendvi41.repository.MessageRepository;
import com.gmail.sendvi41.repository.UserRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestContextConfiguration {
    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private UserRepository userRepository;
}
