package com.gmail.sendvi41.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sendvi41.dto.GetMessageRequest;
import com.gmail.sendvi41.entity.MessageEntity;
import com.gmail.sendvi41.service.MessageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static com.gmail.sendvi41.constants.ApiConstants.CONTROLLER_MESSAGE_PATH;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
})
class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MessageService messageService;

    @Test
    void getMessage_shouldReturn200OkAndListMessageEntity() throws Exception {
        //given
        GetMessageRequest request = buildGetMessageRequest();
        List<MessageEntity> listMessageEntity = getListMessageEntity();
        //when
        when(messageService.getMessagesByName(request.getName(), request.getAmount()))
                .thenReturn(listMessageEntity);
        mockMvc.perform(post(CONTROLLER_MESSAGE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                //then
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(listMessageEntity)));

        verify(messageService, Mockito.times(1))
                .getMessagesByName(request.getName(), request.getAmount());

    }

    private GetMessageRequest buildGetMessageRequest() {
        return new GetMessageRequest()
                .setName("IVANCOOL")
                .setAmount("history 10");
    }

    private List<MessageEntity> getListMessageEntity() {
        return List.of(MessageEntity.builder()
                .id(1)
                .message("Hello")
                .creationDate(LocalDateTime.now())
                .build());
    }
}