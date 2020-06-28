package com.ironhack.MidtermProject.controller.impl.users;

import com.ironhack.MidtermProject.controller.interfaces.users.AdminControllerInterface;
import com.ironhack.MidtermProject.dto.ChangeBalance;
import com.ironhack.MidtermProject.dto.CreateThirdParty;
import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.model.entities.accounts.Account;
import com.ironhack.MidtermProject.model.entities.users.Admin;
import com.ironhack.MidtermProject.model.viewmodel.AccountViewModel;
import com.ironhack.MidtermProject.model.viewmodel.CreditCardViewModel;
import com.ironhack.MidtermProject.model.viewmodel.SavingViewModel;
import com.ironhack.MidtermProject.service.users.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Api(tags = "Admin Controller")
@RestController
@RequestMapping("/")
public class AdminController implements AdminControllerInterface {
    @Autowired
    private AdminService adminService;

    /**
     * Find all Admin created.
     * @return a list of admin users.
     */
    @GetMapping("/admins")
    @ApiOperation(value="Find All Admins",
            notes = "Lists all admins created",
            response = Admin.class)
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> findAll(){
        return adminService.findAll();
    }

    /**
     * Find Admin by id.
     * @param id receives an integer id of user.
     * @return an admin user corresponding to that id.
     */
    @GetMapping("/admins/{id}")
    @ApiOperation(value="Find Admins by Id",
            response = Admin.class)
    @ResponseStatus(HttpStatus.OK)
    public Admin findById(@PathVariable("id") Integer id){
        return adminService.findById(id);
    }

    /**
     * Find Admin by name.
     * @param name receives a string with a name.
     * @return a list of admin users.
     */
    @GetMapping("/admins-name/{name}")
    @ApiOperation(value="Find Admins by Name",
            notes = "Lists all admins by name",
            response = Admin.class)
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> findByName(@PathVariable("name") String name){
        return adminService.findByName(name);
    }

    /**
     * Allow admins to unfreeze accounts with status frozen.
     * @param adminId receives an integer with id from admin.
     * @param accountId receives an integer with id from account.
     */
    @GetMapping("/admins/unfreeze-account")
    @ApiOperation(value="Admins Unfreeze Frozen Accounts",
            notes = "Allow admins to turn account status back to ACTIVE",
            response = Admin.class)
    @ResponseStatus(HttpStatus.OK)
    public void unfreezeAccount(@RequestParam("admin_id") Integer adminId, @RequestParam("account_id") Integer accountId){
        adminService.unfreezeAccount(adminId, accountId);
    }

    /**
     * Allows admin users to login into their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return an admin logged in.
     */
    @PatchMapping("/admins/login")
    @ApiOperation(value="Allow Admins to Login Into Accounts",
            response = Admin.class)
    @ResponseStatus(HttpStatus.OK)
    public Admin loginAdmin(@RequestBody @Valid LoginAccount loginAccount){
        return adminService.loginAdmin(loginAccount);
    }

    /**
     * Allows admin users to logout from their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return an admin logged out.
     */
    @PatchMapping("/admins/logout")
    @ApiOperation(value="Allow Admins to Logout from Their Accounts",
            response = Admin.class)
    @ResponseStatus(HttpStatus.OK)
    public Admin logoutAdmin(@RequestBody @Valid LoginAccount loginAccount){
        return adminService.logOutAdmin(loginAccount);
    }

    /**
     * Allows admin to check the balance from any account.
     * @param adminId receives an integer with id from admin.
     * @param accountId receives an integer with id from account.
     * @return a BigDecimal with the balance from that account.
     */
    // --- GET BALANCE FROM ANY ACCOUNT ---
    @GetMapping("/admin/balance")
    @ApiOperation(value="Admins Check Balance From Every Type of Account",
            response = Admin.class)
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal checkAccountBalance(@RequestParam(value = "admin_id", required = true) Integer adminId, @RequestParam(value = "account_id", required = true) Integer accountId){
        return adminService.checkAccountBalance(adminId, accountId);
    }

    /**
     * Creates a new admin
     * @param admin receives an admin with name and password defined.
     */
    @PostMapping("/admin")
    @ApiOperation(value="Create New Admin",
            response = Admin.class)
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewAdmin(@RequestBody @Valid Admin admin){
        adminService.createNewAdmin(admin);
    }

    // --- CREATE THIRD PARTY ---
    /**
     * Allows admin users to create a new ThirdParty user.
     * @param adminId receives an integer with id from admin.
     * @param createThirdParty receives a createThirdParty with allow information needed to create that user.
     */
    @PostMapping("/third-party")
    @ApiOperation(value="Create New Third Party",
            notes = "Admins can create a new Third Party",
            response = Admin.class)
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewThirdParty(@RequestParam("admin_id") Integer adminId, @RequestBody @Valid CreateThirdParty createThirdParty){
        adminService.createNewThirdParty(adminId, createThirdParty);
    }

    // --- CREATE ACCOUNTS ---
    /**
     * Creates new savings account.
     * @param adminId receives an integer with id from admin.
     * @param savingViewModel receives a SavingViewModel that will allow a savings account to be created.
     */
    @PostMapping("/account/savings")
    @ApiOperation(value="Create New Savings Account",
            notes = "Admins create new Savings account",
            response = Admin.class)
    @ResponseStatus(HttpStatus.CREATED)
    public void createSavingsAccount(@RequestParam("admin_id") Integer adminId, @RequestBody @Valid SavingViewModel savingViewModel){
        adminService.createNewSavingsAccount(adminId, savingViewModel);
    }

    /**
     * Creates new credit card account.
     * @param adminId receives an integer with id from admin.
     * @param creditCardViewModel receives a creditCardViewModel that will allow a credit card account to be created.
     */
    @PostMapping("/account/credit-card")
    @ApiOperation(value="Create New Credit Card Account",
            notes = "Admins create new Credit Card account",
            response = Admin.class)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCreditCardAccount(@RequestParam("admin_id") Integer adminId, @RequestBody @Valid CreditCardViewModel creditCardViewModel){
        adminService.createNewCreditCardAccount(adminId, creditCardViewModel);
    }

    /**
     * Creates new checking or student checking account depending on the age of primary owner.
     * @param adminId receives an integer with id from admin.
     * @param accountViewModel receives an AccountViewModel that will allow any of both accounts to be created.
     */
    @PostMapping("/account/depending-age")
    @ApiOperation(value="Create New Checking or Student Checking Account",
            notes = "Admins create new Checking or Student Checking account depending on the age of the Primary Owner",
            response = Admin.class)
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewAccount(@RequestParam("admin_id") Integer adminId, @RequestBody @Valid AccountViewModel accountViewModel){
        adminService.createNewAccountDepAge(adminId, accountViewModel);
    }

    // --- DEBIT AND CREDIT BALANCE ---
    /**
     * Allows admin to debit amount in any account.
     * @param adminId receives an integer with id from admin.
     * @param changeBalance receives ChangeBalance that will allow the transaction.
     */
    @PatchMapping("/debit-balance")
    @ApiOperation(value="Debit Balance",
            notes = "Admins can debit amount on every type of account",
            response = Admin.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debitBalance(@RequestParam("admin_id") Integer adminId, @RequestBody @Valid ChangeBalance changeBalance){
        adminService.debitBalance(adminId, changeBalance);
    }

    /**
     * Allows admin to credit amount in any account.
     * @param adminId receives an integer with id from admin.
     * @param changeBalance receives ChangeBalance that will allow the transaction.
     */
    @PatchMapping("/credit-balance")
    @ApiOperation(value="Credit Balance",
            notes = "Admins can credit amount on every type of account",
            response = Admin.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditBalance(@RequestParam("admin_id") Integer adminId, @RequestBody @Valid ChangeBalance changeBalance){
        adminService.creditBalance(adminId, changeBalance);
    }

    /**
     * Allow admins to delete any type of account.
     * @param accountId receives an integer with id from account.
     * @param adminId receives an integer with id from admin.
     */
    @DeleteMapping("/account")
    @ApiOperation(value="Delete account",
            response = Account.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@RequestParam("account_id") Integer accountId, @RequestParam("admin_id") Integer adminId){
        adminService.deleteAccount(accountId, adminId);
    }

}
