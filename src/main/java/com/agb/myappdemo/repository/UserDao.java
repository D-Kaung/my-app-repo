package com.agb.myappdemo.repository;

import com.agb.myappdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
}
