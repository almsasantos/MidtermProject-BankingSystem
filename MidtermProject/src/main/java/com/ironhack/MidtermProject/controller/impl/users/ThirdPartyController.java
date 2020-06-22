package com.ironhack.MidtermProject.controller.impl.users;

import com.ironhack.MidtermProject.dto.ThirdPartyTransaction;
import com.ironhack.MidtermProject.model.entities.users.ThirdParty;
import com.ironhack.MidtermProject.service.users.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ThirdPartyController {
    @Autowired
    private ThirdPartyService thirdPartyService;

    @GetMapping("/thirdparties")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdParty> findAll(){
        return thirdPartyService.findAll();
    }

    @GetMapping("/thirdparties/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ThirdParty findById(@PathVariable("id") Integer id){
        return thirdPartyService.findById(id);
    }

    @PatchMapping("/debit_transaction/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debitTransaction(@PathVariable("id") Integer thirdPartyId, ThirdPartyTransaction thirdPartyTransaction){
        thirdPartyService.debitTransaction(thirdPartyId, thirdPartyTransaction);
    }

    @PatchMapping("/credit_transaction/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditTransaction(@PathVariable("id") Integer thirdPartyId, ThirdPartyTransaction thirdPartyTransaction){
        thirdPartyService.creditTransaction(thirdPartyId, thirdPartyTransaction);
    }
}
