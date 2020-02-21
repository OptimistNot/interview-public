package com.devexperts.controller;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import com.devexperts.model.Error;
import com.devexperts.model.Transaction;
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

    private static final String TRANSACTION_URL = "/api/operations/transfer";

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

        ResponseEntity<Void> result = testRestTemplate.exchange(TRANSACTION_URL,
                HttpMethod.POST,
                new HttpEntity<>(createTransaction()),
                Void.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testSourceAccountNotFound() {
        accountService.createAccount(createAccount(2, 0.0));

        ResponseEntity<Error> result = testRestTemplate.exchange(TRANSACTION_URL,
                HttpMethod.POST,
                new HttpEntity<>(createTransaction()),
                Error.class);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testInsufficientFunds() {
        accountService.createAccount(createAccount(1, 10.0));
        accountService.createAccount(createAccount(2, 0.0));

        Transaction transaction = createTransaction();
        transaction.setAmount(20.0);

        ResponseEntity<Error> result = testRestTemplate.exchange(TRANSACTION_URL,
                HttpMethod.POST,
                new HttpEntity<>(transaction),
                Error.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void testAmountIsNegative() {
        accountService.createAccount(createAccount(1, 10.0));
        accountService.createAccount(createAccount(2, 0.0));

        Transaction transaction = createTransaction();
        transaction.setAmount(-2.0);

        ResponseEntity<Error> result = testRestTemplate.exchange(TRANSACTION_URL,
                HttpMethod.POST,
                new HttpEntity<>(transaction),
                Error.class);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testEmptyBody() {
        accountService.createAccount(createAccount(1, 10.0));
        accountService.createAccount(createAccount(2, 0.0));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<Error> result = testRestTemplate.exchange(TRANSACTION_URL,
                HttpMethod.POST,
                new HttpEntity<>(null, httpHeaders),
                Error.class);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    private Transaction createTransaction() {
        return new Transaction.Builder()
                .withSourceId(1)
                .withTargetId(2)
                .withAmount(2.0)
                .build();
    }

    private Account createAccount(long id, double balance) {
        AccountKey accountKey = AccountKey.valueOf(id);
        return new Account(accountKey, "john", "doe", balance);
    }


}
