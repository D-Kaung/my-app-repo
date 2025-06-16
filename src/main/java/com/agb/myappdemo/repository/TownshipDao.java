package com.agb.myappdemo.repository;

import com.agb.myappdemo.entity.Township;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownshipDao extends JpaRepository<Township, Long> {
}
