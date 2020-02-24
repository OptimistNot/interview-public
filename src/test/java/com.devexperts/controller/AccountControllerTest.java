package com.devexperts.controller;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import com.devexperts.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {

    private static final String TRANSACTION_URL = "/api/operations/transfer?source_id=%s&target_id=%s&amount=%s";

    @Autowired
    private AccountService accountService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setup() {
        accountService.clear();
    }

    @Test
    public void testTransaction() {
        accountService.createAccount(createAccount(1, 10.0));
        accountService.createAccount(createAccount(2, 0.0));

        ResponseEntity<Void> result = testRestTemplate.exchange(
                String.format(TRANSACTION_URL, "1", "2", "2.0"),
                HttpMethod.POST,
                null,
                Void.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testSourceAccountNotFound() {
        accountService.createAccount(createAccount(2, 0.0));

        ResponseEntity<Error> result = testRestTemplate.exchange(
                String.format(TRANSACTION_URL, "1", "2", "2.0"),
                HttpMethod.POST,
                null,
                Error.class);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testInsufficientFunds() {
        accountService.createAccount(createAccount(1, 10.0));
        accountService.createAccount(createAccount(2, 0.0));

        ResponseEntity<Error> result = testRestTemplate.exchange(
                String.format(TRANSACTION_URL, "1", "2", "20.0"),
                HttpMethod.POST,
                null,
                Error.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void testAmountIsNegative() {
        accountService.createAccount(createAccount(1, 10.0));
        accountService.createAccount(createAccount(2, 0.0));

        ResponseEntity<Error> result = testRestTemplate.exchange(
                String.format(TRANSACTION_URL, "1", "2", "-2.0"),
                HttpMethod.POST,
                null,
                Error.class);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testEmptyParameter() {
        accountService.createAccount(createAccount(1, 10.0));
        accountService.createAccount(createAccount(2, 0.0));

        ResponseEntity<Error> result = testRestTemplate.exchange(
                "/api/operations/transfer?source_id=1&target_id=2",
                HttpMethod.POST,
                null,
                Error.class);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    private Account createAccount(long id, double balance) {
        AccountKey accountKey = AccountKey.valueOf(id);
        return new Account(accountKey, "john", "doe", balance);
    }


}
