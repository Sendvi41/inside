package com.gmail.sendvi41.service;

import com.gmail.sendvi41.entity.MessageEntity;
import com.gmail.sendvi41.exception.IncorrectPatternForMessage;
import com.gmail.sendvi41.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gmail.sendvi41.constants.MessageConstants.HISTORY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final static String INCORRECT_MESSAGE = "Pattern of message isn't correct Correct format 'history 10'";

    @Override
    public List<MessageEntity> getMessagesByName(String name, String amount) {
        Integer number = parseAmount(amount);
        return messageRepository.findByNameWithLimit(name, number);
    }

    private Integer parseAmount(String amount) {
        String[] array = amount.split(" ");
        try {
            if (HISTORY.equals(array[0])) {
                return Integer.parseInt(array[1]);
            } else {
                throw new IncorrectPatternForMessage(INCORRECT_MESSAGE, BAD_REQUEST);
            }
        } catch (Exception ex) {
            throw new IncorrectPatternForMessage(INCORRECT_MESSAGE, BAD_REQUEST);
        }
    }
}
