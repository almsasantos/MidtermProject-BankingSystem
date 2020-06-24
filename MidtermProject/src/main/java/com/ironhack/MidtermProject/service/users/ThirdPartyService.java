package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.dto.ThirdPartyTransaction;
import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.*;
import com.ironhack.MidtermProject.model.entities.users.ThirdParty;
import com.ironhack.MidtermProject.repository.accounts.*;
import com.ironhack.MidtermProject.repository.users.ThirdPartyRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ThirdPartyService {
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    private static final Logger LOGGER = LogManager.getLogger(ThirdPartyService.class);

    public List<ThirdParty> findAll(){
        LOGGER.info("Get all Third Party users");
        return thirdPartyRepository.findAll();
    }

    public ThirdParty findById(Integer id){
        LOGGER.info("Get Third Party by id");
        return thirdPartyRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Third Party id not found"));
    }

    public ThirdParty loginThirdParty(LoginAccount loginAccount){
        ThirdParty thirdParty = thirdPartyRepository.findById(loginAccount.getId()).orElseThrow(() -> new DataNotFoundException("Third Party id not found"));
        if(!thirdParty.getPassword().equals(loginAccount.getPassword())){
            throw new DataNotFoundException("Password incorrect, try again");
        }
        thirdParty.login();
        thirdPartyRepository.save(thirdParty);
        return thirdParty;
    }

    public ThirdParty logOutThirdParty(LoginAccount loginAccount){
        ThirdParty thirdParty = thirdPartyRepository.findById(loginAccount.getId()).orElseThrow(() -> new DataNotFoundException("Third Party id not found"));
        if(!thirdParty.getPassword().equals(loginAccount.getPassword())){
            throw new DataNotFoundException("Password incorrect, try again");
        }
        thirdParty.logOut();
        thirdPartyRepository.save(thirdParty);
        return thirdParty;
    }

    public void thirdPartyIsLogged(ThirdParty thirdParty){
        LOGGER.info("Check if Third Party is logged in");
        if(thirdParty.isLogged() == false){
            throw new DataNotFoundException("Third Party must be logged in");
        }
    }

    public void debitTransaction(String hashedKey, ThirdPartyTransaction thirdPartyTransaction){
        LOGGER.info("[INIT] Third Party make debit transaction");



        LOGGER.info("Confirm Third Party hashed key exists to make debit transaction");
        checkHashedKeyExists(hashedKey);

        Account account = accountRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Account id not found"));
        if(account.getAccountType().equals(AccountType.CHECKING)){
            LOGGER.info("Third Party make debit transaction to a Checking Account");
            Checking checkingToDebit = checkingRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Checking account id not found"));
            if(!checkingToDebit.getSecretKey().equals(thirdPartyTransaction.getSecretKey())){
                throw new DataNotFoundException("Secret key doesn't match checking account id");
            }
            checkingToDebit.setBalance(new Money(checkingToDebit.getBalance().decreaseAmount(thirdPartyTransaction.getAmount())));
            if(checkingToDebit.getLastPenalty() == 0 && checkingToDebit.getBalance().getAmount().compareTo(checkingToDebit.getMinimumBalance()) == -1){
                checkingToDebit.setBalance(new Money(checkingToDebit.getBalance().decreaseAmount(checkingToDebit.getPenaltyFee())));
                checkingToDebit.setLastPenalty(1);
            }
            checkingRepository.save(checkingToDebit);
        }

        else if(account.getAccountType().equals(AccountType.SAVINGS)){
            LOGGER.info("Third Party make debit transaction to a Savings Account");
            Saving savingToDebit = savingRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Saving account id not found"));
            if(!savingToDebit.getSecretKey().equals(thirdPartyTransaction.getSecretKey())){
                throw new DataNotFoundException("Secret key doesn't match savings account id");
            }
            savingToDebit.setBalance(new Money(savingToDebit.getBalance().decreaseAmount(thirdPartyTransaction.getAmount())));
            if(savingToDebit.getLastPenalty() == 0 && savingToDebit.getBalance().getAmount().compareTo(savingToDebit.getMinimumBalance()) == -1){
                savingToDebit.setBalance(new Money(savingToDebit.getBalance().decreaseAmount(savingToDebit.getPenaltyFee())));
                savingToDebit.setLastPenalty(1);
            }
            savingRepository.save(savingToDebit);
        }

        else if(account.getAccountType().equals(AccountType.CREDIT_CARD)){
            LOGGER.info("Third Party make debit transaction to a Credit Account");
            CreditCard creditCardToDebit = creditCardRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Credit card account id not found"));
            creditCardToDebit.setBalance(new Money(creditCardToDebit.getBalance().decreaseAmount(thirdPartyTransaction.getAmount())));
            creditCardRepository.save(creditCardToDebit);
        }

        else if(account.getAccountType().equals(AccountType.STUDENT_CHECKING)){
            LOGGER.info("Third Party make debit transaction to a Student Checking Account");
            StudentChecking studentCheckingToDebit = studentCheckingRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Student Checking account id not found"));
            if(!studentCheckingToDebit.getSecretKey().equals(thirdPartyTransaction.getSecretKey())){
                throw new DataNotFoundException("Secret key doesn't match student checking account id");
            }
            studentCheckingToDebit.setBalance(new Money(studentCheckingToDebit.getBalance().decreaseAmount(thirdPartyTransaction.getAmount())));
            studentCheckingRepository.save(studentCheckingToDebit);
        }
        LOGGER.info("[END] Third Party make debit transaction");
    }

    public void creditTransaction(String hashedKey, ThirdPartyTransaction thirdPartyTransaction){
        LOGGER.info("[INIT] Third Party make credit transaction");

        LOGGER.info("Confirm Third Party hashed key exists to make credit transaction");
        checkHashedKeyExists(hashedKey);

        Account account = accountRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Account id not found"));

        if(account.getAccountType().equals(AccountType.CHECKING)){
            LOGGER.info("Third Party make credit transaction to a Checking Account");
            Checking checkingToCredit = checkingRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Checking account id not found"));
            if(!checkingToCredit.getSecretKey().equals(thirdPartyTransaction.getSecretKey())){
                throw new DataNotFoundException("Secret key doesn't match checking account id");
            }
            checkingToCredit.setBalance(new Money(checkingToCredit.getBalance().increaseAmount(thirdPartyTransaction.getAmount())));
            if(checkingToCredit.getLastPenalty() == 1 && checkingToCredit.getBalance().getAmount().compareTo(checkingToCredit.getMinimumBalance()) ==1){
                checkingToCredit.setLastPenalty(0);
            }
            checkingRepository.save(checkingToCredit);
        }

        else if(account.getAccountType().equals(AccountType.SAVINGS)){
            LOGGER.info("Third Party make credit transaction to a Savings Account");
            Saving savingToCredit = savingRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Savings account id not found"));

            if(!savingToCredit.getSecretKey().equals(thirdPartyTransaction.getSecretKey())){
                throw new DataNotFoundException("Secret key doesn't match savings account id");
            }
            savingToCredit.setBalance(new Money(savingToCredit.getBalance().increaseAmount(thirdPartyTransaction.getAmount())));
            if(savingToCredit.getLastPenalty() == 1 && savingToCredit.getBalance().getAmount().compareTo(savingToCredit.getMinimumBalance()) == 1){
                savingToCredit.setLastPenalty(0);
            }savingRepository.save(savingToCredit);
        }

        else if(account.getAccountType().equals(AccountType.CREDIT_CARD)){
            LOGGER.info("Third Party make credit transaction to a Credit Card Account");
            CreditCard creditCardToCredit = creditCardRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Credit card account id not found"));
            creditCardToCredit.setBalance(new Money(creditCardToCredit.getBalance().increaseAmount(thirdPartyTransaction.getAmount())));
            creditCardRepository.save(creditCardToCredit);
        }

        else if(account.getAccountType().equals(AccountType.STUDENT_CHECKING)){
            LOGGER.info("Third Party make credit transaction to a Student Checking Account");
            StudentChecking studentCheckingToCredit = studentCheckingRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Student checking account id not found"));
            if(!studentCheckingToCredit.getSecretKey().equals(thirdPartyTransaction.getSecretKey())){
                throw new DataNotFoundException("Secret key doesn't match checking account id");
            }
            studentCheckingToCredit.setBalance(new Money(studentCheckingToCredit.getBalance().increaseAmount(thirdPartyTransaction.getAmount())));
            studentCheckingRepository.save(studentCheckingToCredit);
        }

        LOGGER.info("[END] Third Party make credit transaction");
    }

    public void checkHashedKeyExists(String hashedKey){
        List<Set<String>> thirdPartyMap =  thirdPartyRepository.findAll().stream().map(t -> t.getAccountDetails().keySet()).collect(Collectors.toList());
        List<String> definiteKey = new ArrayList<String>();
        for(Set<String> hashedkeyList: thirdPartyMap){
            for(String key: hashedkeyList){
                definiteKey.add(key);
            }
        }
        if(!definiteKey.contains(hashedKey)){
            throw new DataNotFoundException("Hashed key doesn't exist");
        }
    }
}
