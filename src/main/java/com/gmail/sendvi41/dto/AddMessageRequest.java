package com.gmail.sendvi41.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddMessageRequest {
    private String name;
    private String message;
}
