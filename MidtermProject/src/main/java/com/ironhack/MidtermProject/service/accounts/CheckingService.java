package com.ironhack.MidtermProject.service.accounts;

import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.entities.Checking;
import com.ironhack.MidtermProject.repository.accounts.CheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
