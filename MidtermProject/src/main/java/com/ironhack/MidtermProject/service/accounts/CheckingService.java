package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.entities.accounts.Checking;
import com.ironhack.MidtermProject.repository.accounts.CheckingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckingService {
    @Autowired
    private CheckingRepository checkingRepository;

    private static final Logger LOGGER = LogManager.getLogger(CheckingService.class);

    public List<Checking> findAll(){
        LOGGER.info("Get all Checking accounts");
        return checkingRepository.findAll();
    }

    public Checking findById(Integer id){
        LOGGER.info("Get Checking account with id " + id);
        return checkingRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Checking id not found"));
    }

    public List<Checking> findByStatus(Status status){
        LOGGER.info("Get Checking account with status " + status);
        return checkingRepository.findByStatus(status);
    }

    public List<Checking> findByMinimumBalance(BigDecimal minimumBalance){
        LOGGER.info("Get Checking account with minimum balance of " + minimumBalance);
        return checkingRepository.findByMinimumBalance(minimumBalance);
    }

    public List<Checking> findByMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee){
        LOGGER.info("Get Checking account with monthly maintenance fee of " + monthlyMaintenanceFee);
        return checkingRepository.findByMonthlyMaintenanceFee(monthlyMaintenanceFee);
    }
}
