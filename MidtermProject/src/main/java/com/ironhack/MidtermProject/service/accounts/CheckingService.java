package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.entities.accounts.Checking;
import com.ironhack.MidtermProject.repository.accounts.CheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckingService {
    @Autowired
    private CheckingRepository checkingRepository;

    public List<Checking> findAll(){
        return checkingRepository.findAll();
    }

    public Checking findById(Integer id){
        return checkingRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Checking id not found"));
    }

    public List<Checking> findBySecretKey(String secretKey){
        return checkingRepository.findBySecretKey(secretKey);
    }

    public List<Checking> findByStatus(Status status){
        return checkingRepository.findByStatus(status);
    }

    public List<Checking> findByMinimumBalance(BigDecimal minimumBalance){
        return checkingRepository.findByMinimumBalance(minimumBalance);
    }

    public List<Checking> findByMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee){
        return checkingRepository.findByMonthlyMaintenanceFee(monthlyMaintenanceFee);
    }
}
