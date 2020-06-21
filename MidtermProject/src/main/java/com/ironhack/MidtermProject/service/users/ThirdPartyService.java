package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.entities.ThirdParty;
import com.ironhack.MidtermProject.repository.users.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThirdPartyService {
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    public List<ThirdParty> findAll(){
        return thirdPartyRepository.findAll();
    }

    public ThirdParty findById(Integer id){
        return thirdPartyRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Third Party id not found"));
    }

    public void createNewThirdParty(ThirdParty thirdParty){
        thirdPartyRepository.save(thirdParty);
    }
}
