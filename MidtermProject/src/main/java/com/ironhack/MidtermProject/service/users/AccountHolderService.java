package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.dto.Transference;
import com.ironhack.MidtermProject.enums.AccountType;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.entities.accounts.Account;
import com.ironhack.MidtermProject.model.entities.accounts.Checking;
import com.ironhack.MidtermProject.model.entities.accounts.Saving;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import com.ironhack.MidtermProject.repository.accounts.AccountRepository;
import com.ironhack.MidtermProject.repository.accounts.CheckingRepository;
import com.ironhack.MidtermProject.repository.accounts.SavingRepository;
import com.ironhack.MidtermProject.repository.users.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public List<AccountHolder> findAll() {
        return accountsHolderRepository.findAll();
    }

    public AccountHolder findById(Integer id) {
        return accountsHolderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Account Holder id not found"));
    }

    public void createAccountHolder(AccountHolder accountHolders) {
        accountsHolderRepository.save(accountHolders);
    }

    public List<Object[]> checkAccountBalance(Integer accountId, Integer primaryOwnerId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Account id not found"));
        if (account.getPrimaryOwner().getUserId() == primaryOwnerId) {
            return accountsHolderRepository.checkAccountBalance(accountId);
        } else {
            throw new DataNotFoundException("User doesn't have access to this account.");
        }
    }

    @Transactional
    public void transferAmount(String name, Transference transference) {
        AccountHolder accountHolder = accountsHolderRepository.findById(transference.getUserId()).orElseThrow(() -> new DataNotFoundException("User id not found"));

        List<String> primaryOwnerName = accountHolder.getAccounts().stream().map(account -> account.getPrimaryOwner().getName()).collect(Collectors.toList());
        List<String> secondaryOwnerName = accountHolder.getAccounts().stream().map(account -> account.getSecondaryOwner().getName()).collect(Collectors.toList());

        List<Integer> accountHolderAccountsList;
        Account sender;
        Account receiver;
        if (primaryOwnerName.contains(name) || secondaryOwnerName.contains(name)) {
            accountHolderAccountsList = accountHolder.getAccounts().stream().map(account -> account.getAccountId()).collect(Collectors.toList());

            sender = accountRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender account id not found"));
            receiver = accountRepository.findById(transference.getReceiverAccountId()).orElseThrow(() -> new DataNotFoundException("Receiver account id not found"));
        } else {
            throw new DataNotFoundException("User id is not associated with that account");
        }

        Saving savingSender;
        Checking checkingSender;
        if (sender.getAccountType().equals(AccountType.SAVING)) {
            savingSender = savingRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender account id not found"));
            if (accountHolderAccountsList.contains(transference.getSenderAccountId())) {
                savingSender.setBalance(savingSender.getBalance().subtract(transference.getAmount()));
                if(savingSender.getBalance().compareTo(savingSender.getMinimumBalance()) == -1){
                    savingSender.setBalance(savingSender.getBalance().subtract(savingSender.getPenaltyFee()));
                }
                savingRepository.save(savingSender);
            } else if (sender.getAccountType().equals(AccountType.CHECKING)) {
                checkingSender = checkingRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender account id not found"));
                if (accountHolderAccountsList.contains(transference.getSenderAccountId())) {
                    checkingSender.setBalance(checkingSender.getBalance().subtract(transference.getAmount()));
                    if(checkingSender.getBalance().compareTo(checkingSender.getMinimumBalance()) == -1){
                        checkingSender.setBalance(checkingSender.getBalance().subtract(checkingSender.getPenaltyFee()));
                    }
                    savingRepository.save(savingSender);
                }
            }
            receiver.setBalance(receiver.getBalance().add(transference.getAmount()));
            accountRepository.save(receiver);
        }
    }
}

