package com.devexperts;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import com.devexperts.service.AccountServiceImpl;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    public AccountServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAccount() {
        AccountKey accountKey = AccountKey.valueOf(1L);
        Account account = new Account(accountKey, "john", "doe", 0.0);

        accountService.createAccount(account);

        assertEquals(account, accountService.getAccount(1));
    }

    @Test
    public void testClearAccounts() {
        AccountKey accountKey = AccountKey.valueOf(1L);
        Account account = new Account(accountKey, "john", "doe", 0.0);

        accountService.createAccount(account);

        assertEquals(account, accountService.getAccount(1));

        accountService.clear();

        assertNull(accountService.getAccount(1));
    }
}
