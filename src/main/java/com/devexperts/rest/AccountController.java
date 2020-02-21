package com.devexperts.rest;

import com.devexperts.model.Transaction;
import com.devexperts.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController extends AbstractAccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/operations/transfer")
    public ResponseEntity<Void> transfer(@RequestBody Transaction transaction) {

        accountService.transfer(transaction.getSourceId(), transaction.getTargetId(), transaction.getAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
