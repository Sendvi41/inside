package com.gmail.sendvi41.controller;

import com.gmail.sendvi41.dto.GetMessageRequest;
import com.gmail.sendvi41.entity.MessageEntity;
import com.gmail.sendvi41.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.sendvi41.constants.ApiConstants.CONTROLLER_MESSAGE_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(CONTROLLER_MESSAGE_PATH)
public class MessageController {

    private final MessageService messageService;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public List<MessageEntity> getMessages(@RequestBody GetMessageRequest request) {
        return messageService.getMessagesByName(request.getName(), request.getAmount());
    }
}
