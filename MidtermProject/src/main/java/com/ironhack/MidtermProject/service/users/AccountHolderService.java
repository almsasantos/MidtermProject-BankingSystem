package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.dto.Transference;
import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.enums.Status;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.*;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import com.ironhack.MidtermProject.repository.accounts.*;
import com.ironhack.MidtermProject.repository.users.AccountHolderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountHolderService {

    @Autowired
    private AccountHolderRepository accountsHolderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SavingRepository savingRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    private static final Logger LOGGER = LogManager.getLogger(ThirdPartyService.class);

    public List<AccountHolder> findAll() {
        LOGGER.info("Get all Account Holder users");
        return accountsHolderRepository.findAll();
    }

    public AccountHolder findById(Integer id) {
        LOGGER.info("Get Account Holder by id");
        return accountsHolderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Account Holder id not found"));
    }

    public void createAccountHolder(AccountHolder accountHolder) {
        LOGGER.info("Create a new Account Holder");
        accountsHolderRepository.save(accountHolder);
    }

    public AccountHolder loginAccountHolder(LoginAccount loginAccount) {
        AccountHolder accountHolder = accountsHolderRepository.findById(loginAccount.getId()).orElseThrow(() -> new DataNotFoundException("Account Holder id not found"));
        if(!accountHolder.getPassword().equals(loginAccount.getPassword())){
            throw new DataNotFoundException("Password incorrect, try again");
        }
        accountHolder.login();
        accountsHolderRepository.save(accountHolder);
        return accountHolder;
    }

    public AccountHolder logOutAccountHolder(LoginAccount loginAccount) {
        AccountHolder accountHolder = accountsHolderRepository.findById(loginAccount.getId()).orElseThrow(() -> new DataNotFoundException("Account Holder id not found"));
        if(!accountHolder.getPassword().equals(loginAccount.getPassword())){
            throw new DataNotFoundException("Password incorrect, try again");
        }
        accountHolder.logOut();
        accountsHolderRepository.save(accountHolder);
        return accountHolder;
    }

    public BigDecimal checkAccountBalance(Integer accountId, Integer primaryOwnerId) {
        LOGGER.info("Account Holder check own account's balance");
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Account id not found"));
        if (account.getPrimaryOwner().getUserId() == primaryOwnerId) {
            if (account.getPrimaryOwner().isLogged() == false) {
                throw new DataNotFoundException("User must be logged in");
            }
            if (account.getAccountType().equals(AccountType.CHECKING)) {
                LOGGER.info("Account Holder check balance from own Checking account");
                return accountsHolderRepository.checkCheckingBalance(accountId);
            } else if (account.getAccountType().equals(AccountType.STUDENT_CHECKING)) {
                LOGGER.info("Account Holder check balance from own Student Checking account");
                return accountsHolderRepository.checkStudentCheckingBalance(accountId);
            } else if (account.getAccountType().equals(AccountType.SAVINGS)) {
                LOGGER.info("Account Holder check balance from own Savings account");
                return accountsHolderRepository.checkSavingsBalance(accountId);
            } else {
                LOGGER.info("Account Holder check balance from own Credit Card account");
                return accountsHolderRepository.checkCreditCardBalance(accountId);
            }
        } else {
            throw new DataNotFoundException("User doesn't have access to this account.");
        }
    }

    public void fraudDetectionSavings(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Account id not found"));
        List<List<LocalDateTime>> listOfList = new ArrayList<List<LocalDateTime>>();
        List<LocalDateTime> todayTransactions = new ArrayList<LocalDateTime>();
        List<Integer> numberOfTransactions = new ArrayList<Integer>();

        if (account.getAccountType().equals(AccountType.SAVINGS)) {
            Saving saving = savingRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Saving account id not found"));
            if (saving.getTransactionsMade().size() >= 2) {
                long seconds = Duration.between(saving.getTransactionsMade().get(saving.getTransactionsMade().size() - 2), saving.getTransactionsMade().get(saving.getTransactionsMade().size() - 1)).toSeconds();
                if (seconds < 1) {
                    saving.setStatus(Status.FROZEN);
                    savingRepository.save(saving);
                    throw new DataNotFoundException("Saving account status is frozen");
                }

                List<LocalDateTime> lastDayTransactions = new ArrayList<LocalDateTime>();
                for (LocalDateTime dateTime : saving.getTransactionsMade()) {
                    if (Duration.between(dateTime, LocalDateTime.now()).toHours() <= 24) {
                        lastDayTransactions.add(dateTime);
                    }
                }

                if (saving.getMaxTransferencesInADay() < lastDayTransactions.size()) {
                    if (lastDayTransactions.size() / saving.getMaxTransferencesInADay() * 100 > 150) {
                        saving.setStatus(Status.FROZEN);
                        savingRepository.save(saving);
                    }
                    saving.setMaxTransferencesInADay(lastDayTransactions.size());
                }
            }
        }
    }

    public void fraudDetectionChecking(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Account id not found"));
        if (account.getAccountType().equals(AccountType.CHECKING)) {
            Checking checking = checkingRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Checking account id not found"));
            if (checking.getTransactionsMade().size() >= 2) {
                long seconds = Duration.between(checking.getTransactionsMade().get(checking.getTransactionsMade().size() - 2), checking.getTransactionsMade().get(checking.getTransactionsMade().size() - 1)).toSeconds();
                if (seconds < 1) {
                    checking.setStatus(Status.FROZEN);
                    checkingRepository.save(checking);
                    throw new DataNotFoundException("Checking account status is frozen");
                }

                List<LocalDateTime> lastDayTransactions = new ArrayList<LocalDateTime>();
                for (LocalDateTime dateTime : checking.getTransactionsMade()) {
                    if (Duration.between(dateTime, LocalDateTime.now()).toHours() <= 24) {
                        lastDayTransactions.add(dateTime);
                    }
                }

                if (checking.getMaxTransferencesInADay() < lastDayTransactions.size()) {
                    if (lastDayTransactions.size() / checking.getMaxTransferencesInADay() * 100 > 150) {
                        checking.setStatus(Status.FROZEN);
                        checkingRepository.save(checking);
                    }
                    checking.setMaxTransferencesInADay(lastDayTransactions.size());
                }
            }
        }
    }

    public void fraudDetectionStudentChecking(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Account id not found"));
        if (account.getAccountType().equals(AccountType.STUDENT_CHECKING)) {
            StudentChecking studentChecking = studentCheckingRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Student checking account id not found"));
            if (studentChecking.getTransactionsMade().size() >= 2) {
                long seconds = Duration.between(studentChecking.getTransactionsMade().get(studentChecking.getTransactionsMade().size() - 2), studentChecking.getTransactionsMade().get(studentChecking.getTransactionsMade().size() - 1)).toSeconds();
                if (seconds < 1) {
                    studentChecking.setStatus(Status.FROZEN);
                    studentCheckingRepository.save(studentChecking);
                    throw new DataNotFoundException("Student checking account status is frozen");
                }
            }

            List<LocalDateTime> lastDayTransactions = new ArrayList<LocalDateTime>();
            for (LocalDateTime dateTime : studentChecking.getTransactionsMade()) {
                if (Duration.between(dateTime, LocalDateTime.now()).toHours() <= 24) {
                    lastDayTransactions.add(dateTime);
                }
            }

            if (studentChecking.getMaxTransferencesInADay() < lastDayTransactions.size()) {
                if (lastDayTransactions.size() / studentChecking.getMaxTransferencesInADay() * 100 > 150) {
                    studentChecking.setStatus(Status.FROZEN);
                    studentCheckingRepository.save(studentChecking);
                }
                studentChecking.setMaxTransferencesInADay(lastDayTransactions.size());
            }
        }
    }

    @Transactional
    public void transferAmount(Transference transference) throws RuntimeException {
        LOGGER.info("[INIT] Account Holder makes a transference");

        LOGGER.info("Check that sender user exist and it's logged in");
        AccountHolder accountHolder = accountsHolderRepository.findById(transference.getUserId()).orElseThrow(() -> new DataNotFoundException("Sender id not found"));
        if (accountHolder.isLogged() == false) {
            throw new DataNotFoundException("User must be logged in to make a transference");
        }

        LOGGER.info("Check that all accounts exist");
        Account sender = accountRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender account id not found"));
        Account receiver = accountRepository.findById(transference.getReceiverAccountId()).orElseThrow(() -> new DataNotFoundException("Receiver account id not found"));

        if (sender.getBalance().getAmount().compareTo(BigDecimal.ZERO) == -1) {
            LOGGER.info("Sender's account balance can't be negative");
            throw new DataNotFoundException("To make a transference sender balance must be positive");
        }

        LOGGER.info("[INIT] Check that names provided belong to the existing account");
        List<String> primaryOwnerName = accountHolder.getAccounts().stream().map(account -> account.getPrimaryOwner().getName()).collect(Collectors.toList());

        List<String> secondaryOwnerName = new ArrayList<String>();
        if (sender.getSecondaryOwner() != null) {
            secondaryOwnerName = accountHolder.getAccounts().stream().map(account -> account.getSecondaryOwner().getName()).collect(Collectors.toList());
        }

        List<Integer> accountHolderAccountsList = new ArrayList<Integer>();

        if (primaryOwnerName.contains(transference.getSenderName()) || secondaryOwnerName.contains(transference.getSenderName())) {
            for (Account accountId : accountHolder.getAccounts()) {
                accountHolderAccountsList.add(accountId.getAccountId());
            }
        } else {
            throw new DataNotFoundException("Sender user is not associated with that sender account");
        }
        LOGGER.info("[END] Check that names provided belong to the existing account");

        if (!accountHolderAccountsList.contains(transference.getSenderAccountId())) {
            throw new DataNotFoundException("Sender user doesn't have that account id");
        }

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
            transferAmountSenderSaving(transference);
        } else if (sender.getAccountType().equals(AccountType.CHECKING)) {
            transferAmountSenderChecking(transference);
        } else if (sender.getAccountType().equals(AccountType.CREDIT_CARD)) {
            transferAmountSenderCreditCard(transference);
        } else if (sender.getAccountType().equals(AccountType.STUDENT_CHECKING)) {
            transferAmountSenderStudentChecking(transference);
        }

        if (receiver.getAccountType().equals(AccountType.CHECKING)) {
            LOGGER.info("Receiver's account is from type Checking");
            Checking receiverChecking = checkingRepository.findById(transference.getReceiverAccountId()).orElseThrow(() -> new DataNotFoundException("Checking account id not found"));
            receiverChecking.setBalance(new Money(receiverChecking.getBalance().increaseAmount(transference.getAmountToTransfer())));
            if (receiverChecking.getLastPenalty() == 1 && receiverChecking.getBalance().getAmount().compareTo(receiverChecking.getMinimumBalance()) == 1) {
                receiverChecking.setLastPenalty(0);
            }
            checkingRepository.save(receiverChecking);
        } else if (receiver.getAccountType().equals(AccountType.SAVINGS)) {
            LOGGER.info("Receiver's account is from type Savings");
            Saving receiverSaving = savingRepository.findById(transference.getReceiverAccountId()).orElseThrow(() -> new DataNotFoundException("Saving account id not found"));
            receiverSaving.setBalance(new Money(receiverSaving.getBalance().increaseAmount(transference.getAmountToTransfer())));
            if (receiverSaving.getLastPenalty() == 1 && receiverSaving.getBalance().getAmount().compareTo(receiverSaving.getMinimumBalance()) == 1) {
                receiverSaving.setLastPenalty(0);
            }
            savingRepository.save(receiverSaving);
        } else {
            LOGGER.info("Receiver's account is either Student Checking or Credit Card");
            receiver.setBalance(new Money(receiver.getBalance().increaseAmount(transference.getAmountToTransfer())));
            accountRepository.save(receiver);
        }
        LOGGER.info("[END] Account Holder makes a transference");
    }

    public void transferAmountSenderSaving(Transference transference) {
        LOGGER.info("Sender's account is from type Savings");
        Saving savingSender = savingRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender savings account id not found"));

        fraudDetectionSavings(transference.getSenderAccountId());

        savingSender.setBalance(new Money(savingSender.getBalance().decreaseAmount(transference.getAmountToTransfer())));
        savingSender.getTransactionsMade().add(LocalDateTime.now());
        if (savingSender.getBalance().getAmount().compareTo(savingSender.getMinimumBalance()) == -1) {
            if (savingSender.getLastPenalty() == 0) {
                LOGGER.info("Penalty fee applied to Sender's account which is from type Savings");
                savingSender.setBalance(new Money(savingSender.getBalance().decreaseAmount(savingSender.getPenaltyFee())));
                savingSender.setLastPenalty(1);
            }
        }
        savingRepository.save(savingSender);
    }

    public void transferAmountSenderChecking(Transference transference) {
        LOGGER.info("Sender's account is from type Checking");
        Checking checkingSender = checkingRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender checking account id not found"));

        fraudDetectionChecking(transference.getSenderAccountId());

        checkingSender.setBalance(new Money(checkingSender.getBalance().decreaseAmount(transference.getAmountToTransfer())));
        checkingSender.getTransactionsMade().add(LocalDateTime.now());
        if (checkingSender.getBalance().getAmount().compareTo(checkingSender.getMinimumBalance()) == -1) {
            if (checkingSender.getLastPenalty() == 0) {
                LOGGER.info("Penalty fee applied to Sender's account which is from type Checking");
                checkingSender.setBalance(new Money(checkingSender.getBalance().decreaseAmount(checkingSender.getPenaltyFee())));
                checkingSender.setLastPenalty(1);
            }
        }
        checkingRepository.save(checkingSender);
    }

    public void transferAmountSenderCreditCard(Transference transference) {
        LOGGER.info("Sender's account is from type Credit Card");
        CreditCard creditCardSender = creditCardRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender credit card account id not found"));
        creditCardSender.setBalance(new Money(creditCardSender.getBalance().decreaseAmount(transference.getAmountToTransfer())));
        creditCardRepository.save(creditCardSender);
    }

    public void transferAmountSenderStudentChecking(Transference transference) {
        LOGGER.info("Sender's account is from type Student Checking");
        StudentChecking studentCheckingSender = studentCheckingRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender student checking account id not found"));

        fraudDetectionStudentChecking(transference.getSenderAccountId());

        studentCheckingSender.setBalance(new Money(studentCheckingSender.getBalance().decreaseAmount(transference.getAmountToTransfer())));
        studentCheckingSender.getTransactionsMade().add(LocalDateTime.now());
        studentCheckingRepository.save(studentCheckingSender);
    }
}

