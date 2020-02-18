package com.devexperts.service;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import com.devexperts.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class AccountServiceImpl implements AccountService {

    private final ConcurrentMap<AccountKey, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public void clear() {
        accounts.clear();
    }

    @Override
    public void createAccount(Account account) {
        accounts.put(account.getAccountKey(), account);
    }

    @Override
    public Account getAccount(long id) {
        return accounts.get(AccountKey.valueOf(id));
    }

    @Override
    public void transfer(Account source, Account target, double amount) {

        if (!accountExist(source) || !accountExist(target)) {
            throw new NotFoundException("Account not exist!");
        }
        if (source.getAccountKey().equals(target.getAccountKey())) {
            throw new IllegalArgumentException("Account should be different");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount should be positive");
        }


        source.testBalance(-amount);

        synchronized (this) {
            // Do transfer in two steps
            source.changeBalance(-amount);
            target.changeBalance(amount);
        }
    }

    private boolean accountExist(Account account) {
        return account != null && accounts.get(account.getAccountKey()) != null;
    }
}
