package com.example.transaction.transaction;

public interface BarService {

    void hasTransactional() throws RollbackException;
    void noTransactional() throws RollbackException;

    void hasTransactionalNoException() throws RollbackException;

}
