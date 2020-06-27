package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.dto.Transference;
import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.*;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import com.ironhack.MidtermProject.model.entities.users.Admin;
import com.ironhack.MidtermProject.repository.accounts.*;
import com.ironhack.MidtermProject.repository.users.AccountHolderRepository;
import com.ironhack.MidtermProject.repository.users.AdminRepository;
import com.ironhack.MidtermProject.service.accounts.CreditCardService;
import com.ironhack.MidtermProject.service.accounts.SavingsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountHolderService {

    @Autowired
    private AccountHolderRepository accountsHolderRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private SavingsService savingsService;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private AdminService adminService;

    private static final Logger LOGGER = LogManager.getLogger(AccountHolderService.class);

    /**
     * Find all AccountHolder users created.
     * @return a list of account holder users.
     */
    public List<AccountHolder> findAll() {
        LOGGER.info("Get all Account Holder users");
        return accountsHolderRepository.findAll();
    }

    /**
     * Find AccountHolder by id.
     * @param id receives an integer id of user.
     * @return an AccountHolder user corresponding to that id.
     */
    public AccountHolder findById(Integer id) {
        LOGGER.info("Get Account Holder with id " + id);
        return accountsHolderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Account Holder id not found"));
    }

    /**
     * Allows admins to create new account holders.
     * @param adminId receives an integer with id from admin.
     * @param accountHolder receives an AccountHolder with personal information needed to create that user.
     */
    public void createAccountHolder(Integer adminId, AccountHolder accountHolder) {
        LOGGER.info("Create a new Account Holder");
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new DataNotFoundException("Admin id not found"));
        adminService.adminIsLogged(admin);
        accountsHolderRepository.save(accountHolder);
    }

    /**
     * Allows AccountHolder users to login into their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return an AccountHolder logged in.
     */
    public AccountHolder loginAccountHolder(LoginAccount loginAccount) {
        LOGGER.info("[INIT] Login AccountHolder " + loginAccount.getId());
        AccountHolder accountHolder = accountsHolderRepository.findById(loginAccount.getId()).orElseThrow(() -> new DataNotFoundException("Account Holder id not found"));

        LOGGER.info("Check if user " + loginAccount.getId() + " is already logged in");
        if(accountHolder.isLogged()){
            throw new DataNotFoundException("User is already logged in");
        }

        LOGGER.info("Check if password provided belongs to respective account holder");
        if (!accountHolder.getPassword().equals(loginAccount.getPassword())) {
            throw new DataNotFoundException("Password incorrect, try again");
        }

        accountHolder.login();
        accountsHolderRepository.save(accountHolder);
        LOGGER.info("[END] Login AccountHolder " + loginAccount.getId());
        return accountHolder;
    }

    /**
     * Allows AccountHolder users to logout from their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return an AccountHolder logged out.
     */
    public AccountHolder logOutAccountHolder(LoginAccount loginAccount) {
        LOGGER.info("[INIT] Logout AccountHolder " + loginAccount.getId());
        AccountHolder accountHolder = accountsHolderRepository.findById(loginAccount.getId()).orElseThrow(() -> new DataNotFoundException("Account Holder id not found"));

        LOGGER.info("Check if user " + loginAccount.getId() + " is already logged out");
        if(!accountHolder.isLogged()){
            throw new DataNotFoundException("User is already logged out");
        }

        LOGGER.info("Check if password provided belongs to respective account holder");
        if (!accountHolder.getPassword().equals(loginAccount.getPassword())) {
            throw new DataNotFoundException("Password incorrect, try again");
        }

        accountHolder.logOut();
        accountsHolderRepository.save(accountHolder);
        LOGGER.info("[END] Logout AccountHolder " + loginAccount.getId());
        return accountHolder;
    }

    /**
     * Allows AccountHolder users to check balance from their accounts.
     * @param accountId receives an integer with id from account.
     * @param ownerId receives an integer with id from account holder.
     * @return a BigDecimal with balance from account.
     */
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public BigDecimal checkAccountBalance(Integer accountId, Integer ownerId) {
        LOGGER.info("Account Holder " + ownerId + " checks balance from account " + accountId);
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Account id not found"));

        AccountHolder accountHolder = accountsHolderRepository.findById(ownerId).orElseThrow(() -> new DataNotFoundException("User id not found"));

        if (accountHolder.isLogged() == false) {
            throw new DataNotFoundException("User must be logged in");
        }

        List<Integer> totalAccounts = new ArrayList<Integer>();
        for (Account a : accountHolder.getAccounts()) {
            totalAccounts.add(a.getAccountId());
        }
        for (Account a1 : accountHolder.getSecondaryAccounts()) {
            totalAccounts.add(a1.getAccountId());
        }

        if (!totalAccounts.contains(accountId)) {
            throw new DataNotFoundException("User doesn't have access to this account.");
        }

        if (account.getAccountType().equals(AccountType.CHECKING)) {
            LOGGER.info("Account Holder check balance from own Checking account");
            return accountsHolderRepository.checkCheckingBalance(accountId);
        } else if (account.getAccountType().equals(AccountType.STUDENT_CHECKING)) {
            LOGGER.info("Account Holder check balance from own Student Checking account");
            return accountsHolderRepository.checkStudentCheckingBalance(accountId);
        } else if (account.getAccountType().equals(AccountType.SAVINGS)) {
            savingsService.interestRateGain(accountId);
            LOGGER.info("Account Holder check balance from own Savings account");
            return accountsHolderRepository.checkSavingsBalance(accountId);
        } else {
            creditCardService.interestDateGainMonthly(accountId);
            LOGGER.info("Account Holder check balance from own Credit Card account");
            return accountsHolderRepository.checkCreditCardBalance(accountId);
        }
    }

    /**
     * Allows account holder users to transfer money from their accounts to others.
     * @param transference receives a Transference with all information needed to make that transaction.
     */
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public void transferAmount(Transference transference) {
        LOGGER.info("[INIT] Account Holder " + transference.getUserId() + " makes a transference of " + transference.getAmountToTransfer());

        LOGGER.info("Check that sender user " + transference.getUserId() + " exist and it's logged in");
        AccountHolder accountHolder = accountsHolderRepository.findById(transference.getUserId()).orElseThrow(() -> new DataNotFoundException("Sender id not found"));
        if (accountHolder.isLogged() == false) {
            throw new DataNotFoundException("User must be logged in to make a transference");
        }

        LOGGER.info("Check that sender account " + transference.getSenderAccountId() + " and receiver account " + transference.getReceiverAccountId() + " exist");
        Account sender = accountRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender account id not found"));
        Account receiver = accountRepository.findById(transference.getReceiverAccountId()).orElseThrow(() -> new DataNotFoundException("Receiver account id not found"));

        adminService.confirmDataIsCorrect(sender, transference.getUserId(), transference.getSenderName(), transference.getSenderAccountId());

        LOGGER.info("Check that names provided belong to the receiver account");
        List<String> receiverPrimaryAndSecondaryOwner = new ArrayList<String>();
        receiverPrimaryAndSecondaryOwner.add(receiver.getPrimaryOwner().getName());
        if (receiver.getSecondaryOwner() != null) {
            receiverPrimaryAndSecondaryOwner.add(receiver.getSecondaryOwner().getName());
        }

        if (!receiverPrimaryAndSecondaryOwner.contains(transference.getReceiverName())) {
            throw new DataNotFoundException("Receiver user is not associated with that receiver account");
        }

        if (sender.getBalance().getAmount().compareTo(transference.getAmountToTransfer()) == -1) {
            throw new DataNotFoundException("Transference is not possible, sender's balance is less than amount to transfer");
        }

        if (sender.getAccountType().equals(AccountType.SAVINGS)) {
            savingsService.interestRateGain(transference.getSenderAccountId());
            transferAmountSenderSaving(transference);
        } else if (sender.getAccountType().equals(AccountType.CHECKING)) {
            transferAmountSenderChecking(transference);
        } else if (sender.getAccountType().equals(AccountType.CREDIT_CARD)) {
            creditCardService.interestDateGainMonthly(transference.getSenderAccountId());
            transferAmountSenderCreditCard(transference);
        } else if (sender.getAccountType().equals(AccountType.STUDENT_CHECKING)) {
            transferAmountSenderStudentChecking(transference);
        }

        if (receiver.getAccountType().equals(AccountType.CHECKING)) {
            LOGGER.info("Receiver's account " + transference.getReceiverAccountId() + " is from type Checking");
            Checking receiverChecking = checkingRepository.findById(transference.getReceiverAccountId()).orElseThrow(() -> new DataNotFoundException("Checking account id not found"));
            receiverChecking.setBalance(new Money(receiverChecking.getBalance().increaseAmount(transference.getAmountToTransfer())));
            if (receiverChecking.getLastPenalty() == 1 && receiverChecking.getBalance().getAmount().compareTo(receiverChecking.getMinimumBalance()) == 1) {
                receiverChecking.setLastPenalty(0);
            }
            checkingRepository.save(receiverChecking);
        } else if (receiver.getAccountType().equals(AccountType.SAVINGS)) {
            LOGGER.info("Receiver's account " + transference.getReceiverAccountId() + " is from type Savings");
            Saving receiverSaving = savingRepository.findById(transference.getReceiverAccountId()).orElseThrow(() -> new DataNotFoundException("Saving account id not found"));
            receiverSaving.setBalance(new Money(receiverSaving.getBalance().increaseAmount(transference.getAmountToTransfer())));
            if (receiverSaving.getLastPenalty() == 1 && receiverSaving.getBalance().getAmount().compareTo(receiverSaving.getMinimumBalance()) == 1) {
                receiverSaving.setLastPenalty(0);
            }
            savingRepository.save(receiverSaving);
        } else {
            LOGGER.info("Receiver's account " + transference.getReceiverAccountId());
            receiver.setBalance(new Money(receiver.getBalance().increaseAmount(transference.getAmountToTransfer())));
            accountRepository.save(receiver);
        }
        LOGGER.info("[END] Account Holder " + transference.getUserId() + " makes a transference of " + transference.getAmountToTransfer());
    }

    /**
     * Detects fraud detections if transactions are done within certain period of time.
     * @param studentCheckingSender receives a studend checking account.
     */
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public void fraudDetectionStudentChecking(StudentChecking studentCheckingSender) {
        LOGGER.info("[INIT] Fraud detection of student checking " + studentCheckingSender.getAccountId());
        if (studentCheckingSender.getTransactionsMade().size() >= 3) {
            LOGGER.info("Check if more than 2 transactions occurred within a second period");
            long seconds = Duration.between(studentCheckingSender.getTransactionsMade().get(studentCheckingSender.getTransactionsMade().size() - 3), studentCheckingSender.getTransactionsMade().get(studentCheckingSender.getTransactionsMade().size() - 1)).toSeconds();
            if (seconds < 1) {
                studentCheckingSender.setStatus(Status.FROZEN);
                studentCheckingSender.getTransactionsMade().remove(studentCheckingSender.getTransactionsMade().size() - 1);
                studentCheckingRepository.save(studentCheckingSender);
                LOGGER.info("More than 2 transactions occurred within a second period, account " + studentCheckingSender.getAccountId() + " is now frozen");
                throw new DataNotFoundException("Student checking account status is frozen");
            }
        }

        if (studentCheckingSender.getTransactionsMade().size() >= 2) {
            LOGGER.info("Check if daily transactions exceeded 150% of the maximum ever done in 24h");
            List<LocalDateTime> lastDayTransactions = new ArrayList<LocalDateTime>();
            List<LocalDateTime> todayTransactions = new ArrayList<LocalDateTime>();
            for (LocalDateTime dateTime : studentCheckingSender.getTransactionsMade()) {
                if (Duration.between(dateTime, LocalDateTime.now()).toHours() > 24 && Duration.between(dateTime, LocalDateTime.now()).toHours() <= 48) {
                    lastDayTransactions.add(dateTime);
                } else if (Duration.between(dateTime, LocalDateTime.now()).toHours() <= 24) {
                    todayTransactions.add(dateTime);
                }
            }

            if (lastDayTransactions.size() > studentCheckingSender.getMaxTransferencesInADay()) {
                studentCheckingSender.setMaxTransferencesInADay(lastDayTransactions.size());
                studentCheckingRepository.save(studentCheckingSender);
            }

            if (studentCheckingSender.getMaxTransferencesInADay() < todayTransactions.size()) {
                double division = (double) todayTransactions.size() / studentCheckingSender.getMaxTransferencesInADay() * 100;
                if (division > 150.0f) {
                    studentCheckingSender.setStatus(Status.FROZEN);
                    studentCheckingSender.setMaxTransferencesInADay(todayTransactions.size() - 1);
                    studentCheckingSender.getTransactionsMade().remove(studentCheckingSender.getTransactionsMade().size() - 1);
                    studentCheckingRepository.save(studentCheckingSender);
                    LOGGER.info("Daily transactions exceeded 150% of the maximum ever done in 24h " + studentCheckingSender.getAccountId() + " is now frozen");
                    throw new DataNotFoundException("Checking account status is frozen");
                }
            }
        }
        LOGGER.info("[END] Fraud detection of student checking " + studentCheckingSender.getAccountId());
    }

    /**
     * Detects fraud detections if transactions are done within certain period of time.
     * @param checkingSender receives a checking account.
     */
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public void fraudDetectionChecking(Checking checkingSender) {
        LOGGER.info("[INIT] Fraud detection of checking " + checkingSender.getAccountId());
        if (checkingSender.getTransactionsMade().size() >= 3) {
            LOGGER.info("Check if more than 2 transactions occurred within a second period");
            long seconds = Duration.between(checkingSender.getTransactionsMade().get(checkingSender.getTransactionsMade().size() - 3), checkingSender.getTransactionsMade().get(checkingSender.getTransactionsMade().size() - 1)).toSeconds();
            if (seconds < 1) {
                checkingSender.setStatus(Status.FROZEN);
                checkingSender.getTransactionsMade().remove(checkingSender.getTransactionsMade().size() - 1);
                checkingRepository.save(checkingSender);
                LOGGER.info("More than 2 transactions occurred within a second period, account " + checkingSender.getAccountId() + " is now frozen");
                throw new DataNotFoundException("Checking account status is frozen");
            }
        }

        if (checkingSender.getTransactionsMade().size() >= 2) {
            LOGGER.info("Check if daily transactions exceeded 150% of the maximum ever done in 24h");
            List<LocalDateTime> lastDayTransactions = new ArrayList<LocalDateTime>();
            List<LocalDateTime> todayTransactions = new ArrayList<LocalDateTime>();
            for (LocalDateTime dateTime : checkingSender.getTransactionsMade()) {
                if (Duration.between(dateTime, LocalDateTime.now()).toHours() > 24 && Duration.between(dateTime, LocalDateTime.now()).toHours() <= 48) {
                    lastDayTransactions.add(dateTime);
                } else if (Duration.between(dateTime, LocalDateTime.now()).toHours() <= 24) {
                    todayTransactions.add(dateTime);
                }
            }

            if (lastDayTransactions.size() > checkingSender.getMaxTransferencesInADay()) {
                checkingSender.setMaxTransferencesInADay(lastDayTransactions.size());
                checkingRepository.save(checkingSender);
            }

            if (checkingSender.getMaxTransferencesInADay() < todayTransactions.size()) {
                double division = (double) todayTransactions.size() / checkingSender.getMaxTransferencesInADay() * 100;
                if (division > 150.0f) {
                    checkingSender.setStatus(Status.FROZEN);
                    checkingSender.setMaxTransferencesInADay(todayTransactions.size() - 1);
                    checkingSender.getTransactionsMade().remove(checkingSender.getTransactionsMade().size() - 1);
                    checkingRepository.save(checkingSender);
                    LOGGER.info("Daily transactions exceeded 150% of the maximum ever done in 24h " + checkingSender.getAccountId() + " is now frozen");
                    throw new DataNotFoundException("Checking account status is frozen");
                }
            }
        }
        LOGGER.info("[END] Fraud detection of checking " + checkingSender.getAccountId());
    }

    /**
     * Detects fraud detections if transactions are done within certain period of time.
     * @param savingSender receives a savings account.
     */
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public void fraudDetectionSavings(Saving savingSender) {
        LOGGER.info("[INIT] Fraud detection of savings " + savingSender.getAccountId());
        if (savingSender.getTransactionsMade().size() >= 3) {
            LOGGER.info("Check if more than 2 transactions occurred within a second period");
            long seconds = Duration.between(savingSender.getTransactionsMade().get(savingSender.getTransactionsMade().size() - 3), savingSender.getTransactionsMade().get(savingSender.getTransactionsMade().size() - 1)).toSeconds();
            if (seconds < 1) {
                savingSender.setStatus(Status.FROZEN);
                savingSender.getTransactionsMade().remove(savingSender.getTransactionsMade().size() - 1);
                savingRepository.save(savingSender);
                LOGGER.info("More than 2 transactions occurred within a second period, account " + savingSender.getAccountId() + " is now frozen");
                throw new DataNotFoundException("Saving account status is frozen");
            }
        }

        if (savingSender.getTransactionsMade().size() >= 2) {
            LOGGER.info("Check if daily transactions exceeded 150% of the maximum ever done in 24h");
            List<LocalDateTime> lastDayTransactions = new ArrayList<LocalDateTime>();
            List<LocalDateTime> todayTransactions = new ArrayList<LocalDateTime>();
            for (LocalDateTime dateTime : savingSender.getTransactionsMade()) {
                if (Duration.between(dateTime, LocalDateTime.now()).toHours() > 24 && Duration.between(dateTime, LocalDateTime.now()).toHours() <= 48) {
                    lastDayTransactions.add(dateTime);
                } else if (Duration.between(dateTime, LocalDateTime.now()).toHours() <= 24) {
                    todayTransactions.add(dateTime);
                }
            }

            if (lastDayTransactions.size() > savingSender.getMaxTransferencesInADay()) {
                savingSender.setMaxTransferencesInADay(lastDayTransactions.size());
                savingRepository.save(savingSender);
            }

            if (savingSender.getMaxTransferencesInADay() < todayTransactions.size()) {
                double division = (double) todayTransactions.size() / savingSender.getMaxTransferencesInADay() * 100;
                if (division > 150.0f) {
                    savingSender.setStatus(Status.FROZEN);
                    savingSender.setMaxTransferencesInADay(todayTransactions.size() - 1);
                    savingSender.getTransactionsMade().remove(savingSender.getTransactionsMade().size() - 1);
                    savingRepository.save(savingSender);
                    LOGGER.info("Daily transactions exceeded 150% of the maximum ever done in 24h " + savingSender.getAccountId() + " is now frozen");
                    throw new DataNotFoundException("Saving account status is frozen");
                }
            }
        }
        LOGGER.info("[END] Fraud detection of savings " + savingSender.getAccountId());
    }

    /**
     * Allows transfers having sender account type as savings.
     * @param transference receives a Transference with all information needed to make that transaction.
     */
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public void transferAmountSenderSaving(Transference transference) {
        LOGGER.info("Sender's account is from type Savings");

        Saving savingSender = savingRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender savings account id not found"));
        savingSender.getTransactionsMade().add(LocalDateTime.now());

        fraudDetectionSavings(savingSender);

        LOGGER.info("Transference is on it's way");
        savingSender.setBalance(new Money(savingSender.getBalance().decreaseAmount(transference.getAmountToTransfer())));
        if (savingSender.getBalance().getAmount().compareTo(savingSender.getMinimumBalance()) == -1) {
            if (savingSender.getLastPenalty() == 0) {
                LOGGER.info("Penalty fee applied to Sender's account " + savingSender.getAccountId() + " which is from type Savings");
                savingSender.setBalance(new Money(savingSender.getBalance().decreaseAmount(savingSender.getPenaltyFee())));
                savingSender.setLastPenalty(1);
            }
        }
        savingRepository.save(savingSender);

        LOGGER.info("The transference from " + transference.getSenderAccountId() + " to " + transference.getReceiverAccountId() + " done successfully");
    }

    /**
     * Allows transfers having sender account type as checking.
     * @param transference receives a Transference with all information needed to make that transaction.
     */
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public void transferAmountSenderChecking(Transference transference) {
        LOGGER.info("Sender's account is from type Checking");
        Checking checkingSender = checkingRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender checking account id not found"));
        checkingSender.getTransactionsMade().add(LocalDateTime.now());

        fraudDetectionChecking(checkingSender);

        LOGGER.info("Transference is on it's way");
        checkingSender.setBalance(new Money(checkingSender.getBalance().decreaseAmount(transference.getAmountToTransfer())));
        if (checkingSender.getBalance().getAmount().compareTo(checkingSender.getMinimumBalance()) == -1) {
            if (checkingSender.getLastPenalty() == 0) {
                LOGGER.info("Penalty fee applied to Sender's account " + checkingSender.getAccountId() + " which is from type Checking");
                checkingSender.setBalance(new Money(checkingSender.getBalance().decreaseAmount(checkingSender.getPenaltyFee())));
                checkingSender.setLastPenalty(1);
            }
        }
        checkingRepository.save(checkingSender);

        LOGGER.info("The transference from " + transference.getSenderAccountId() + " to " + transference.getReceiverAccountId() + " done successfully");
    }

    /**
     * Allows transfers having sender account type as credit card.
     * @param transference receives a Transference with all information needed to make that transaction.
     */
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public void transferAmountSenderCreditCard(Transference transference) {
        LOGGER.info("Sender's account is from type Credit Card");
        CreditCard creditCardSender = creditCardRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender credit card account id not found"));

        LOGGER.info("Transference is on it's way");
        creditCardSender.setBalance(new Money(creditCardSender.getBalance().decreaseAmount(transference.getAmountToTransfer())));

        creditCardRepository.save(creditCardSender);
        LOGGER.info("The transference from " + transference.getSenderAccountId() + " to " + transference.getReceiverAccountId() + " done successfully");
    }

    /**
     * Allows transfers having sender account type as student checking.
     * @param transference receives a Transference with all information needed to make that transaction.
     */
    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public void transferAmountSenderStudentChecking(Transference transference) {
        LOGGER.info("Sender's account is from type Student Checking");
        StudentChecking studentCheckingSender = studentCheckingRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender student checking account id not found"));
        studentCheckingSender.getTransactionsMade().add(LocalDateTime.now());

        fraudDetectionStudentChecking(studentCheckingSender);

        LOGGER.info("Transference is on it's way");
        studentCheckingSender.setBalance(new Money(studentCheckingSender.getBalance().decreaseAmount(transference.getAmountToTransfer())));

        studentCheckingRepository.save(studentCheckingSender);
        LOGGER.info("The transference from " + transference.getSenderAccountId() + " to " + transference.getReceiverAccountId() + " done successfully");
    }
}

