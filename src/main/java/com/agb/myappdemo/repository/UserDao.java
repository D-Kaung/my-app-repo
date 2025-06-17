package com.agb.myappdemo.repository;

import com.agb.myappdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {

    boolean existsByPhone(String phone);

    boolean existsByNrc(String nrc);

    List<User>findUserByTownshipId(@RequestParam("townshipId")Long townshipId);

    List<User> findUserByDivisionId(@RequestParam("divisionId") Long divisionId);
}
