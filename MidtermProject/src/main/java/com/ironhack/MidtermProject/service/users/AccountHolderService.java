package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.dto.Transference;
import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.classes.Money;
import com.ironhack.MidtermProject.model.entities.accounts.*;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import com.ironhack.MidtermProject.repository.accounts.*;
import com.ironhack.MidtermProject.repository.users.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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

    public List<AccountHolder> findAll() {
        return accountsHolderRepository.findAll();
    }

    public AccountHolder findById(Integer id) {
        return accountsHolderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Account Holder id not found"));
    }

    public void createAccountHolder(AccountHolder accountHolder) {
        accountsHolderRepository.save(accountHolder);
    }

    public BigDecimal checkAccountBalance(Integer accountId, Integer primaryOwnerId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Account id not found"));
        if (account.getPrimaryOwner().getUserId() == primaryOwnerId) {
            if (account.getAccountType().equals(AccountType.CHECKING)) {
                return accountsHolderRepository.checkCheckingBalance(accountId);
            } else if (account.getAccountType().equals(AccountType.STUDENT_CHECKING)) {
                return accountsHolderRepository.checkStudentCheckingBalance(accountId);
            } else if (account.getAccountType().equals(AccountType.SAVINGS)) {
                return accountsHolderRepository.checkSavingsBalance(accountId);
            } else {
                return accountsHolderRepository.checkCreditCardBalance(accountId);
            }
        } else {
            throw new DataNotFoundException("User doesn't have access to this account.");
        }
    }


    @Transactional
    public void transferAmount(Transference transference) {
        AccountHolder accountHolder = accountsHolderRepository.findById(transference.getUserId()).orElseThrow(() -> new DataNotFoundException("Sender id not found"));
        Account sender = accountRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender account id not found"));
        Account receiver = accountRepository.findById(transference.getReceiverAccountId()).orElseThrow(() -> new DataNotFoundException("Receiver account id not found"));

        List<String> primaryOwnerName = accountHolder.getAccounts().stream().map(account -> account.getPrimaryOwner().getName()).collect(Collectors.toList());

        List<String> secondaryOwnerName = new ArrayList<String>();
        if (sender.getSecondaryOwner() != null) {
            secondaryOwnerName = accountHolder.getAccounts().stream().map(account -> account.getSecondaryOwner().getName()).collect(Collectors.toList());
        }

        boolean b = receiver.getPrimaryOwner().equals(transference.getReceiverFirstName());
        boolean c = receiver.getSecondaryOwner()==null;
        System.out.println(b);
        System.out.println(c);

        List<Integer> accountHolderAccountsList;

        if (primaryOwnerName.contains(transference.getSenderFirstName()) || secondaryOwnerName.contains(transference.getSenderFirstName())) {
            accountHolderAccountsList = accountHolder.getAccounts().stream().map(account -> account.getAccountId()).collect(Collectors.toList());

        } else {
            throw new DataNotFoundException("User is not associated with that account");
        }

        Saving savingSender;
        Checking checkingSender;
        CreditCard creditCardSender;
        StudentChecking studentCheckingSender;

        if (accountHolderAccountsList.contains(transference.getSenderAccountId())) {

            if (sender.getAccountType().equals(AccountType.SAVINGS)) {
                savingSender = savingRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender savings account id not found"));
                savingSender.setBalance(new Money(savingSender.getBalance().decreaseAmount(transference.getAmountToTransfer())));
                if (savingSender.getBalance().getAmount().compareTo(savingSender.getMinimumBalance()) == -1) {
                    savingSender.setBalance(new Money(savingSender.getBalance().decreaseAmount(savingSender.getPenaltyFee())));
                }
                savingRepository.save(savingSender);

            } else if (sender.getAccountType().equals(AccountType.CHECKING)) {
                checkingSender = checkingRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender checking account id not found"));
                checkingSender.setBalance(new Money(checkingSender.getBalance().decreaseAmount(transference.getAmountToTransfer())));
                if (checkingSender.getBalance().getAmount().compareTo(checkingSender.getMinimumBalance()) == -1) {
                    checkingSender.setBalance(new Money(checkingSender.getBalance().decreaseAmount(checkingSender.getPenaltyFee())));
                }
                checkingRepository.save(checkingSender);

            } else if (sender.getAccountType().equals(AccountType.CREDIT_CARD)) {
                creditCardSender = creditCardRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender credit card account id not found"));
                creditCardSender.setBalance(new Money(creditCardSender.getBalance().decreaseAmount(transference.getAmountToTransfer())));

            } else if (sender.getAccountType().equals(AccountType.STUDENT_CHECKING)) {
                studentCheckingSender = studentCheckingRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender student checking account id not found"));
                studentCheckingSender.setBalance(new Money(studentCheckingSender.getBalance().decreaseAmount(transference.getAmountToTransfer())));
            }

            receiver.setBalance(new Money(receiver.getBalance().increaseAmount(transference.getAmountToTransfer())));
            accountRepository.save(receiver);
        }
    }
}

