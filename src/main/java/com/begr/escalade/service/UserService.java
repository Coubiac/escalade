package com.begr.escalade.service;

import com.begr.escalade.entity.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
