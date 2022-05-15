package com.gmail.sendvi41.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.sendvi41.dto.CreateTokenRequest;
import com.gmail.sendvi41.dto.TokenResponse;
import com.gmail.sendvi41.entity.UserEntity;
import com.gmail.sendvi41.jwt.JwtClientProvider;
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

import static com.gmail.sendvi41.constants.ApiConstants.CONTROLLER_TOKEN_PATH;
import static com.gmail.sendvi41.utils.TestUtils.buildUserEntity;
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
class TokenControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtClientProvider jwtProvider;
    @MockBean
    private UserService userService;

    @Test
    void createToken_shouldReturn200OkAndTokenResponse() throws Exception {
        //given
        CreateTokenRequest request = buildGetMessageRequest();
        String expectedToken = jwtProvider.generateTokenForUser(buildUserEntity());
        //when
        when(userService.findUserByNameAndPassword(request.getName(), request.getPassword()))
                .thenReturn(buildUserEntity());
        String result = mockMvc.perform(post(CONTROLLER_TOKEN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                //then
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(UTF_8);

        TokenResponse actualToken = objectMapper.readValue(result, TokenResponse.class);

        assertThat(result).isNotBlank();
        assertThat(actualToken.getToken()).isNotBlank();
        assertThat(actualToken.getToken()).isEqualTo(expectedToken);
        verify(userService, Mockito.times(1))
                .findUserByNameAndPassword(request.getName(), request.getPassword());
    }

    private CreateTokenRequest buildGetMessageRequest() {
        return new CreateTokenRequest()
                .setName("IVANCOOL")
                .setPassword("0000");
    }
}