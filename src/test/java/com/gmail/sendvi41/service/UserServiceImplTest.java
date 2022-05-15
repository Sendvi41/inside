package com.gmail.sendvi41.service;

import com.gmail.sendvi41.entity.MessageEntity;
import com.gmail.sendvi41.entity.UserEntity;
import com.gmail.sendvi41.exception.AuthException;
import com.gmail.sendvi41.exception.UserServiceException;
import com.gmail.sendvi41.repository.MessageRepository;
import com.gmail.sendvi41.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.gmail.sendvi41.utils.TestUtils.buildUserEntity;
import static com.gmail.sendvi41.utils.TestUtils.getMessageEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
})
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByNameAndPassword_shouldReturnUserEntity() {
        //given
        String name = "IVAN";
        String password = "0000";
        UserEntity expectedResult = buildUserEntity();
        //when
        when(userRepository.findByName(name)).thenReturn(Optional.of(expectedResult));
        UserEntity result = userService.findUserByNameAndPassword(name, password);
        //then
        assertThat(result).isNotNull();
        assertEquals(expectedResult, result);
    }

    @Test
    void findUserByNameAndPasswordWithInvalidName_shouldThrowAuthException() {
        //given
        String invalidName = "IVAN";
        String password = "0000";

        //when
        assertThatThrownBy(() -> userService.findUserByNameAndPassword(invalidName, password))
                //then
                .isInstanceOf(AuthException.class);
    }

    @Test
    void findUserByNameAndPasswordWithInvalidPassword_shouldThrowAuthException() {
        //given
        String name = "IVAN";
        String invalidPassword = "dsdsdsd";

        //when
        when(userRepository.findByName(name)).thenReturn(Optional.of(buildUserEntity()));

        assertThatThrownBy(() -> userService.findUserByNameAndPassword(name, invalidPassword))
                //then
                .isInstanceOf(AuthException.class);
    }

    @Test
    void addMessageForUser_shouldReturnMessageEntity() {
        //given
        String name = "IVAN";
        String message = "Hello";
        UserEntity userEntity = buildUserEntity();
        MessageEntity expectedMessageEntity = getMessageEntity();
        //when
        when(userRepository.findByName(name)).thenReturn(Optional.of(userEntity));
        when(messageRepository.save(any())).thenReturn(expectedMessageEntity);
        MessageEntity result = userService.addMessageForUser(name, message);
        //then
        assertThat(result).isNotNull();
        assertEquals(expectedMessageEntity, result);
        verify(messageRepository).save(any());
    }

    @Test
    void addMessageForUserWithInvalidName_shouldThrowUserServiceExceptionn() {
        //given
        String invalidName = "VICTOR";
        String message = "Hello";

        //when
        assertThatThrownBy(() -> userService.addMessageForUser(invalidName, message))
                //then
                .isInstanceOf(UserServiceException.class);
    }
}