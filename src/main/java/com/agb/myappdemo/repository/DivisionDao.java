package com.agb.myappdemo.repository;

import com.agb.myappdemo.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DivisionDao extends JpaRepository<Division, Long> {

    List<Division> findAll();
}
