package com.devexperts.account;

import com.devexperts.exception.InsufficientFundsException;

public class Account {
    private final AccountKey accountKey;
    private final String firstName;
    private final String lastName;
    private Double balance;

    public Account(AccountKey accountKey, String firstName, String lastName, double balance) {
        this.accountKey = accountKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    public AccountKey getAccountKey() {
        return accountKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void changeBalance(Double sum) {
        balance += sum;
    }

    public boolean testBalance(Double sum) {
        if (sum < 0 && balance < Math.abs(sum)) {
            throw new InsufficientFundsException("not enough money", this);
        }
        return true;
    }
}
