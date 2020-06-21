package com.ironhack.MidtermProject.controller.impl.users;

import com.ironhack.MidtermProject.model.entities.Admin;
import com.ironhack.MidtermProject.model.entities.CreditCard;
import com.ironhack.MidtermProject.model.entities.Saving;
import com.ironhack.MidtermProject.service.users.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewAdmin(@RequestBody Admin admin){
        adminService.createNewAdmin(admin);
    }

    @PostMapping("/create_savings/{admin_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSavingsAccount(@PathVariable("admin_id") Integer id, @RequestBody Saving savings){
        adminService.createNewSavingsAccount(id, savings);
    }

    @PostMapping("/create_creditCard/{admin_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCreditCardAccount(@PathVariable("admin_id") Integer id, @RequestBody CreditCard creditCard){
        adminService.createNewCreditCardAccount(id, creditCard);
    }
/*
    @PostMapping("/create_account_depending_age/{admin_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewAccount(@PathVariable("admin_id") Integer id, @RequestBody Account account){
        adminService.createNewAccountDepAge(id, account);
    }

 */
}
