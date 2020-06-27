package com.ironhack.MidtermProject.controller.interfaces.users;

import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.dto.ThirdPartyTransaction;
import com.ironhack.MidtermProject.model.entities.users.ThirdParty;

import java.util.List;

public interface ThirdPartyControllerInterface {
    /**
     * Find all ThirdParty created.
     * @return a list of third party users.
     */
    public List<ThirdParty> findAll();

    /**
     * Find ThirdParty By id.
     * @param id receives an integer id of user.
     * @return a third party user corresponding to that id.
     */
    public ThirdParty findById(Integer id);

    /**
     * Allows third party users to login into their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return a third party logged in.
     */
    public ThirdParty loginThirdParty(LoginAccount loginAccount);

    /**
     * Allows third party users to logout from their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return a third party logged out.
     */
    public ThirdParty logoutThirdParty(LoginAccount loginAccount);

    /**
     * Allows Third Party Users to debit amount in any type of account.
     * @param hashedKey receives the hashedKey unique that third party has.
     * @param thirdPartyTransaction receives ThirdPartyTransaction containing account id, account secretKey and amount to debit.
     */
    public void debitTransaction(String hashedKey,ThirdPartyTransaction thirdPartyTransaction);

    /**
     * Allows Third Party Users to credit amount in any type of account.
     * @param hashedKey receives the hashedKey unique that third party has.
     * @param thirdPartyTransaction receives ThirdPartyTransaction containing account id, account secretKey and amount to credit.
     */
    public void creditTransaction(String hashedKey, ThirdPartyTransaction thirdPartyTransaction);
}
