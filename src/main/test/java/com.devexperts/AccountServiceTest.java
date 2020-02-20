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

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidAccount() {
        accountService.createAccount(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAccountInvalidAccountKey() {
        Account account = new Account(null, "john", "doe", 0.0);
        accountService.createAccount(account);
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

    @Test
    public void testTransferFunds() {
        //create source account
        AccountKey sourceAccountKey = AccountKey.valueOf(1L);
        Account sourceAccount = new Account(sourceAccountKey, "john", "doe", 10.0);

        accountService.createAccount(sourceAccount);

        //create target account
        AccountKey targetAccountKey = AccountKey.valueOf(2L);
        Account targetAccount = new Account(targetAccountKey, "john", "doe", 0.0);

        accountService.createAccount(targetAccount);

        accountService.transfer(sourceAccount, targetAccount, 3.0);

        assertEquals(7.0, accountService.getAccount(1).getBalance(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferFundsInvalidSourceAccount() {
        //create target account
        AccountKey targetAccountKey = AccountKey.valueOf(2L);
        Account targetAccount = new Account(targetAccountKey, "john", "doe", 0.0);

        accountService.createAccount(targetAccount);

        accountService.transfer(null, targetAccount, 3.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferFundsInvalidTargetAccount() {
        //create source account
        AccountKey sourceAccountKey = AccountKey.valueOf(1L);
        Account sourceAccount = new Account(sourceAccountKey, "john", "doe", 10.0);

        accountService.createAccount(sourceAccount);

        accountService.transfer(sourceAccount, null, 3.0);

        assertEquals(7.0, accountService.getAccount(1).getBalance(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferFundsNonExistingAccount() {
        //create source account
        AccountKey sourceAccountKey = AccountKey.valueOf(1L);
        Account sourceAccount = new Account(sourceAccountKey, "john", "doe", 10.0);

        accountService.createAccount(sourceAccount);

        //non existing account
        AccountKey nonExistingAccountKey = AccountKey.valueOf(3L);
        Account nonExistingAccount = new Account(nonExistingAccountKey, "john", "doe", 0.0);

        accountService.transfer(sourceAccount, nonExistingAccount, 3.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferFundsNegativeAmount() {
        //create source account
        AccountKey sourceAccountKey = AccountKey.valueOf(1L);
        Account sourceAccount = new Account(sourceAccountKey, "john", "doe", 10.0);

        accountService.createAccount(sourceAccount);

        //create target account
        AccountKey targetAccountKey = AccountKey.valueOf(2L);
        Account targetAccount = new Account(targetAccountKey, "john", "doe", 0.0);

        accountService.createAccount(targetAccount);

        accountService.transfer(sourceAccount, targetAccount, -3.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferFundsInsufficientFunds() {
        //create source account
        AccountKey sourceAccountKey = AccountKey.valueOf(1L);
        Account sourceAccount = new Account(sourceAccountKey, "john", "doe", 0.0);

        accountService.createAccount(sourceAccount);

        //create target account
        AccountKey targetAccountKey = AccountKey.valueOf(2L);
        Account targetAccount = new Account(targetAccountKey, "john", "doe", 0.0);

        accountService.createAccount(targetAccount);

        accountService.transfer(sourceAccount, targetAccount, 3.0);
    }
}
