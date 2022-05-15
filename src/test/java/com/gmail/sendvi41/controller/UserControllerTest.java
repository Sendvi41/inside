package com.gmail.sendvi41.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sendvi41.dto.AddMessageRequest;
import com.gmail.sendvi41.entity.MessageEntity;
import com.gmail.sendvi41.service.UserService;
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

import static com.gmail.sendvi41.constants.ApiConstants.CONTROLLER_USER_PATH;
import static com.gmail.sendvi41.enums.Status.SUCCESS;
import static com.gmail.sendvi41.utils.TestUtils.getMessageEntity;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @Test
    void addMessage_shouldReturn200OkAndMessageAdditionResponse() throws Exception {
        //given
        AddMessageRequest request = buildAddMessageRequest();
        //when
        when(userService.addMessageForUser(request.getName(), request.getMessage()))
                .thenReturn(getMessageEntity());
        String result = mockMvc.perform(post(CONTROLLER_USER_PATH + "/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                //then
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(UTF_8);

        assertThat(result).isNotBlank().contains(SUCCESS.toString()).contains("1");

        verify(userService, Mockito.times(1))
                .addMessageForUser(request.getName(), request.getMessage());
    }

    private AddMessageRequest buildAddMessageRequest() {
        return new AddMessageRequest()
                .setName("IVANCOOL")
                .setMessage("Hello");
    }
}