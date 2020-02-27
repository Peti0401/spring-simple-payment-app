package hu.gyarmatip.simplepaymentapp.service;


import hu.gyarmatip.simplepaymentapp.dto.TransactionDto;
import hu.gyarmatip.simplepaymentapp.entity.Account;
import hu.gyarmatip.simplepaymentapp.entity.Transaction;
import hu.gyarmatip.simplepaymentapp.exception.AccountNotFoundException;
import hu.gyarmatip.simplepaymentapp.exception.InsufficientBalanceException;

import java.util.List;

public interface TransactionService {

    /**
     * Returns a list of all the transactions.
     * @return a list of all the transactions.
     */
    List<Transaction> findAllTransactions();

    /**
     * Returns a list of all the transactions of an account identified by accountId.
     * @param accountId must not be {@literal null}.
     * @return a list of all the transactions of an account identified by accountId.
     * @throws AccountNotFoundException if no account exists with the specified accountId.
     */
    List<Transaction> findAllTransactionsByAccount(Long accountId) throws AccountNotFoundException;

    /**
     * Commits a transaction if both accounts are existent and the sender has sufficient balance
     * to complete the transaction.
     * @param transactionDto must not be {@literal null} nor must it have a field which is {@literal null}.
     * @throws AccountNotFoundException if no account exists with the accountId specified in the Transaction object.
     * @throws InsufficientBalanceException if the sender's balance is insufficient (smaller than the sum field of the Transaction object)
     * @throws RuntimeException if the {@literal sum} field of {@literal transactionDto} is a non-positive number
     */
    void completeTransaction(TransactionDto transactionDto) throws AccountNotFoundException, InsufficientBalanceException;

}
