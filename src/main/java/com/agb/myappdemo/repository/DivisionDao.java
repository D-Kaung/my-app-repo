package com.agb.myappdemo.repository;

import com.agb.myappdemo.entity.Division;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DivisionDao extends JpaRepository<Division, Long> {

    List<Division> findAll();

    List<Division> findDivisionsById(Long divisionId);

    Page<Division> findDivisionsByNameContainingIgnoreCase(String search, Pageable pageable);


}
