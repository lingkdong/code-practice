package com.code.codepractice.dto;

import java.math.BigDecimal;

public class Account {
    private BigDecimal balance = new BigDecimal(10000);//余额

    /**
     * deposit:[dəˈpäzət] 存款
     *
     * @param amount
     */
    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    //转出
    public void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    //转账 account1 to account2
    public static void transfer(Account account1, Account account2, BigDecimal amount) {
        account1.withdraw(amount);
        account2.deposit(amount);
    }
}
