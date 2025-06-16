package com.agb.myappdemo.repository;

import com.agb.myappdemo.entity.Status;
import com.agb.myappdemo.entity.Township;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface TownshipDao extends JpaRepository<Township, Long> {

    @Query("SELECT t FROM Township t where t.division.id = :divisionId And t.status= :status")
    List<Township> findTownshipByDivisionId(@RequestParam("divisionId") Long divisionId,
                                            @Param("status") Status status);

    List<Township> findAll();

}
