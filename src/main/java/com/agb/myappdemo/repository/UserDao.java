package com.agb.myappdemo.repository;

import com.agb.myappdemo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

    boolean existsByPhone(String phone);

    boolean existsByNrc(String nrc);

    List<User>findUserByTownshipId(@RequestParam("townshipId")Long townshipId);

    List<User> findUserByDivisionId(@RequestParam("divisionId") Long divisionId);

    List<User> findUserByUsername(String username);

    Page<User> findByUsernameContainingIgnoreCase(String search, Pageable pageable);

    Optional<User> findByUsername(String username);

    Optional<User> findByPhone(String newPhone);

    Optional<User> findByNrc(String newNrc);

    void deleteById(int id);

    Optional<User> findById(int id);
}
