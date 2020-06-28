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

import java.math.BigDecimal;
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

    /**
     * Find all ThirdParty created.
     * @return a list of third party users.
     */
    public List<ThirdParty> findAll(){
        LOGGER.info("Get all Third Party users");
        return thirdPartyRepository.findAll();
    }

    /**
     * Find ThirdParty By id.
     * @param id receives an integer id of user.
     * @return a third party user corresponding to that id.
     */
    public ThirdParty findById(Integer id){
        LOGGER.info("Get Third Party with id " + id);
        return thirdPartyRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Third Party id not found"));
    }

    /**
     * Allows third party users to login into their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return a third party logged in.
     */
    public ThirdParty loginThirdParty(LoginAccount loginAccount){
        LOGGER.info("Third party with id " + loginAccount.getId() + " tried to login");
        ThirdParty thirdParty = thirdPartyRepository.findById(loginAccount.getId()).orElseThrow(() -> new DataNotFoundException("Third Party id not found"));

        LOGGER.info("Check if third party " + loginAccount.getId() + " is already logged in");
        if(thirdParty.isLogged()){
            throw new DataNotFoundException("Third party is already logged in");
        }

        LOGGER.info("Check if password provided belongs to respective third party");
        if(!thirdParty.getPassword().equals(loginAccount.getPassword())){
            throw new DataNotFoundException("Password incorrect, try again");
        }

        thirdParty.login();
        thirdPartyRepository.save(thirdParty);
        LOGGER.info("Third party with id " + loginAccount.getId() + " just logged in");
        return thirdParty;
    }

    /**
     * Allows third party users to logout from their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return a third party logged out.
     */
    public ThirdParty logOutThirdParty(LoginAccount loginAccount){
        LOGGER.info("Third party with id " + loginAccount.getId() + " tried to logout");
        ThirdParty thirdParty = thirdPartyRepository.findById(loginAccount.getId()).orElseThrow(() -> new DataNotFoundException("Third Party id not found"));

        LOGGER.info("Check if third party " + loginAccount.getId() + " is already logged out");
        if(!thirdParty.isLogged()){
            throw new DataNotFoundException("Third party is already logged out");
        }

        LOGGER.info("Check if password provided belongs to respective third party");
        if(!thirdParty.getPassword().equals(loginAccount.getPassword())){
            throw new DataNotFoundException("Password incorrect, try again");
        }

        thirdParty.logOut();
        thirdPartyRepository.save(thirdParty);
        LOGGER.info("Third party with id " + loginAccount.getId() + " just logged out");
        return thirdParty;
    }

    /**
     * Allows Third Party Users to debit amount in any type of account.
     * @param hashedKey receives the hashedKey unique that third party has.
     * @param thirdPartyTransaction receives ThirdPartyTransaction containing account id, account secretKey and amount to debit.
     */
    public void debitTransaction(String hashedKey, ThirdPartyTransaction thirdPartyTransaction){
        LOGGER.info("[INIT] Third Party with hashedKey " + hashedKey +  " tried to make debit transaction in account " + thirdPartyTransaction.getAccountId());

        checkHashedKeyExists(hashedKey);

        LOGGER.info("Confirm if account with id " + thirdPartyTransaction.getAccountId() + " exists");
        Account account = accountRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Account id not found"));

        LOGGER.info("Check if amount provided is positive");
        if(thirdPartyTransaction.getAmount().compareTo(BigDecimal.ZERO) == -1){
            throw new DataNotFoundException("Amount cannot be negative");
        }

        if(account.getAccountType().equals(AccountType.CHECKING)){
            LOGGER.info("Confirm if account with id " + thirdPartyTransaction.getAccountId() + " is from type checking");
            Checking checkingToDebit = checkingRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Checking account id not found"));

            LOGGER.info("Check if account's secret key is the same as provided");
            if(!checkingToDebit.getSecretKey().equals(thirdPartyTransaction.getSecretKey())){
                throw new DataNotFoundException("Secret key doesn't match checking account id");
            }

            LOGGER.info("Third Party make debit transaction to a Checking Account with id " + thirdPartyTransaction.getAccountId());
            checkingToDebit.setBalance(new Money(checkingToDebit.getBalance().decreaseAmount(thirdPartyTransaction.getAmount())));
            if(checkingToDebit.getLastPenalty() == 0 && checkingToDebit.getBalance().getAmount().compareTo(checkingToDebit.getMinimumBalance()) == -1){
                checkingToDebit.setBalance(new Money(checkingToDebit.getBalance().decreaseAmount(checkingToDebit.getPenaltyFee())));
                checkingToDebit.setLastPenalty(1);
            }
            checkingRepository.save(checkingToDebit);
        }

        else if(account.getAccountType().equals(AccountType.SAVINGS)){
            LOGGER.info("Confirm if account with id " + thirdPartyTransaction.getAccountId() + " is from type savings");
            Saving savingToDebit = savingRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Saving account id not found"));

            LOGGER.info("Check if account's secret key is the same as provided");
            if(!savingToDebit.getSecretKey().equals(thirdPartyTransaction.getSecretKey())){
                throw new DataNotFoundException("Secret key doesn't match savings account id");
            }

            LOGGER.info("Third Party make debit transaction to a Savings Account with id " + thirdPartyTransaction.getAccountId());
            savingToDebit.setBalance(new Money(savingToDebit.getBalance().decreaseAmount(thirdPartyTransaction.getAmount())));
            if(savingToDebit.getLastPenalty() == 0 && savingToDebit.getBalance().getAmount().compareTo(savingToDebit.getMinimumBalance()) == -1){
                savingToDebit.setBalance(new Money(savingToDebit.getBalance().decreaseAmount(savingToDebit.getPenaltyFee())));
                savingToDebit.setLastPenalty(1);
            }
            savingRepository.save(savingToDebit);
        }

        else if(account.getAccountType().equals(AccountType.CREDIT_CARD)){
            LOGGER.info("Confirm if account with id " + thirdPartyTransaction.getAccountId() + " is from type credit card");
            CreditCard creditCardToDebit = creditCardRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Credit card account id not found"));

            LOGGER.info("Third Party make debit transaction to a Credit Account with id " + thirdPartyTransaction.getAccountId());
            creditCardToDebit.setBalance(new Money(creditCardToDebit.getBalance().decreaseAmount(thirdPartyTransaction.getAmount())));
            creditCardRepository.save(creditCardToDebit);
        }

        else if(account.getAccountType().equals(AccountType.STUDENT_CHECKING)){
            LOGGER.info("Confirm if account with id " + thirdPartyTransaction.getAccountId() + " is from type student checking");
            StudentChecking studentCheckingToDebit = studentCheckingRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Student Checking account id not found"));

            LOGGER.info("Check if account's secret key is the same as provided");
            if(!studentCheckingToDebit.getSecretKey().equals(thirdPartyTransaction.getSecretKey())){
                throw new DataNotFoundException("Secret key doesn't match student checking account id");
            }

            LOGGER.info("Third Party make debit transaction to a Student Checking Account with id " + thirdPartyTransaction.getAccountId());
            studentCheckingToDebit.setBalance(new Money(studentCheckingToDebit.getBalance().decreaseAmount(thirdPartyTransaction.getAmount())));
            studentCheckingRepository.save(studentCheckingToDebit);
        }
        LOGGER.info("[END] Third Party with hashedKey " + hashedKey +  " tried to make debit transaction in account " + thirdPartyTransaction.getAccountId());
    }

    /**
     * Allows Third Party Users to credit amount in any type of account.
     * @param hashedKey receives the hashedKey unique that third party has.
     * @param thirdPartyTransaction receives ThirdPartyTransaction containing account id, account secretKey and amount to credit.
     */
    public void creditTransaction(String hashedKey, ThirdPartyTransaction thirdPartyTransaction){
        LOGGER.info("[INIT] Third Party with hashedKey " + hashedKey +  " tried to make debit transaction in account " + thirdPartyTransaction.getAccountId());

        checkHashedKeyExists(hashedKey);

        LOGGER.info("Confirm if account with id " + thirdPartyTransaction.getAccountId() + " exists");
        Account account = accountRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Account id not found"));

        LOGGER.info("Check if amount provided is positive");
        if(thirdPartyTransaction.getAmount().compareTo(BigDecimal.ZERO) == -1){
            throw new DataNotFoundException("Amount cannot be negative");
        }

        if(account.getAccountType().equals(AccountType.CHECKING)){
            LOGGER.info("Confirm if account with id " + thirdPartyTransaction.getAccountId() + " is from type checking");
            Checking checkingToCredit = checkingRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Checking account id not found"));

            LOGGER.info("Check if account's secret key is the same as provided");
            if(!checkingToCredit.getSecretKey().equals(thirdPartyTransaction.getSecretKey())){
                throw new DataNotFoundException("Secret key doesn't match checking account id");
            }

            LOGGER.info("Third Party make credit transaction to a Checking Account with id " + thirdPartyTransaction.getAccountId());
            checkingToCredit.setBalance(new Money(checkingToCredit.getBalance().increaseAmount(thirdPartyTransaction.getAmount())));
            if(checkingToCredit.getLastPenalty() == 1 && checkingToCredit.getBalance().getAmount().compareTo(checkingToCredit.getMinimumBalance()) ==1){
                checkingToCredit.setLastPenalty(0);
            }
            checkingRepository.save(checkingToCredit);
        }

        else if(account.getAccountType().equals(AccountType.SAVINGS)){
            LOGGER.info("Confirm if account with id " + thirdPartyTransaction.getAccountId() + " is from type savings");
            Saving savingToCredit = savingRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Savings account id not found"));

            LOGGER.info("Check if account's secret key is the same as provided");
            if(!savingToCredit.getSecretKey().equals(thirdPartyTransaction.getSecretKey())){
                throw new DataNotFoundException("Secret key doesn't match savings account id");
            }

            LOGGER.info("Third Party make credit transaction to a Savings Account with id " + thirdPartyTransaction.getAccountId());
            savingToCredit.setBalance(new Money(savingToCredit.getBalance().increaseAmount(thirdPartyTransaction.getAmount())));
            if(savingToCredit.getLastPenalty() == 1 && savingToCredit.getBalance().getAmount().compareTo(savingToCredit.getMinimumBalance()) == 1){
                savingToCredit.setLastPenalty(0);
            }savingRepository.save(savingToCredit);
        }

        else if(account.getAccountType().equals(AccountType.CREDIT_CARD)){
            LOGGER.info("Confirm if account with id " + thirdPartyTransaction.getAccountId() + " is from type credit card");
            CreditCard creditCardToCredit = creditCardRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Credit card account id not found"));

            LOGGER.info("Third Party make credit transaction to a Credit Card Account with id " + thirdPartyTransaction.getAccountId());
            creditCardToCredit.setBalance(new Money(creditCardToCredit.getBalance().increaseAmount(thirdPartyTransaction.getAmount())));
            creditCardRepository.save(creditCardToCredit);
        }

        else if(account.getAccountType().equals(AccountType.STUDENT_CHECKING)){
            LOGGER.info("Confirm if account with id " + thirdPartyTransaction.getAccountId() + " is from type student checking");
            StudentChecking studentCheckingToCredit = studentCheckingRepository.findById(thirdPartyTransaction.getAccountId()).orElseThrow(() -> new DataNotFoundException("Student checking account id not found"));

            LOGGER.info("Check if account's secret key is the same as provided");
            if(!studentCheckingToCredit.getSecretKey().equals(thirdPartyTransaction.getSecretKey())){
                throw new DataNotFoundException("Secret key doesn't match checking account id");
            }

            LOGGER.info("Third Party make credit transaction to a Student Checking Account with id " + thirdPartyTransaction.getAccountId());
            studentCheckingToCredit.setBalance(new Money(studentCheckingToCredit.getBalance().increaseAmount(thirdPartyTransaction.getAmount())));
            studentCheckingRepository.save(studentCheckingToCredit);
        }

        LOGGER.info("[END] Third Party make credit transaction");
    }

    /**
     * Method that checks if hashedKey that Third Party Users provide exists.
     * @param hashedKey receives the hashedKey unique that third party has.
     */
    public void checkHashedKeyExists(String hashedKey){
        LOGGER.info("Confirm Third Party hashed key " + hashedKey + " exists to make transaction");
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
