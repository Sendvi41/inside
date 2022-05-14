package com.gmail.sendvi41.controller;

import com.gmail.sendvi41.dto.CreateTokenRequest;
import com.gmail.sendvi41.dto.TokenResponse;
import com.gmail.sendvi41.entity.UserEntity;
import com.gmail.sendvi41.jwt.JwtClientProvider;
import com.gmail.sendvi41.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.sendvi41.constants.ApiConstants.CONTROLLER_TOKEN_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(CONTROLLER_TOKEN_PATH)
public class TokenController {
    private final UserService userService;
    private final JwtClientProvider jwtProvider;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public TokenResponse createToken(@RequestBody CreateTokenRequest request) {
        UserEntity user = userService.findUserByNameAndPassword(request.getName(), request.getPassword());
        return TokenResponse.of(jwtProvider.generateTokenForUser(user));
    }
}
