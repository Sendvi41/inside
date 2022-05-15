package com.gmail.sendvi41.service;

import com.gmail.sendvi41.entity.MessageEntity;
import com.gmail.sendvi41.entity.UserEntity;
import com.gmail.sendvi41.exception.IncorrectPatternForMessage;
import com.gmail.sendvi41.exception.NotFoundException;
import com.gmail.sendvi41.repository.MessageRepository;
import com.gmail.sendvi41.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.gmail.sendvi41.utils.TestUtils.getListMessageEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
})
class MessageServiceImplTest {

    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void getMessagesByName_shouldReturnListMessageEntity() {
        //given
        String name = "IVAN";
        String amount = "history 10";
        List<MessageEntity> expectedList = getListMessageEntity();
        //when
        when(userRepository.findByName(name)).thenReturn(Optional.of(new UserEntity()));
        when(messageRepository.findByNameWithLimit(name, 10)).thenReturn(expectedList);
        List<MessageEntity> result = messageService.getMessagesByName(name, amount);
        //then
        assertThat(result).isNotNull();
        assertEquals(result, expectedList);

        verify(userRepository).findByName(name);
        verify(messageRepository).findByNameWithLimit(name, 10);
    }

    @Test
    void getMessagesByNameWithIncorrectAmountFormat_shouldThrowIncorrectPatternForMessage() {
        //given
        String name = "IVAN";
        String incorrectAmount = "history 1sd0";

        //when
        when(userRepository.findByName(name)).thenReturn(Optional.of(new UserEntity()));

        assertThatThrownBy(() -> messageService.getMessagesByName(name, incorrectAmount))
                //then
                .isInstanceOf(IncorrectPatternForMessage.class);
    }

    @Test
    void getMessagesByNameWithInvalidName_shouldThrowNotFoundException() {
        //given
        String invalidName = "VIKTOR";
        String amount = "history 10";
        List<MessageEntity> expectedList = getListMessageEntity();

        //when
        assertThatThrownBy(() -> messageService.getMessagesByName(invalidName, amount))
                //then
                .isInstanceOf(NotFoundException.class);
    }
}