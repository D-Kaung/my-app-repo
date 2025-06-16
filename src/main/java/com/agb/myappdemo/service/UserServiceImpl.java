package com.agb.myappdemo.service;

import com.agb.myappdemo.entity.Township;
import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.repository.TownshipDao;
import com.agb.myappdemo.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final TownshipDao townshipDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder,
                           TownshipDao townshipDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.townshipDao = townshipDao;
    }

    @Override
    public void signUpUser(User user) throws Exception {
        // Validate unique fields
        if (userDao.existsByPhone(user.getPhone())) {
            throw new Exception("This Phone is already exists.");
        }
        if (userDao.existsByNrc(user.getNrc())) {
            throw new Exception("This nrc is already exists.");
        }

        // Set township from ID
        if (user.getTownship() != null && user.getTownship().getId() != null) {
            Township township = townshipDao.findById(user.getTownship().getId())
                    .orElseThrow(() -> new Exception("Invalid township selected"));
            user.setTownship(township);
            user.setDivision(township.getDivision());
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user
        userDao.save(user);
    }
}