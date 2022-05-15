package com.gmail.sendvi41.utils;

import com.gmail.sendvi41.entity.MessageEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtils {

    public static List<MessageEntity> getListMessageEntity() {
        return List.of(MessageEntity.builder()
                .id(1)
                .message("Hello")
                .creationDate(LocalDateTime.now())
                .build());
    }
}
