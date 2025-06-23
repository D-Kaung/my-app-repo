package com.agb.myappdemo.service;

import com.agb.myappdemo.entity.Division;
import com.agb.myappdemo.entity.Status;
import com.agb.myappdemo.entity.Township;
import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.repository.DivisionDao;
import com.agb.myappdemo.repository.TownshipDao;
import com.agb.myappdemo.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final DivisionDao divisionDao;
    private final TownshipDao townshipDao;
    private final UserDao userDao;

    @Autowired
    public LocationService(DivisionDao divisionDao, TownshipDao townshipDao, UserDao userDao) {
        this.divisionDao = divisionDao;
        this.townshipDao = townshipDao;
        this.userDao = userDao;
    }

    public List<Township> getAllTownship () {
        return townshipDao.findAll();
    }

    public List<Division> getAllDivision () {
        return divisionDao.findAll();
    }

    public List<Township> getAllTownshipByDivisionId(Long divisionId, Status status) {
         return townshipDao.findTownshipByDivisionId(divisionId, status.ACTIVE);
    }

    public List<User> getAllUserByDivisionId(Long divisionId) {
        return userDao.findUserByDivisionId(divisionId);
    }

    public List<User> getAllUserByTownshipId(Long townshipId) {
        return userDao.findUserByTownshipId(townshipId);
    }

    public List<Division> getAllDivisionById(Long divisionId) {
        return divisionDao.findDivisionsById(divisionId);
    }

    public Division findDivisionId(Long divisionId) {
        return divisionDao.findById(divisionId).orElse(null);
    }

    public void saveDivision(Division division) {
        divisionDao.save(division);
    }

    public Township findTownshipId(Long townshipId) {
        return townshipDao.findById(townshipId).orElse(null);
    }

    public void saveTownship(Township township){
        townshipDao.save(township);
    }
}
