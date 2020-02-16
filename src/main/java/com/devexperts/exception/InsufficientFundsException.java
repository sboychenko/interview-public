package com.devexperts.exception;

import com.devexperts.account.Account;

public class InsufficientFundsException extends IllegalStateException  {

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Account account) {
        super(message + account.getAccountKey());
    }

}
