package hu.gyarmatip.simplepaymentapp.service;

import hu.gyarmatip.simplepaymentapp.dto.TransactionDto;
import hu.gyarmatip.simplepaymentapp.entity.Account;
import hu.gyarmatip.simplepaymentapp.entity.Transaction;
import hu.gyarmatip.simplepaymentapp.exception.AccountNotFoundException;
import hu.gyarmatip.simplepaymentapp.exception.InsufficientBalanceException;
import hu.gyarmatip.simplepaymentapp.repository.AccountRepository;
import hu.gyarmatip.simplepaymentapp.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> findAllTransactions() {
        return ((List<Transaction>) transactionRepository.findAll());
    }

    @Override
    public List<Transaction> findAllTransactionsByAccount(Long accountId) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        Account account = accountOptional.orElseThrow(() -> new AccountNotFoundException(accountId));
        List<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(account.getSentTransactions());
        allTransactions.addAll(account.getReceivedTransactions());
        allTransactions.sort(Comparator.comparing(Transaction::getDate));
        return allTransactions;
    }

    @Override
    public Transaction completeTransaction(TransactionDto transactionDto) throws AccountNotFoundException, InsufficientBalanceException {
        if (transactionDto.getSum() <= 0) {
            throw new RuntimeException("Sum of transaction must be greater than 0");
        }

        // check whether the transacting account exist
        Optional<Account> senderAccountOptional = accountRepository.findById(transactionDto.getSenderAccountId());
        Account senderAccount = senderAccountOptional
                .orElseThrow(() -> new AccountNotFoundException(transactionDto.getSenderAccountId()));

        Optional<Account> recipientAccountOptional = accountRepository.findById(transactionDto.getRecipientAccountId());
        Account recipientAccount = recipientAccountOptional
                .orElseThrow(() -> new AccountNotFoundException(transactionDto.getRecipientAccountId()));

        // check whether the sender's balance is sufficient for the transfer
        if (senderAccount.getBalance() < transactionDto.getSum()) {
            throw new InsufficientBalanceException();
        }

        // if everything is alright, create and complete the transaction
        Transaction transaction = new Transaction();
        transaction.setSenderAccount(senderAccount);
        transaction.setRecipientAccount(recipientAccount);
        transaction.setSum(transactionDto.getSum());

        // save the transaction to the database
        Transaction savedTransaction = transactionRepository.save(transaction);

        // decrement sender's balance
        senderAccount.setBalance(senderAccount.getBalance() - transactionDto.getSum());
        // increment recipient's balance
        recipientAccount.setBalance(recipientAccount.getBalance() + transactionDto.getSum());

        // update the transaction set of both account instances
        senderAccount.getSentTransactions().add(savedTransaction);
        recipientAccount.getReceivedTransactions().add(savedTransaction);

        // update the transacting accounts in the database as well
        accountRepository.saveAll(Arrays.asList(senderAccount, recipientAccount));

        return transaction;
    }
}
