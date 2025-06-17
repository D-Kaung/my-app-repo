package com.agb.myappdemo.service;

import com.agb.myappdemo.entity.User;

public interface UserService {

    void signUpUser(User user) throws Exception;

    User findUserById(Integer userId);

    boolean existsByUsername(String newUsername);

    boolean existsByPhone(String newPhone);

    boolean existsByNrc(String newNrc);

    void saveUser(User user);
}
