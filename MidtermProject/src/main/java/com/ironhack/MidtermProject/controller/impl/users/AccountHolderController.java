package com.ironhack.MidtermProject.controller.impl.users;

import com.ironhack.MidtermProject.controller.interfaces.users.AccountHolderControllerInterface;
import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.dto.Transference;
import com.ironhack.MidtermProject.model.entities.users.AccountHolder;
import com.ironhack.MidtermProject.service.users.AccountHolderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Api(tags = "Account Holder Controller")
@RestController
@RequestMapping("/")
public class AccountHolderController implements AccountHolderControllerInterface {
    @Autowired
    private AccountHolderService accountsHolderService;

    /**
     * Find all AccountHolder users created.
     * @return a list of account holder users.
     */
    @GetMapping("/accountholders")
    @ApiOperation(value="Find All Account Holders",
            notes = "Lists all account holders created",
            response = AccountHolder.class)
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> findAll(){
        return accountsHolderService.findAll();
    }

    /**
     * Find AccountHolder by id.
     * @param id receives an integer id of user.
     * @return an AccountHolder user corresponding to that id.
     */
    @GetMapping("/accountholders/{id}")
    @ApiOperation(value="Find Account Holders by Id",
            response = AccountHolder.class)
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder findById(@PathVariable("id") Integer id){
        return accountsHolderService.findById(id);
    }

    /**
     * Allows AccountHolder users to check balance from their accounts.
     * @param accountId receives an integer with id from account.
     * @param userId receives an integer with id from account holder.
     * @return a BigDecimal with balance from account.
     */
    @GetMapping("/accountholders/balance")
    @ApiOperation(value="Check balance from Account Holders' account",
            response = AccountHolder.class)
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal checkAccountBalance(@RequestParam(value = "account_id", required = true) Integer accountId, @RequestParam(value = "user_id", required = true)  Integer userId){
        return accountsHolderService.checkAccountBalance(accountId, userId);
    }

    /**
     * Allows AccountHolder users to login into their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return an AccountHolder logged in.
     */
    @PatchMapping("/accountholders/login")
    @ApiOperation(value="Allow Account Holders to login their accounts",
            response = AccountHolder.class)
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder loginAccountHolder(@RequestBody @Valid LoginAccount loginAccount){
        return accountsHolderService.loginAccountHolder(loginAccount);
    }

    /**
     * Allows AccountHolder users to logout from their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return an AccountHolder logged out.
     */
    @PatchMapping("/accountholders/logout")
    @ApiOperation(value="Allow Account Holders to logout from their accounts",
            response = AccountHolder.class)
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder logoutAccountHolder(@RequestBody @Valid LoginAccount loginAccount){
        return accountsHolderService.logOutAccountHolder(loginAccount);
    }

    /**
     * Allows account holder users to transfer money from their accounts to others.
     * @param transference receives a Transference with all information needed to make that transaction.
     */
    @PatchMapping("/transference")
    @ApiOperation(value="Account Holders Make a Transference",
            notes = "Allow account holders to make transferences to their accounts or others",
            response = AccountHolder.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transferAmount(@RequestBody @Valid Transference transference){
        accountsHolderService.transferAmount(transference);
    }

    /**
     * Allows admins to create new account holders.
     * @param adminId receives an integer with id from admin.
     * @param accountHolders receives an AccountHolder with personal information needed to create that user.
     */
    @PostMapping("/accountholder")
    @ApiOperation(value="Create a new Account Holder",
            response = AccountHolder.class)
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccountHolder(@RequestParam("admin_id") Integer adminId, @RequestBody @Valid AccountHolder accountHolders){
        accountsHolderService.createAccountHolder(adminId, accountHolders);
    }

}
