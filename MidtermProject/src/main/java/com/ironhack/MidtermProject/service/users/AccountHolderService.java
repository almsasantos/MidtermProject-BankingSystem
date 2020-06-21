package com.ironhack.MidtermProject.service.users;

import com.ironhack.MidtermProject.dto.Transference;
import com.ironhack.MidtermProject.exception.DataNotFoundException;
import com.ironhack.MidtermProject.model.entities.Account;
import com.ironhack.MidtermProject.model.entities.AccountHolder;
import com.ironhack.MidtermProject.repository.accounts.AccountRepository;
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

    public List<AccountHolder> findAll(){
        return accountsHolderRepository.findAll();
    }

    public AccountHolder findById(Integer id){
        return accountsHolderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Account Holder id not found"));
    }

    public void createAccountHolder(AccountHolder accountHolders){
        accountsHolderRepository.save(accountHolders);
    }

    public List<Object[]> checkAccountBalance(Integer accountId, Integer primaryOwnerId){
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new DataNotFoundException("Account id not found"));
        if(account.getPrimaryOwner().getUserId() == primaryOwnerId){
            return accountsHolderRepository.checkAccountBalance(accountId);
        } else {
            throw new DataNotFoundException("User doesn't have access to this account.");
        }
    }

    @Transactional
    public void transferAmount(String name, Transference transference){
        AccountHolder accountHolder = accountsHolderRepository.findById(transference.getUserId()).orElseThrow(() -> new DataNotFoundException("User id not found"));

        List<String> primaryOwnerName = accountHolder.getAccounts().stream().map(account -> account.getPrimaryOwner().getName()).collect(Collectors.toList());
        List<String> secondaryOwnerName = accountHolder.getAccounts().stream().map(account -> account.getSecondaryOwner().getName()).collect(Collectors.toList());

        if(primaryOwnerName.contains(name) || secondaryOwnerName.contains(name)){
            Account sender = accountRepository.findById(transference.getSenderAccountId()).orElseThrow(() -> new DataNotFoundException("Sender account id not found"));
            List<Integer> accountHolderAccountsList = accountHolder.getAccounts().stream().map(account -> account.getAccountId()).collect(Collectors.toList());

            if(accountHolderAccountsList.contains(transference.getSenderAccountId())){
                sender.setBalance(sender.getBalance().subtract(transference.getAmount()));
                accountRepository.save(sender);

                Account receiver = accountRepository.findById(transference.getReceiverAccountId()).orElseThrow(() -> new DataNotFoundException("Receiver account id not found"));
                receiver.setBalance(receiver.getBalance().add(transference.getAmount()));
                accountRepository.save(receiver);
            } else {
                throw new DataNotFoundException("User id is not associated with that account");
            }
        }
    }
}
