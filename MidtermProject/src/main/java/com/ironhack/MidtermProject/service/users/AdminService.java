package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.entities.accounts.*;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import com.ironhack.MidtermProject.model.entities.users.Admin;
import com.ironhack.MidtermProject.model.entities.users.ThirdParty;
import com.ironhack.MidtermProject.model.viewmodel.AccountViewModel;
import com.ironhack.MidtermProject.model.viewmodel.CreditCardViewModel;
import com.ironhack.MidtermProject.model.viewmodel.SavingViewModel;
import com.ironhack.MidtermProject.repository.accounts.*;
import com.ironhack.MidtermProject.repository.users.AccountHolderRepository;
import com.ironhack.MidtermProject.repository.users.AdminRepository;
import com.ironhack.MidtermProject.repository.users.ThirdPartyRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AccountHolderRepository accountsHolderRepository;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private SavingRepository savingsRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    private static final Logger LOGGER = LogManager.getLogger(AdminService.class);

    public List<Admin> findAll(){
        LOGGER.info("Get all Admin users");
        return adminRepository.findAll();
    }

    public Admin findById(Integer id){
        LOGGER.info("Get Admin user by id");
        return adminRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
    }

    public List<Admin> findByName(String name){
        LOGGER.info("Get Admin user by name");
        return adminRepository.findByName(name);
    }

    public void createNewAdmin(Admin admin){
        LOGGER.info("Create new Admin user");
        adminRepository.save(admin);
    }

    // --- CREATE ACCOUNTS ---
    public void createNewSavingsAccount(Integer id, SavingViewModel savingViewModel){
        LOGGER.info("[INIT] Create new Savings Account");
        adminRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        AccountHolder primaryOwner = accountsHolderRepository.findById(savingViewModel.getPrimaryOwnerId()).orElseThrow(() -> new DataNotFoundException("Primary Owner id not found"));
        AccountHolder secondaryOwner;
        Saving saving = new Saving();
        if(savingViewModel.getSecondaryOwnerId() != null){
            secondaryOwner = accountsHolderRepository.findById(savingViewModel.getPrimaryOwnerId()).orElseThrow(() -> new DataNotFoundException("Secondary Owner id not found"));
            saving.setSecondaryOwner(secondaryOwner);
        }
        saving.setBalance(savingViewModel.getBalance());
        saving.setSecretKey(savingViewModel.getSecretKey());
        saving.setStatus(savingViewModel.getStatus());
        saving.setPrimaryOwner(primaryOwner);
        saving.setInterestRate(savingViewModel.getInterestRate());
        saving.setMinimumBalance(savingViewModel.getMinimumBalance());

        if(saving.getInterestRate().compareTo(new BigDecimal("0.5")) == -1){
            System.out.println("good");
        } else{
            saving.setInterestRate(new BigDecimal("0.025"));
        }
        if(saving.getMinimumBalance().intValueExact() <= 1000 && saving.getMinimumBalance().intValueExact() >= 100){
            System.out.println("minimum good");
        } else{
            saving.setMinimumBalance(new BigDecimal("1000"));
        }
        //accountHolders.getAccounts().add(savings);
        //accountsHolderRepository.save(accountHolders);
        savingsRepository.save(saving);
        LOGGER.info("[END] Create new Savings Account");
    }

    public void createNewAccountDepAge(Integer id, AccountViewModel accountViewModel){
        LOGGER.info("[INIT] Create new Account based on user's age");
        adminRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        AccountHolder primaryOwner = accountsHolderRepository.findById(accountViewModel.getPrimaryOwnerId()).orElseThrow(() -> new DataNotFoundException("AccountHolder id not found"));
        AccountHolder secondaryOwner = new AccountHolder();
        Integer primaryOwnerAge = Period.between(primaryOwner.getDateOfBirth(), LocalDate.now()).getYears();
        if(primaryOwnerAge<24){
            createNewStudentChecking(primaryOwner, accountViewModel, secondaryOwner);
        } else{
            createNewCheckingAccount(primaryOwner, accountViewModel, secondaryOwner);
        }
        LOGGER.info("[END] Create new Account based on user's age");
    }

    public void createNewStudentChecking(AccountHolder primaryOwner, AccountViewModel accountViewModel, AccountHolder secondaryOwner){
        StudentChecking studentChecking = new StudentChecking(accountViewModel.getBalance(), accountViewModel.getSecretKey(), accountViewModel.getStatus());
        studentChecking.setPrimaryOwner(primaryOwner);
        if(accountViewModel.getSecondaryOwnerId() != null){
            secondaryOwner = accountsHolderRepository.findById(accountViewModel.getPrimaryOwnerId()).orElseThrow(() -> new DataNotFoundException("Secondary Owner id not found"));
            studentChecking.setSecondaryOwner(secondaryOwner);
        }
        studentCheckingRepository.save(studentChecking);
    }

    public void createNewCheckingAccount(AccountHolder primaryOwner, AccountViewModel accountViewModel, AccountHolder secondaryOwner){
        Checking checking = new Checking();
        if(accountViewModel.getSecondaryOwnerId() != null){
            secondaryOwner = accountsHolderRepository.findById(accountViewModel.getPrimaryOwnerId()).orElseThrow(() -> new DataNotFoundException("Secondary Owner id not found"));
            checking.setSecondaryOwner(secondaryOwner);
        }
        checking.setBalance(accountViewModel.getBalance());
        checking.setSecretKey(accountViewModel.getSecretKey());
        checking.setStatus(accountViewModel.getStatus());
        checking.setPrimaryOwner(primaryOwner);
        checkingRepository.save(checking);
    }


    public void createNewCreditCardAccount(Integer id, CreditCardViewModel creditCardViewModel){
        LOGGER.info("[INIT] Create new Credit Card Account");
        adminRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        AccountHolder primaryOwner = accountsHolderRepository.findById(creditCardViewModel.getPrimaryOwnerId()).orElseThrow(() -> new DataNotFoundException("Primary Owner id not found"));
        AccountHolder secondaryOwner;
        CreditCard creditCard = new CreditCard();
        if(creditCardViewModel.getSecondaryOwnerId() != null){
            secondaryOwner = accountsHolderRepository.findById(creditCardViewModel.getPrimaryOwnerId()).orElseThrow(() -> new DataNotFoundException("Secondary Owner id not found"));
            creditCard.setSecondaryOwner(secondaryOwner);
        }
        creditCard.setBalance(creditCardViewModel.getBalance());
        creditCard.setSecretKey(creditCardViewModel.getSecretKey());
        creditCard.setStatus(creditCardViewModel.getStatus());
        creditCard.setPrimaryOwner(primaryOwner);
        creditCard.setCreditLimit(creditCardViewModel.getCreditLimit());
        creditCard.setInterestRate(creditCardViewModel.getInterestRate());

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
        LOGGER.info("[END] Create new Credit Card Account");
    }

    public List<Object[]> checkAccountBalance(Integer accountId){
        LOGGER.info("[INIT] Admin Check Account Balance");
        accountRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Account id not found"));
        LOGGER.info("[END] Admin Check Account Balance");
        return adminRepository.checkAccountBalance(accountId);
    }

    // --- CREATE THIRD PARTY ---

    public void createNewThirdParty(Integer adminId, ThirdParty thirdParty){
        LOGGER.info("[INIT] Admin Create New Third Party");
        adminRepository.findById(adminId).orElseThrow(() -> new DataNotFoundException("Third Party id not found"));
        thirdPartyRepository.save(thirdParty);
        updateThirdPartyHashMap(thirdParty.getUserId());
        LOGGER.info("[END] Admin Create New Third Party");
    }

    public void updateThirdPartyHashMap(Integer thirdPartyId){
        LOGGER.info("[INIT] Update Hash Map to Create New Third Party");
        ThirdParty thirdParty = thirdPartyRepository.findById(thirdPartyId).orElseThrow(() -> new DataNotFoundException("Third Party id not found"));
        HashMap<Integer, String> updateThirdParty = new HashMap<Integer, String>();
        updateThirdParty.put(thirdPartyId, thirdParty.getName());
        thirdParty.setAccountDetails(updateThirdParty);
        thirdPartyRepository.save(thirdParty);
        LOGGER.info("[END] Update Hash Map to Create New Third Party");
    }
}
