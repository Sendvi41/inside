package com.gmail.sendvi41.service;

import com.gmail.sendvi41.entity.MessageEntity;

import java.util.List;

public interface MessageService {
    List<MessageEntity> getMessagesByName(String name, String amount);
}
