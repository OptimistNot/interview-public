package com.devexperts.service;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
public class AccountServiceImpl implements AccountService {

    private Map<AccountKey, Account> accounts = new HashMap<>();

    @Override
    public void clear() {
        accounts.clear();
    }

    @Override
    public void createAccount(Account account) {
        if (isNull(account)) {
            throw new IllegalArgumentException("Invalid account");
        }

        if (isNull(account.getAccountKey())) {
            throw new IllegalArgumentException("Invalid account key");
        }
        accounts.put(account.getAccountKey(), account);
    }

    @Override
    public Account getAccount(long id) {
        return accounts.get(AccountKey.valueOf(id));
    }

    @Override
    public void transfer(Account source, Account target, double amount) {
        if (isNull(target) || isNull(source)) {
            throw new IllegalArgumentException("Invalid account");
        }

        if (isNull(accounts.get(source.getAccountKey())) || isNull(accounts.get(target.getAccountKey()))) {
            throw new IllegalArgumentException("Account doesn't exist");
        }

        //assuming amount cannot be negative
        if (amount < 0) {
            throw new IllegalArgumentException("Amount should be positive");
        }

        if (source.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        source.setBalance(source.getBalance() - amount);
        target.setBalance(target.getBalance() + amount);
    }
}
