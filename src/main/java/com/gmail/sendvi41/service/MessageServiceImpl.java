package com.gmail.sendvi41.service;

import com.gmail.sendvi41.entity.MessageEntity;
import com.gmail.sendvi41.entity.UserEntity;
import com.gmail.sendvi41.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl {
    private final MessageRepository messageRepository;

//    public MessageEntity createMessage(UserEntity user, String message){
//
//    }
}
