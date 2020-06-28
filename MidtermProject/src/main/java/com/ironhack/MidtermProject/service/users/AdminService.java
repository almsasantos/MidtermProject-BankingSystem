package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.dto.ChangeBalance;
import com.ironhack.MidtermProject.dto.CreateThirdParty;
import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.classes.Money;
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
import com.ironhack.MidtermProject.repository.users.UsersRepository;
import com.ironhack.MidtermProject.service.accounts.CreditCardService;
import com.ironhack.MidtermProject.service.accounts.SavingsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountsHolderRepository;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private SavingRepository savingsRepository;

    @Autowired
    private SavingsService savingsService;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    private static final Logger LOGGER = LogManager.getLogger(AdminService.class);

    /**
     * Find all Admin created.
     * @return a list of admin users.
     */
    public List<Admin> findAll() {
        LOGGER.info("Get all Admin users");
        return adminRepository.findAll();
    }

    /**
     * Find Admin by id.
     * @param id receives an integer id of user.
     * @return an admin user corresponding to that id.
     */
    public Admin findById(Integer id) {
        LOGGER.info("Get Admin user by " + id);
        return adminRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
    }

    /**
     * Find Admin by name.
     * @param name receives a string with a name.
     * @return a list of admin users.
     */
    public List<Admin> findByName(String name) {
        LOGGER.info("Get Admin user by " + name);
        return adminRepository.findByName(name);
    }

    /**
     * Creates a new admin
     * @param admin receives an admin with name and password defined.
     */
    public void createNewAdmin(Admin admin) {
        LOGGER.info("Create new Admin user");
        adminRepository.save(admin);
    }

    /**
     * Allows admin users to login into their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return an admin logged in.
     */
    public Admin loginAdmin(LoginAccount loginAccount) {
        LOGGER.info("[INIT] Login Admin " + loginAccount.getId());
        Admin admin = adminRepository.findById(loginAccount.getId()).orElseThrow(() -> new DataNotFoundException("Admin id not found"));

        LOGGER.info("Check if admin " + loginAccount.getId() + " is already logged in");
        if(admin.isLogged()){
            throw new DataNotFoundException("Admin is already logged in");
        }

        LOGGER.info("Check if password provided belongs to respective admin");
        if (!admin.getPassword().equals(loginAccount.getPassword())) {
            throw new DataNotFoundException("Password incorrect, try again");
        }

        admin.login();
        adminRepository.save(admin);
        LOGGER.info("[END] Login Admin " + loginAccount.getId());
        return admin;
    }

    /**
     * Allows admin users to logout from their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return an admin logged out.
     */
    public Admin logOutAdmin(LoginAccount loginAccount) {
        LOGGER.info("[INIT] Logout Admin " + loginAccount.getId());
        Admin admin = adminRepository.findById(loginAccount.getId()).orElseThrow(() -> new DataNotFoundException("Admin id not found"));

        LOGGER.info("Check if admin " + loginAccount.getId() + " is already logged out");
        if(!admin.isLogged()){
            throw new DataNotFoundException("Admin is already logged out");
        }

        LOGGER.info("Check if password provided belongs to respective admin");
        if (!admin.getPassword().equals(loginAccount.getPassword())) {
            throw new DataNotFoundException("Password incorrect, try again");
        }

        admin.logOut();
        adminRepository.save(admin);
        LOGGER.info("[END] Logout Admin " + loginAccount.getId());
        return admin;
    }

    /**
     * Check if admin is logged in into their accounts.
     * @param admin receives an admin with name and password defined.
     */
    public void adminIsLogged(Admin admin) {
        LOGGER.info("Check if admin " + admin.getUserId() + " exists and if it's logged in");
        if (admin.isLogged() == false) {
            throw new DataNotFoundException("Admin must be logged in");
        }
    }

    // --- CREATE ACCOUNTS ---

    /**
     * Creates new savings account.
     * @param id receives an integer with id from admin.
     * @param savingViewModel receives a SavingViewModel that will allow a savings account to be created.
     */
    public void createNewSavingsAccount(Integer id, SavingViewModel savingViewModel) {
        LOGGER.info("[INIT] Admin " + id + " creates new Savings Account");

        Admin admin = adminRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        adminIsLogged(admin);

        LOGGER.info("Check if balance provided is positive");
        if (savingViewModel.getBalance().compareTo(BigDecimal.ZERO) == -1) {
            throw new DataNotFoundException("Balance can't be less than zero");
        }

        LOGGER.info("Check that primary owner " + savingViewModel.getPrimaryOwnerId() + " exists");
        AccountHolder primaryOwner = accountsHolderRepository.findById(savingViewModel.getPrimaryOwnerId()).orElseThrow(() -> new DataNotFoundException("Primary Owner id not found"));
        AccountHolder secondaryOwner;
        Saving saving = new Saving();

        if (savingViewModel.getSecondaryOwnerId() != null) {
            LOGGER.info("Check that secondary owner " + savingViewModel.getSecondaryOwnerId() + " exists");
            secondaryOwner = accountsHolderRepository.findById(savingViewModel.getSecondaryOwnerId()).orElseThrow(() -> new DataNotFoundException("Secondary Owner id not found"));
            saving.setSecondaryOwner(secondaryOwner);
            secondaryOwner.getAccounts().add(saving);
            accountsHolderRepository.save(secondaryOwner);
        }

        saving.setBalance(new Money(savingViewModel.getBalance()));
        saving.setSecretKey(savingViewModel.getSecretKey());
        saving.setStatus(savingViewModel.getStatus());
        saving.setPrimaryOwner(primaryOwner);

        LOGGER.info("Check that values of interest rate and minimum balance are correct");
        if (savingViewModel.getInterestRate() != null) {
            saving.setInterestRate(savingViewModel.getInterestRate());
        }
        if (savingViewModel.getMinimumBalance() != null) {
            saving.setMinimumBalance(savingViewModel.getMinimumBalance());
        }

        if (saving.getInterestRate().compareTo(new BigDecimal("0.5")) <= 0) {
            System.out.println("good");
        } else {
            saving.setInterestRate(new BigDecimal("0.025"));
        }
        if (saving.getMinimumBalance().intValueExact() <= 1000 && saving.getMinimumBalance().intValueExact() >= 100) {
            System.out.println("minimum good");
        } else {
            saving.setMinimumBalance(new BigDecimal("1000"));
        }
        primaryOwner.getAccounts().add(saving);
        accountsHolderRepository.save(primaryOwner);
        savingsRepository.save(saving);
        LOGGER.info("[END] Admin " + id + " creates new Savings Account");
    }

    /**
     * Creates new checking or student checking account depending on the age of primary owner.
     * @param id receives an integer with id from admin.
     * @param accountViewModel receives an AccountViewModel that will allow any of both accounts to be created.
     */
    public void createNewAccountDepAge(Integer id, AccountViewModel accountViewModel) {
        LOGGER.info("[INIT] Admin " + id + " creates new Account based on user's age");

        Admin admin = adminRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        adminIsLogged(admin);

        LOGGER.info("Check if balance provided is positive");
        if (accountViewModel.getBalance().compareTo(BigDecimal.ZERO) == -1) {
            throw new DataNotFoundException("Balance can't be less than zero");
        }

        LOGGER.info("Check that primary owner " + accountViewModel.getPrimaryOwnerId() + " exists");
        AccountHolder primaryOwner = accountsHolderRepository.findById(accountViewModel.getPrimaryOwnerId()).orElseThrow(() -> new DataNotFoundException("AccountHolder id not found"));
        AccountHolder secondaryOwner = new AccountHolder();

        LOGGER.info("Check that primary owner's age " + accountViewModel.getPrimaryOwnerId() + " to decide which type of account to create");
        Integer primaryOwnerAge = Period.between(primaryOwner.getDateOfBirth(), LocalDate.now()).getYears();
        if (primaryOwnerAge < 24) {
            createNewStudentChecking(primaryOwner, accountViewModel, secondaryOwner);
        } else {
            createNewCheckingAccount(primaryOwner, accountViewModel, secondaryOwner);
        }

        LOGGER.info("[END] Admin " + id + " creates new Account based on user's age");
    }

    /**
     * Creates new student checking account.
     * @param primaryOwner receives an AccountHolder with information from the primaryOwner of that account.
     * @param accountViewModel receives an AccountViewModel that will allow a student checking account to be created.
     * @param secondaryOwner receives an optional AccountHolder with information from the secondaryOwner of that account.
     */
    public void createNewStudentChecking(AccountHolder primaryOwner, AccountViewModel accountViewModel, AccountHolder secondaryOwner) {
        StudentChecking studentChecking = new StudentChecking(new Money(accountViewModel.getBalance()), accountViewModel.getSecretKey(), accountViewModel.getStatus());
        studentChecking.setPrimaryOwner(primaryOwner);
        if (accountViewModel.getSecondaryOwnerId() != null) {
            secondaryOwner = accountsHolderRepository.findById(accountViewModel.getSecondaryOwnerId()).orElseThrow(() -> new DataNotFoundException("Secondary Owner id not found"));
            studentChecking.setSecondaryOwner(secondaryOwner);
        }
        studentCheckingRepository.save(studentChecking);

        LOGGER.info("A new Student Checking account was created");
    }

    /**
     * Creates new checking account.
     * @param primaryOwner receives an AccountHolder with information from the primaryOwner of that account.
     * @param accountViewModel receives an AccountViewModel that will allow a checking account to be created.
     * @param secondaryOwner receives an optional AccountHolder with information from the secondaryOwner of that account.
     */
    public void createNewCheckingAccount(AccountHolder primaryOwner, AccountViewModel accountViewModel, AccountHolder secondaryOwner) {
        Checking checking = new Checking();
        if (accountViewModel.getSecondaryOwnerId() != null) {
            secondaryOwner = accountsHolderRepository.findById(accountViewModel.getSecondaryOwnerId()).orElseThrow(() -> new DataNotFoundException("Secondary Owner id not found"));
            checking.setSecondaryOwner(secondaryOwner);
        }
        checking.setBalance(new Money(accountViewModel.getBalance()));
        checking.setSecretKey(accountViewModel.getSecretKey());
        checking.setStatus(accountViewModel.getStatus());
        checking.setPrimaryOwner(primaryOwner);
        checkingRepository.save(checking);

        LOGGER.info("A new Checking account was created");
    }

    /**
     * Creates new credit card account.
     * @param id receives an integer with id from admin.
     * @param creditCardViewModel receives a creditCardViewModel that will allow a credit card account to be created.
     */
    public void createNewCreditCardAccount(Integer id, CreditCardViewModel creditCardViewModel) {
        LOGGER.info("[INIT] Admin " + id + " creates new Credit Card account");

        Admin admin = adminRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        adminIsLogged(admin);

        LOGGER.info("Check if balance provided is positive");
        if (creditCardViewModel.getBalance().compareTo(BigDecimal.ZERO) == -1) {
            throw new DataNotFoundException("Balance can't be less than zero");
        }

        LOGGER.info("Check that primary owner " + creditCardViewModel.getPrimaryOwnerId() + " exists");
        AccountHolder primaryOwner = accountsHolderRepository.findById(creditCardViewModel.getPrimaryOwnerId()).orElseThrow(() -> new DataNotFoundException("Primary Owner id not found"));
        AccountHolder secondaryOwner;
        CreditCard creditCard = new CreditCard();

        if (creditCardViewModel.getSecondaryOwnerId() != null) {
            LOGGER.info("Check that secondary owner " + creditCardViewModel.getSecondaryOwnerId() + " exists");
            secondaryOwner = accountsHolderRepository.findById(creditCardViewModel.getSecondaryOwnerId()).orElseThrow(() -> new DataNotFoundException("Secondary Owner id not found"));
            creditCard.setSecondaryOwner(secondaryOwner);
            secondaryOwner.getAccounts().add(creditCard);
            accountsHolderRepository.save(secondaryOwner);
        }

        creditCard.setBalance(new Money(creditCardViewModel.getBalance()));

        creditCard.setPrimaryOwner(primaryOwner);

        LOGGER.info("Check that values of interest rate and credit limit are correct");
        if (creditCardViewModel.getCreditLimit() != null) {
            creditCard.setCreditLimit(creditCardViewModel.getCreditLimit());
        }
        if (creditCardViewModel.getInterestRate() != null) {
            creditCard.setInterestRate(creditCardViewModel.getInterestRate());
        }

        if (creditCard.getCreditLimit().intValueExact() >= 100 && creditCard.getCreditLimit().intValueExact() <= 1000) {
            System.out.println("good");
        } else {
            creditCard.setCreditLimit(new BigDecimal("100"));
        }
        if (creditCard.getInterestRate().compareTo(new BigDecimal("0.2")) == -1 && creditCard.getInterestRate().compareTo(new BigDecimal("0.1")) == 1) {
            System.out.println("interest good");
        } else {
            creditCard.setInterestRate(new BigDecimal("0.2"));
        }
        creditCardRepository.save(creditCard);

        LOGGER.info("[END] Admin " + id + " creates new Credit Card account");
    }

    // --- CHECK BALANCE FROM ANY ACCOUNT ---

    /**
     * Allows admin to check the balance from any account.
     * @param adminId receives an integer with id from admin.
     * @param accountId receives an integer with id from account.
     * @return a BigDecimal with the balance from that account.
     */
    public BigDecimal checkAccountBalance(Integer adminId, Integer accountId) {
        LOGGER.info("[INIT] Admin " + adminId + " checks balance from account " + accountId);

        LOGGER.info("Confirm if account with id " + accountId + " exists");
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Account id not found"));

        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        adminIsLogged(admin);

        LOGGER.info("[END] Admin " + adminId + " checks balance from account " + accountId);
        if (account.getAccountType().equals(AccountType.CHECKING)) {
            return adminRepository.checkCheckingBalance(accountId);
        } else if (account.getAccountType().equals(AccountType.STUDENT_CHECKING)) {
            return adminRepository.checkStudentCheckingBalance(accountId);
        } else if (account.getAccountType().equals(AccountType.SAVINGS)) {
            savingsService.interestRateGain(accountId);
            return adminRepository.checkSavingsBalance(accountId);
        } else {
            creditCardService.interestDateGainMonthly(accountId);
            return adminRepository.checkCreditCardBalance(accountId);
        }
    }

    /**
     * Confirm that data involved in debit and credit amount are correct.
     * @param account receives an Account.
     * @param ownerId receives the id from primary or secondary owner from that specific account.
     * @param ownerName receives the name from primary or secondary owner from that specific account.
     * @param ownerAccountId receives the id from account.
     */
    public void confirmDataIsCorrect(Account account, Integer ownerId, String ownerName, Integer ownerAccountId){
        LOGGER.info("Confirm if owner with id " +  ownerId + " exists");
        AccountHolder accountHolder = accountsHolderRepository.findById(ownerId).orElseThrow(() -> new DataNotFoundException("Owner id not found"));

        LOGGER.info("Check if owner name and id provided are related with account to credit equals to the exact owner from the account " + ownerAccountId);
        boolean primaryOwner = false;
        boolean secondaryOwner = false;

        if(account.getPrimaryOwner().getUserId().equals(accountHolder.getUserId())){
            primaryOwner = true;
        } else if (accountHolder.getSecondaryAccounts().size()>=1){
            for(Account secondaryAccount: accountHolder.getSecondaryAccounts()){
                if(secondaryAccount.getAccountId().equals(account.getAccountId())){
                    secondaryOwner = true;
                }
            }
        }

        LOGGER.info("Check that names provided belong to the account");
        List<String> accountOwnersName = new ArrayList<String>();
        if(primaryOwner){
            accountOwnersName.add(account.getPrimaryOwner().getName());
        } else if (secondaryOwner){
            accountOwnersName.add(account.getSecondaryOwner().getName());
        }

        List<Integer> accountHolderAccountsList = new ArrayList<Integer>();

        if (accountOwnersName.contains(ownerName)) {
            for (Account accountId : accountHolder.getAccounts()) {
                accountHolderAccountsList.add(accountId.getAccountId());
            }

            for(Account accountId : accountHolder.getSecondaryAccounts()){
                accountHolderAccountsList.add(accountId.getAccountId());
            }
        }

        else {
            throw new DataNotFoundException("User name provided is not associated with that account");
        }

        if (!accountHolderAccountsList.contains(ownerAccountId)) {
            throw new DataNotFoundException("User doesn't have that account id");
        }
    }

    /**
     * Allows admin to debit amount in any account.
     * @param adminId receives an integer with id from admin.
     * @param changeBalance receives ChangeBalance that will allow the transaction.
     */
    @Transactional(propagation= Propagation.REQUIRED, noRollbackFor=Exception.class)
    // --- DEBIT THE BALANCE ---
    public void debitBalance(Integer adminId, ChangeBalance changeBalance) {
        LOGGER.info("[INIT] Admin " + adminId + " debits " + changeBalance.getAmount() + " amount in " + changeBalance.getAccountId() + " account");
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        adminIsLogged(admin);

        LOGGER.info("Confirm if account with id " + changeBalance.getAccountId() + " exists");
        Account account = accountRepository.findById(changeBalance.getAccountId()).orElseThrow(() -> new DataNotFoundException("Account id not found"));

        confirmDataIsCorrect(account, changeBalance.getOwnerId(), changeBalance.getAccountOwnerName(), changeBalance.getAccountId());

        if (account.getAccountType().equals(AccountType.CHECKING)) {
            LOGGER.info("Confirm if account with id " + changeBalance.getAccountId() + " is from type checking");
            Checking checking = checkingRepository.findById(changeBalance.getAccountId()).orElseThrow(() -> new DataNotFoundException("Checking account id not found"));
            checking.setBalance(new Money(checking.getBalance().decreaseAmount(changeBalance.getAmount())));
            if (checking.getBalance().getAmount().compareTo(checking.getMinimumBalance()) == -1) {
                if (checking.getLastPenalty() == 0) {
                    checking.setBalance(new Money(checking.getBalance().decreaseAmount(checking.getPenaltyFee())));
                    checking.setLastPenalty(1);
                }
            }
            LOGGER.info("The amount of " + changeBalance.getAmount() + " was debit in a checking account " + changeBalance.getAccountId());
            checkingRepository.save(checking);
        }

        else if (account.getAccountType().equals(AccountType.SAVINGS)) {
            LOGGER.info("Confirm if account with id " + changeBalance.getAccountId() + " is from type savings");
            Saving saving = savingsRepository.findById(changeBalance.getAccountId()).orElseThrow(() -> new DataNotFoundException("Savings account id not found"));
            saving.setBalance(new Money(saving.getBalance().decreaseAmount(changeBalance.getAmount())));
            if (saving.getBalance().getAmount().compareTo(saving.getMinimumBalance()) == -1) {
                if (saving.getLastPenalty() == 0) {
                    saving.setBalance(new Money(saving.getBalance().decreaseAmount(saving.getPenaltyFee())));
                    saving.setLastPenalty(1);
                }
            }
            LOGGER.info("The amount of " + changeBalance.getAmount() + " was debit in a savings account " + changeBalance.getAccountId());
            savingsRepository.save(saving);
        }

        else if (account.getAccountType().equals(AccountType.CREDIT_CARD)) {
            LOGGER.info("Confirm if account with id " + changeBalance.getAccountId() + " is from type credit card");
            CreditCard creditCard = creditCardRepository.findById(changeBalance.getAccountId()).orElseThrow(() -> new DataNotFoundException("Credit card account id not found"));
            creditCard.setBalance(new Money(creditCard.getBalance().decreaseAmount(changeBalance.getAmount())));

            LOGGER.info("The amount of " + changeBalance.getAmount() + " was debit in a credit card account " + changeBalance.getAccountId());
            creditCardRepository.save(creditCard);
        }

        else if (account.getAccountType().equals(AccountType.STUDENT_CHECKING)) {
            LOGGER.info("Confirm if account with id " + changeBalance.getAccountId() + " is from type student checking");
            StudentChecking studentChecking = studentCheckingRepository.findById(changeBalance.getAccountId()).orElseThrow(() -> new DataNotFoundException("Student checking account id not found"));
            studentChecking.setBalance(new Money(studentChecking.getBalance().decreaseAmount(changeBalance.getAmount())));

            LOGGER.info("The amount of " + changeBalance.getAmount() + " was debit in a student checking account " + changeBalance.getAccountId());
            studentCheckingRepository.save(studentChecking);
        }

        LOGGER.info("[END] Admin " + adminId + " debits " + changeBalance.getAmount() + " amount in " + changeBalance.getAccountId() + " account");
    }

    /**
     * Allows admin to credit amount in any account.
     * @param adminId receives an integer with id from admin.
     * @param changeBalance receives ChangeBalance that will allow the transaction.
     */

    @Transactional(propagation= Propagation.REQUIRED, noRollbackFor=Exception.class)
    // --- CREDIT THE BALANCE ---
    public void creditBalance(Integer adminId, ChangeBalance changeBalance) {
        LOGGER.info("[INIT] Admin " + adminId + " credits " + changeBalance.getAmount() + " amount in " + changeBalance.getAccountId() + " account");

        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        adminIsLogged(admin);

        LOGGER.info("Confirm if account with id " + changeBalance.getAccountId() + " exists");
        Account account = accountRepository.findById(changeBalance.getAccountId()).orElseThrow(() -> new DataNotFoundException("Account id not found"));

        confirmDataIsCorrect(account, changeBalance.getOwnerId(), changeBalance.getAccountOwnerName(), changeBalance.getAccountId());

        if (account.getAccountType().equals(AccountType.CHECKING)) {
            LOGGER.info("Confirm if account with id " + changeBalance.getAccountId() + " is from type checking");
            Checking checking = checkingRepository.findById(changeBalance.getAccountId()).orElseThrow(() -> new DataNotFoundException("Checking account id not found"));
            checking.setBalance(new Money(checking.getBalance().increaseAmount(changeBalance.getAmount())));
            if (checking.getLastPenalty() == 1 && checking.getBalance().getAmount().compareTo(checking.getMinimumBalance()) == 1) {
                checking.setLastPenalty(0);
            }

            LOGGER.info("The amount of " + changeBalance.getAmount() + " was credit in a checking account " + changeBalance.getAccountId());
            checkingRepository.save(checking);
        }

        else if (account.getAccountType().equals(AccountType.SAVINGS)) {
            LOGGER.info("Confirm if account with id " + changeBalance.getAccountId() + " is from type savings");
            Saving saving = savingsRepository.findById(changeBalance.getAccountId()).orElseThrow(() -> new DataNotFoundException("Savings account id not found"));
            saving.setBalance(new Money(saving.getBalance().increaseAmount(changeBalance.getAmount())));
            if (saving.getLastPenalty() == 1 && saving.getBalance().getAmount().compareTo(saving.getMinimumBalance()) == 1) {
                saving.setLastPenalty(0);
            }

            LOGGER.info("The amount of " + changeBalance.getAmount() + " was credit in a  savings account " + changeBalance.getAccountId());
            savingsRepository.save(saving);
        }

        else if (account.getAccountType().equals(AccountType.CREDIT_CARD)) {
            LOGGER.info("Confirm if account with id " + changeBalance.getAccountId() + " is from type credit card");
            CreditCard creditCard = creditCardRepository.findById(changeBalance.getAccountId()).orElseThrow(() -> new DataNotFoundException("Credit card account id not found"));
            creditCard.setBalance(new Money(creditCard.getBalance().increaseAmount(changeBalance.getAmount())));

            LOGGER.info("The amount of " + changeBalance.getAmount() + " was credit in a credit card account " + changeBalance.getAccountId());
            creditCardRepository.save(creditCard);
        }

        else if (account.getAccountType().equals(AccountType.STUDENT_CHECKING)) {
            LOGGER.info("Confirm if account with id " + changeBalance.getAccountId() + " is from type student checking");
            StudentChecking studentChecking = studentCheckingRepository.findById(changeBalance.getAccountId()).orElseThrow(() -> new DataNotFoundException("Student checking account id not found"));
            studentChecking.setBalance(new Money(studentChecking.getBalance().increaseAmount(changeBalance.getAmount())));

            LOGGER.info("The amount of " + changeBalance.getAmount() + " was credit in a credit card account " + changeBalance.getAccountId());
            studentCheckingRepository.save(studentChecking);
        }

        LOGGER.info("[END] Admin " + adminId + " credits " + changeBalance.getAmount() + " amount in " + changeBalance.getAccountId() + " account");
    }


    // --- CREATE THIRD PARTY ---

    /**
     * Allows admin users to create a new ThirdParty user.
     * @param adminId receives an integer with id from admin.
     * @param createThirdParty receives a createThirdParty with allow information needed to create that user.
     */
    public void createNewThirdParty(Integer adminId, CreateThirdParty createThirdParty) {
        LOGGER.info("[INIT] Admin " + adminId + " creates New Third Party");

        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        adminIsLogged(admin);

        String hashedKey = UUID.randomUUID().toString();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(hashedKey, createThirdParty.getHashedName());
        ThirdParty thirdParty = new ThirdParty(createThirdParty.getName(), createThirdParty.getPassword());
        thirdParty.setAccountDetails(map);

        thirdPartyRepository.save(thirdParty);

        LOGGER.info("[END] Admin " + adminId + " creates New Third Party");

    }

    // --- UNFREEZE ACCOUNTS ---

    /**
     * Allow admins to unfreeze accounts with status frozen.
     * @param adminId receives an integer with id from admin.
     * @param accountId receives an integer with id from account.
     */
    public void unfreezeAccount(Integer adminId, Integer accountId) {
        LOGGER.info("[INIT] Admin " + adminId + " unfreeze account " + accountId);
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        adminIsLogged(admin);
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Account id not found"));
        if (account.getAccountType().equals(AccountType.SAVINGS)) {
            Saving saving = savingsRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Saving id not found"));
            LOGGER.info("Check status from savings account " + saving.getAccountId());
            if(saving.getStatus().equals(Status.ACTIVE)){
                throw new DataNotFoundException("Savings account Status is already Active");
            }
            saving.setStatus(Status.ACTIVE);
            savingsRepository.save(saving);
        }

        else if (account.getAccountType().equals(AccountType.CHECKING)) {
            Checking checking = checkingRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Checking id not found"));
            LOGGER.info("Check status from checking account " + checking.getAccountId());
            if(checking.getStatus().equals(Status.ACTIVE)){
                throw new DataNotFoundException("Checking account Status is already Active");
            }
            checking.setStatus(Status.ACTIVE);
            checkingRepository.save(checking);
        }

        else if (account.getAccountType().equals(AccountType.STUDENT_CHECKING)) {
            StudentChecking studentChecking = studentCheckingRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Student checking id not found"));
            LOGGER.info("Check status from student checking account " + studentChecking.getAccountId());
            if(studentChecking.getStatus().equals(Status.ACTIVE)){
                throw new DataNotFoundException("Student checking account Status is already Active");
            }
            studentChecking.setStatus(Status.ACTIVE);
            studentCheckingRepository.save(studentChecking);
        }
        LOGGER.info("[END] Admin " + adminId + " unfreeze account " + accountId);
    }

    /**
     * Allow admins to delete any type of account.
     * @param accountId receives an integer with id from account.
     * @param adminId receives an integer with id from admin.
     */
    public void deleteAccount(Integer accountId, Integer adminId){
        adminRepository.findById(adminId).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Account id not found"));
        accountRepository.delete(account);
    }
}
