package com.devexperts.rest;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import com.devexperts.exception.InsufficientFundsException;
import com.devexperts.exception.NotFoundException;
import com.devexperts.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController extends AbstractAccountController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/operations/transfer")
    public ResponseEntity<Void> transfer(
            @RequestParam("source_id") long sourceId,
            @RequestParam("target_id") long targetId,
            @RequestParam("amount") double amount) {

        Account account = new Account(AccountKey.valueOf(1), "Sesr", "fdf", 232);
        accountService.createAccount(account);
        account = new Account(AccountKey.valueOf(2), "Sesr", "fdf", 232);
        accountService.createAccount(account);

        Account source = accountService.getAccount(sourceId);
        Account target = accountService.getAccount(targetId);
        accountService.transfer(source, target, amount);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "insufficient account balance")
    @ExceptionHandler(InsufficientFundsException.class)
    public void internalServerError(Exception e) {
        log.error("Error: " + e.getMessage(), e);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "amount is invalid or same account id")
    @ExceptionHandler(IllegalArgumentException.class)
    public void badRequest(Exception e) {
        log.error("Error: " + e.getMessage(), e);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "account is not found")
    @ExceptionHandler(NotFoundException.class)
    public void notFound(Exception e) {
        log.error("Error: " + e.getMessage(), e);
    }

}
