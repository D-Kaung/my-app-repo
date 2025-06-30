package com.agb.myappdemo.service;

import com.agb.myappdemo.entity.Role;
import com.agb.myappdemo.entity.Township;
import com.agb.myappdemo.entity.User;
import com.agb.myappdemo.repository.TownshipDao;
import com.agb.myappdemo.repository.UserDao;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

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

        user.setRole(Role.USER);
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user
        userDao.save(user);
    }

    @Transactional
    public void deleteUser(int id) {
        userDao.deleteById(id);
    }

    @Override
    public User findUserById(Integer userId) {
        return userDao.findById(Long.valueOf(userId))
                .orElse(null);
    }

    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username).orElse(null);
    }

    @Override
    public boolean existsByUsername(String newUsername) {
        return userDao.findByUsername(newUsername).isPresent();
    }

    @Override
    public boolean existsByPhone(String newPhone) {
        return userDao.findByPhone(newPhone).isPresent();
    }

    @Override
    public boolean existsByNrc(String newNrc) {
        return userDao.findByNrc(newNrc).isPresent();
    }

    public void deleteUserById(int id) {
        userDao.deleteById(id);
    }

    public void generateExcel(HttpServletResponse response) throws IOException {

        List<User> users = userDao.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("DataOfAllUsers");
        HSSFCellStyle dateCellStyle = workbook.createCellStyle();

        HSSFDataFormat dataFormat = workbook.createDataFormat();
        dateCellStyle.setDataFormat(dataFormat.getFormat("yyyy-mm-dd"));
        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("Username");
        row.createCell(1).setCellValue("Nrc");
        row.createCell(2).setCellValue("Phone");
        row.createCell(3).setCellValue("Address");
        row.createCell(4).setCellValue("Role");
        row.createCell(5).setCellValue("DateOfBirth");

        int dataRowIndex = 1;

        for (User user: users){
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(user.getUsername());
            dataRow.createCell(1).setCellValue(user.getNrc());
            dataRow.createCell(2).setCellValue(user.getPhone());
            dataRow.createCell(3).setCellValue(user.getAddress());
            dataRow.createCell(4).setCellValue(String.valueOf(user.getRole()));

            HSSFCell dateCell = dataRow.createCell(5);
            dateCell.setCellValue(user.getDateOfBirth());
            dateCell.setCellStyle(dateCellStyle);
            dataRowIndex ++;
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }


}