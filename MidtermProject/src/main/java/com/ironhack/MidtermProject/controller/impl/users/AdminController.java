package com.ironhack.MidtermProject.controller.impl.users;

import com.ironhack.MidtermProject.dto.ChangeBalance;
import com.ironhack.MidtermProject.dto.CreateThirdParty;
import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.model.entities.users.Admin;
import com.ironhack.MidtermProject.model.viewmodel.AccountViewModel;
import com.ironhack.MidtermProject.model.viewmodel.CreditCardViewModel;
import com.ironhack.MidtermProject.model.viewmodel.SavingViewModel;
import com.ironhack.MidtermProject.service.users.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/admins")
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> findAll(){
        return adminService.findAll();
    }

    @GetMapping("/admins/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Admin findById(@PathVariable("id") Integer id){
        return adminService.findById(id);
    }

    @GetMapping("/admins-name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> findByName(@PathVariable("name") String name){
        return adminService.findByName(name);
    }

    @GetMapping("/admins/unfreeze-account")
    @ResponseStatus(HttpStatus.OK)
    public void unfreezeAccount(@RequestParam("admin_id") Integer adminId, @RequestParam("account_id") Integer accountId){
        adminService.unfreezeAccount(adminId, accountId);
    }

    @PatchMapping("/admins/login")
    @ResponseStatus(HttpStatus.OK)
    public Admin loginAdmin(@RequestBody @Valid LoginAccount loginAccount){
        return adminService.loginAdmin(loginAccount);
    }

    @PatchMapping("/admins/logout")
    @ResponseStatus(HttpStatus.OK)
    public Admin logoutAdmin(@RequestBody @Valid LoginAccount loginAccount){
        return adminService.logOutAdmin(loginAccount);
    }

    // --- GET BALANCE FROM ANY ACCOUNT ---
    @GetMapping("/admin/balance")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal checkAccountBalance(@RequestParam(value = "admin_id", required = true) Integer adminId, @RequestParam(value = "account_id", required = true) Integer accountId){
        return adminService.checkAccountBalance(adminId, accountId);
    }

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewAdmin(@RequestBody @Valid Admin admin){
        adminService.createNewAdmin(admin);
    }

    // --- CREATE THIRD PARTY ---
    @PostMapping("/third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewThirdParty(@RequestParam("admin_id") Integer adminId, @RequestBody @Valid CreateThirdParty createThirdParty){
        adminService.createNewThirdParty(adminId, createThirdParty);
    }

    // --- CREATE ACCOUNTS ---
    @PostMapping("/account/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSavingsAccount(@RequestParam("admin_id") Integer adminId, @RequestBody @Valid SavingViewModel savingViewModel){
        adminService.createNewSavingsAccount(adminId, savingViewModel);
    }

    @PostMapping("/account/credit-card")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCreditCardAccount(@RequestParam("admin_id") Integer adminId, @RequestBody @Valid CreditCardViewModel creditCardViewModel){
        adminService.createNewCreditCardAccount(adminId, creditCardViewModel);
    }

    @PostMapping("/account/depending-age")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewAccount(@RequestParam("admin_id") Integer adminId, @RequestBody @Valid AccountViewModel accountViewModel){
        adminService.createNewAccountDepAge(adminId, accountViewModel);
    }

    // --- DEBIT AND CREDIT BALANCE ---
    @PatchMapping("/debit-balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debitBalance(@RequestParam("admin_id") Integer adminId, @RequestBody @Valid ChangeBalance changeBalance){
        adminService.debitBalance(adminId, changeBalance);
    }

    @PatchMapping("/credit-balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditBalance(@RequestParam("admin_id") Integer adminId, @RequestBody @Valid ChangeBalance changeBalance){
        adminService.creditBalance(adminId, changeBalance);
    }

}
