package com.ironhack.MidtermProject.controller.impl.users;

import com.ironhack.MidtermProject.model.entities.users.Admin;
import com.ironhack.MidtermProject.model.entities.users.ThirdParty;
import com.ironhack.MidtermProject.model.viewmodel.AccountViewModel;
import com.ironhack.MidtermProject.model.viewmodel.CreditCardViewModel;
import com.ironhack.MidtermProject.model.viewmodel.SavingViewModel;
import com.ironhack.MidtermProject.service.users.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/admins_name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> findByName(@PathVariable("name") String name){
        return adminService.findByName(name);
    }

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewAdmin(@RequestBody Admin admin){
        adminService.createNewAdmin(admin);
    }

    // --- CREATE THIRD PARTY ---
    @PostMapping("/create_third_party/{admin_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewThirdParty(@PathVariable("admin_id") Integer adminId, @RequestBody ThirdParty thirdParty){
        adminService.createNewThirdParty(adminId, thirdParty);
    }

    // --- CREATE ACCOUNTS ---
    @PostMapping("/create_savings/{admin_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSavingsAccount(@PathVariable("admin_id") Integer id, @RequestBody @Valid SavingViewModel savingViewModel){
        adminService.createNewSavingsAccount(id, savingViewModel);
    }

    @PostMapping("/create_creditCard/{admin_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCreditCardAccount(@PathVariable("admin_id") Integer id, @RequestBody @Valid CreditCardViewModel creditCardViewModel){
        adminService.createNewCreditCardAccount(id, creditCardViewModel);
    }

    @PostMapping("/create_account_depending_age/{admin_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewAccount(@PathVariable("admin_id") Integer id, @RequestBody @Valid AccountViewModel accountViewModel){
        adminService.createNewAccountDepAge(id, accountViewModel);
    }
}
