package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.entities.Admin;
import com.ironhack.MidtermProject.model.entities.CreditCard;
import com.ironhack.MidtermProject.model.entities.Saving;
import com.ironhack.MidtermProject.repository.accounts.*;
import com.ironhack.MidtermProject.repository.users.AccountHolderRepository;
import com.ironhack.MidtermProject.repository.users.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountsHolderRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;


    public List<Admin> findAll(){
        return adminRepository.findAll();
    }

    public Admin findById(Integer id){
        return adminRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
    }

    public void createNewAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public void createNewSavingsAccount(Integer id, Saving savings){
        adminRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        //AccountHolders accountHolders = accountsHolderRepository.findById(savings.getPrimaryOwner().getUserId()).orElseThrow(() -> new DataNotFoundException("AccountHolder id not found"));
        if(savings.getInterestRate().compareTo(new BigDecimal("0.5")) == -1){
            System.out.println("good");
        } else{
            savings.setInterestRate(new BigDecimal("0.025"));
        }
        if(savings.getMinimumBalance().intValueExact() <= 1000 && savings.getMinimumBalance().intValueExact() >= 100){
            System.out.println("minimum good");
        } else{
            savings.setMinimumBalance(new BigDecimal("1000"));
        }
        //accountHolders.getAccounts().add(savings);
        //accountsHolderRepository.save(accountHolders);
        savingsRepository.save(savings);
    }
/*
    public void createNewAccountDepAge(Integer id, Account account){
        adminRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        Integer primaryOwnerAge = Period.between(account.getPrimaryOwner().getDateOfBirth(), LocalDate.now()).getYears();
        //AccountHolders accountHolders = accountsHolderRepository.findById(account.getPrimaryOwner().getId()).orElseThrow(() -> new DataNotFoundException("AccountHolder id not found"));
        if(primaryOwnerAge<24){
            StudentChecking studentChecking = new StudentChecking(account.getBalance(), account.getSecretKey(), account.getStatus());
            studentChecking.setAccountId(account.getAccountId());
            studentChecking.setPenaltyFee(account.getPenaltyFee());
            studentChecking.setPrimaryOwner(account.getPrimaryOwner());
            studentChecking.setSecondaryOwner(account.getSecondaryOwner());
            studentCheckingRepository.save(studentChecking);
        } else{
            Checking checking = new Checking();
            checking.setAccountId(account.getAccountId());
            checking.setBalance(account.getBalance());
            checking.setPenaltyFee(account.getPenaltyFee());
            checking.setSecretKey(account.getSecretKey());
            checking.setStatus(account.getStatus());
            checking.setPrimaryOwner(account.getPrimaryOwner());
            checking.setSecondaryOwner(account.getSecondaryOwner());
            checkingRepository.save(checking);
        }
    }

 */

    public void createNewCreditCardAccount(Integer id, CreditCard creditCard){
        adminRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        if(creditCard.getCreditLimit().intValueExact()>=100 && creditCard.getCreditLimit().intValueExact()<=1000){
            System.out.println("good");
        } else{
            creditCard.setCreditLimit(new BigDecimal("100"));
        }
        if(creditCard.getInterestRate().compareTo(new BigDecimal("0.2")) == -1 && creditCard.getInterestRate().compareTo(new BigDecimal("0.1")) == 1){
            System.out.println("interest good");
        } else{
            creditCard.setInterestRate(new BigDecimal("0.2"));
        }
        creditCardRepository.save(creditCard);
    }

    public List<Object[]> checkAccountBalance(Integer accountId){
        accountRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Account id not found"));
        return adminRepository.checkAccountBalance(accountId);
    }
}
