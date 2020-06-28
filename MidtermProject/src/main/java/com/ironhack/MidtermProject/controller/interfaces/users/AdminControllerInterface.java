package com.ironhack.MidtermProject.controller.interfaces.users;

import com.ironhack.MidtermProject.dto.ChangeBalance;
import com.ironhack.MidtermProject.dto.CreateThirdParty;
import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.model.entities.users.Admin;
import com.ironhack.MidtermProject.model.viewmodel.AccountViewModel;
import com.ironhack.MidtermProject.model.viewmodel.CreditCardViewModel;
import com.ironhack.MidtermProject.model.viewmodel.SavingViewModel;

import java.math.BigDecimal;
import java.util.List;

public interface AdminControllerInterface {
    /**
     * Find all Admin created.
     * @return a list of admin users.
     */
    public List<Admin> findAll();

    /**
     * Find Admin by id.
     * @param id receives an integer id of user.
     * @return an admin user corresponding to that id.
     */
    public Admin findById(Integer id);

    /**
     * Find Admin by name.
     * @param name receives a string with a name.
     * @return a list of admin users.
     */
    public List<Admin> findByName(String name);

    /**
     * Allow admins to unfreeze accounts with status frozen.
     * @param adminId receives an integer with id from admin.
     * @param accountId receives an integer with id from account.
     */
    public void unfreezeAccount(Integer adminId, Integer accountId);

    /**
     * Allows admin users to login into their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return an admin logged in.
     */
    public Admin loginAdmin(LoginAccount loginAccount);

    /**
     * Allows admin users to logout from their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return an admin logged out.
     */
    public Admin logoutAdmin(LoginAccount loginAccount);

    /**
     * Allows admin to check the balance from any account.
     * @param adminId receives an integer with id from admin.
     * @param accountId receives an integer with id from account.
     * @return a BigDecimal with the balance from that account.
     */
    public BigDecimal checkAccountBalance(Integer adminId, Integer accountId);

    /**
     * Creates a new admin
     * @param admin receives an admin with name and password defined.
     */
    public void createNewAdmin(Admin admin);

    /**
     * Allows admin users to create a new ThirdParty user.
     * @param adminId receives an integer with id from admin.
     * @param createThirdParty receives a createThirdParty with allow information needed to create that user.
     */
    public void createNewThirdParty(Integer adminId, CreateThirdParty createThirdParty);

    /**
     * Creates new savings account.
     * @param adminId receives an integer with id from admin.
     * @param savingViewModel receives a SavingViewModel that will allow a savings account to be created.
     */
    public void createSavingsAccount(Integer adminId, SavingViewModel savingViewModel);

    /**
     * Creates new credit card account.
     * @param adminId receives an integer with id from admin.
     * @param creditCardViewModel receives a creditCardViewModel that will allow a credit card account to be created.
     */
    public void createCreditCardAccount(Integer adminId, CreditCardViewModel creditCardViewModel);

    /**
     * Creates new checking or student checking account depending on the age of primary owner.
     * @param adminId receives an integer with id from admin.
     * @param accountViewModel receives an AccountViewModel that will allow any of both accounts to be created.
     */
    public void createNewAccount(Integer adminId, AccountViewModel accountViewModel);

    /**
     * Allows admin to debit amount in any account.
     * @param adminId receives an integer with id from admin.
     * @param changeBalance receives ChangeBalance that will allow the transaction.
     */
    public void debitBalance(Integer adminId, ChangeBalance changeBalance);

    /**
     * Allows admin to credit amount in any account.
     * @param adminId receives an integer with id from admin.
     * @param changeBalance receives ChangeBalance that will allow the transaction.
     */
    public void creditBalance(Integer adminId, ChangeBalance changeBalance);

    /**
     * Allow admins to delete any type of account.
     * @param accountId receives an integer with id from account.
     * @param adminId receives an integer with id from admin.
     */
    public void deleteAccount(Integer accountId, Integer adminId);
}
