package com.ironhack.MidtermProject.controller.impl.users;

import com.ironhack.MidtermProject.dto.LoginAccount;
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

    @PatchMapping("/thirdparties/login")
    @ResponseStatus(HttpStatus.OK)
    public ThirdParty loginThirdParty(@RequestBody LoginAccount loginAccount){
        return thirdPartyService.loginThirdParty(loginAccount);
    }

    @PatchMapping("/thirdparties/logout")
    @ResponseStatus(HttpStatus.OK)
    public ThirdParty logoutThirdParty(@RequestBody LoginAccount loginAccount){
        return thirdPartyService.logOutThirdParty(loginAccount);
    }

    @PatchMapping("/debit-transaction")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debitTransaction(@RequestParam("hashed_key") String hashedKey, @RequestBody ThirdPartyTransaction thirdPartyTransaction){
        thirdPartyService.debitTransaction(hashedKey, thirdPartyTransaction);
    }

    @PatchMapping("/credit-transaction")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditTransaction(@RequestParam("hashed_key") String hashedKey, @RequestBody ThirdPartyTransaction thirdPartyTransaction){
        thirdPartyService.creditTransaction(hashedKey, thirdPartyTransaction);
    }

}
