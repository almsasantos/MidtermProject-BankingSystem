package com.ironhack.MidtermProject.controller.impl.users;

import com.ironhack.MidtermProject.controller.interfaces.users.ThirdPartyControllerInterface;
import com.ironhack.MidtermProject.dto.LoginAccount;
import com.ironhack.MidtermProject.dto.ThirdPartyTransaction;
import com.ironhack.MidtermProject.model.entities.users.ThirdParty;
import com.ironhack.MidtermProject.service.users.ThirdPartyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Third Party Controller")
@RestController
@RequestMapping("/")
public class ThirdPartyController implements ThirdPartyControllerInterface {
    @Autowired
    private ThirdPartyService thirdPartyService;

    /**
     * Find all ThirdParty created.
     * @return a list of third party users.
     */
    @GetMapping("/thirdparties")
    @ApiOperation(value="Find All Third Parties",
            notes = "Lists all third parties created",
            response = ThirdParty.class)
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdParty> findAll(){
        return thirdPartyService.findAll();
    }

    /**
     * Find ThirdParty By id.
     * @param id receives an integer id of user.
     * @return a third party user corresponding to that id.
     */
    @GetMapping("/thirdparties/{id}")
    @ApiOperation(value="Find Third Parties by Id",
            response = ThirdParty.class)
    @ResponseStatus(HttpStatus.OK)
    public ThirdParty findById(@PathVariable("id") Integer id){
        return thirdPartyService.findById(id);
    }

    /**
     * Allows third party users to login into their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return a third party logged in.
     */
    @PatchMapping("/thirdparties/login")
    @ApiOperation(value="Allow Third Parties to Login Into Accounts",
            response = ThirdParty.class)
    @ResponseStatus(HttpStatus.OK)
    public ThirdParty loginThirdParty(@RequestBody @Valid LoginAccount loginAccount){
        return thirdPartyService.loginThirdParty(loginAccount);
    }

    /**
     * Allows third party users to logout from their accounts.
     * @param loginAccount receives a LoginAccount where the user enters their id and corresponding password.
     * @return a third party logged out.
     */
    @PatchMapping("/thirdparties/logout")
    @ApiOperation(value="Allow Third Parties to Logout from Their Accounts",
            response = ThirdParty.class)
    @ResponseStatus(HttpStatus.OK)
    public ThirdParty logoutThirdParty(@RequestBody @Valid LoginAccount loginAccount){
        return thirdPartyService.logOutThirdParty(loginAccount);
    }

    /**
     * Allows Third Party Users to debit amount in any type of account.
     * @param hashedKey receives the hashedKey unique that third party has.
     * @param thirdPartyTransaction receives ThirdPartyTransaction containing account id, account secretKey and amount to debit.
     */
    @PatchMapping("/debit-transaction")
    @ApiOperation(value="Debit Transaction",
            notes = "Third Parties can debit amount on every type of account",
            response = ThirdParty.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void debitTransaction(@RequestParam("hashed_key") String hashedKey, @RequestBody @Valid ThirdPartyTransaction thirdPartyTransaction){
        thirdPartyService.debitTransaction(hashedKey, thirdPartyTransaction);
    }

    /**
     * Allows Third Party Users to credit amount in any type of account.
     * @param hashedKey receives the hashedKey unique that third party has.
     * @param thirdPartyTransaction receives ThirdPartyTransaction containing account id, account secretKey and amount to credit.
     */
    @PatchMapping("/credit-transaction")
    @ApiOperation(value="Credit Transaction",
            notes = "Third Parties can credit amount on every type of account",
            response = ThirdParty.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void creditTransaction(@RequestParam("hashed_key") String hashedKey, @RequestBody @Valid ThirdPartyTransaction thirdPartyTransaction){
        thirdPartyService.creditTransaction(hashedKey, thirdPartyTransaction);
    }

}
