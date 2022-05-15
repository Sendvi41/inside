package com.gmail.sendvi41.dto;

import com.gmail.sendvi41.enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageAdditionResponse {
    private String messageId;
    private Status status;
}
