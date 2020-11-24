package com.code.codepractice.threadTest;

import com.code.codepractice.exception.InsufficientFundsException;
import com.code.codepractice.dto.Account;

import java.math.BigDecimal;

public class TransferWorker {
    private static final Object tieLock = new Object();

    public void transferMoney(final Account fromAcct,
                              final Account toAcct,
                              final BigDecimal amount) {
        class Helper {
            public void transfer() throws InsufficientFundsException {
                if (fromAcct.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientFundsException();
                } else {
                    fromAcct.withdraw(amount);
                    toAcct.deposit(amount);
                }
            }
        }
        int fromHash = System.identityHashCode(fromAcct);
        int toHash = System.identityHashCode(toAcct);
        if (fromHash < toHash) {
            //锁顺序
            synchronized (fromAcct) {
                synchronized (toAcct) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            //锁顺序
            synchronized (toAcct) {
                synchronized (fromAcct) {
                    new Helper().transfer();
                }
            }
        } else {
            //可能拥有相同散列，则在加一个锁，保证每次只有一个线程以未知的顺序获得锁
            //可能会成为并发瓶颈（类似于整个程序中只有一个锁 tieLock）
            //但是 System.identityHashCode出现散列冲突频率非常低
            //以最小代价换了安全性
            synchronized (tieLock) {
                //锁顺序
                synchronized (fromAcct) {
                    synchronized (toAcct) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }
}
