package com.gmail.sendvi41.controller;

import com.gmail.sendvi41.dto.AddMessageRequest;
import com.gmail.sendvi41.dto.MessageAdditionResponse;
import com.gmail.sendvi41.entity.MessageEntity;
import com.gmail.sendvi41.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.sendvi41.constants.ApiConstants.CONTROLLER_USER_PATH;
import static com.gmail.sendvi41.enums.Status.SUCCESS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(CONTROLLER_USER_PATH)
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/message", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageAdditionResponse> addMessage(@RequestBody AddMessageRequest request) {
        MessageEntity addedMessage = userService.addMessageForUser(request.getName(), request.getMessage());
        return ResponseEntity.ok().body(
                MessageAdditionResponse.builder()
                        .messageId(addedMessage.getId().toString())
                        .status(SUCCESS)
                        .build()
        );
    }
}
