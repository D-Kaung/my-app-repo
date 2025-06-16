package com.agb.myappdemo.repository;

import com.agb.myappdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByPhone(String phone);

    boolean existsByNrc(String nrc);
}
