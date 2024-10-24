package org.example.model;

import org.example.util.AccountUtil;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User{
    private final List<Account> accounts;

    public Customer(String name, String userid, String email, String password) {
        super(name, userid, email, password, "Customer");
        this.accounts = new ArrayList<>();
        accounts.add(createDefaultAccount());
    }

    public List<Account> getAccounts() {

        return accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public Account createDefaultAccount(){
        String accountNumber = AccountUtil.generateRandomAccountNumber(10);
        return new SavingsAccount(accountNumber,this);
    }
}
