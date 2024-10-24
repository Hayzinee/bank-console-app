package org.example.model;

import java.math.BigDecimal;

public abstract class Account {
    private String accountNumber;
    private Customer accountHolder;
    private BigDecimal balance;

    public Account(String accountNumber, Customer accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = BigDecimal.valueOf(0.0);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Customer getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(Customer accountHolder) {
        this.accountHolder = accountHolder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public boolean withdraw(BigDecimal amount){
        if(amount.compareTo(balance) <= 0){
            balance = balance.subtract(amount);
            System.out.println("Withdrawal successful. Balance is" + balance);
            return true;
        } else {
            System.out.println("Insufficient funds");
            return false;
        }
    }

    public void deposit (BigDecimal amount){
        balance = balance.add(amount);
        System.out.println("You have received " + amount + ". New balance is " + balance);
    }
}
