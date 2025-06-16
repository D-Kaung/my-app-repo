package com.agb.myappdemo.repository;

import com.agb.myappdemo.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DivisionDao extends JpaRepository<Division, Long> {
}
