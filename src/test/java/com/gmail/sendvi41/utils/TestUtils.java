package com.gmail.sendvi41.utils;

import com.gmail.sendvi41.entity.MessageEntity;
import com.gmail.sendvi41.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtils {
    private static final String ENCODED_PASSWORD = "$2a$12$DISxoKlNKNdfmoR0sRTCxO5igLNEvn6pQe1PSRw4WP80UqcEmxNo6";

    public static List<MessageEntity> getListMessageEntity() {
        return List.of(MessageEntity.builder()
                .id(1)
                .message("Hello")
                .creationDate(LocalDateTime.now())
                .build());
    }

    public static UserEntity buildUserEntity() {
        return new UserEntity()
                .setId(1)
                .setName("IVANCOOL")
                .setPassword(ENCODED_PASSWORD);
    }

    public static MessageEntity getMessageEntity() {
        return MessageEntity.builder()
                .id(1)
                .message("Hello")
                .creationDate(LocalDateTime.now())
                .build();
    }
}
