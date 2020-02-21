package com.devexperts.rest;

import com.devexperts.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController extends AbstractAccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/operations/transfer")
    public ResponseEntity<Void> transfer(@RequestParam("source_id") long sourceId,
                                         @RequestParam("target_id") long targetId,
                                         @RequestParam("amount") double amount) {

        accountService.transfer(sourceId, targetId, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
