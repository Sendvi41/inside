package com.gmail.sendvi41.service;

import com.gmail.sendvi41.entity.UserEntity;

public interface UserService {
    UserEntity findUserByNameAndPassword(String name, String password);
}
