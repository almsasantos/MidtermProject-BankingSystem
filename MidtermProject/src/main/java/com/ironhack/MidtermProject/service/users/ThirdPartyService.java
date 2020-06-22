package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.dto.ThirdPartyTransaction;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.Account;
import com.ironhack.MidtermProject.model.entities.users.ThirdParty;
import com.ironhack.MidtermProject.repository.accounts.AccountRepository;
import com.ironhack.MidtermProject.repository.users.ThirdPartyRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThirdPartyService {
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private AccountRepository accountRepository;

    private static final Logger LOGGER = LogManager.getLogger(ThirdPartyService.class);

    public List<ThirdParty> findAll(){
        LOGGER.info("Get all Third Party users");
        return thirdPartyRepository.findAll();
    }

    public ThirdParty findById(Integer id){
        LOGGER.info("Get Third Party by id");
        return thirdPartyRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Third Party id not found"));
    }

    public void debitTransaction(Integer thirdPartyId, ThirdPartyTransaction thirdPartyTransaction){
        LOGGER.info("[INIT] Third Party make debit transaction");
        ThirdParty thirdParty = thirdPartyRepository.findById(thirdPartyId).orElseThrow(() -> new DataNotFoundException("Third Party id not found"));
        Account account = accountRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Account id not found"));
        if(account.getSecretKey().equals(thirdPartyTransaction.getSecretKey())){
            account.setBalance(new Money(account.getBalance().decreaseAmount(thirdPartyTransaction.getAmount())));
            accountRepository.save(account);
        } else {
            throw new DataNotFoundException("Secret key doesn't bellow to account id");
        }
        LOGGER.info("[END] Third Party make debit transaction");
    }

    public void creditTransaction(Integer thirdPartyId, ThirdPartyTransaction thirdPartyTransaction){
        LOGGER.info("[INIT] Third Party make credit transaction");
        ThirdParty thirdParty = thirdPartyRepository.findById(thirdPartyId).orElseThrow(() -> new DataNotFoundException("Third Party id not found"));
        Account account = accountRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Account id not found"));
        if(account.getSecretKey().equals(thirdPartyTransaction.getSecretKey())){
            account.setBalance(new Money(account.getBalance().increaseAmount(thirdPartyTransaction.getAmount())));
            accountRepository.save(account);
        } else {
            throw new DataNotFoundException("Secret key doesn't bellow to account id");
        }
        LOGGER.info("[END] Third Party make credit transaction");
    }
}
