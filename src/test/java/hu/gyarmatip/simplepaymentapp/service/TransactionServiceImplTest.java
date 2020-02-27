package hu.gyarmatip.simplepaymentapp.service;

import hu.gyarmatip.simplepaymentapp.SimplePaymentAppApplication;
import hu.gyarmatip.simplepaymentapp.dto.TransactionDto;
import hu.gyarmatip.simplepaymentapp.entity.Account;
import hu.gyarmatip.simplepaymentapp.entity.Transaction;
import hu.gyarmatip.simplepaymentapp.exception.AccountNotFoundException;
import hu.gyarmatip.simplepaymentapp.exception.InsufficientBalanceException;
import hu.gyarmatip.simplepaymentapp.repository.AccountRepository;
import hu.gyarmatip.simplepaymentapp.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@SpringBootTest(classes = SimplePaymentAppApplication.class)
class TransactionServiceImplTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;


    @Test
    void completeTransaction_AtLeastOneAccountIdDoesNotExist_AccountNotFoundExceptionIsThrown() {
        final Long senderId = 5L;
        final Long recipientId = 100L;
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setSenderAccountId(senderId);
        transactionDto.setRecipientAccountId(recipientId);
        transactionDto.setSum(1000.0);
        assertThrows(AccountNotFoundException.class, () -> transactionService.completeTransaction(transactionDto));
    }

    @Test
    void completeTransaction_TransactionSumIsNonPositive_RuntimeExceptionIsThrown() {
        final Long senderId = 5L;
        final Long recipientId = 100L;
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setSenderAccountId(senderId);
        transactionDto.setRecipientAccountId(recipientId);
        transactionDto.setSum(-10.0);
        assertThrows(RuntimeException.class, () -> transactionService.completeTransaction(transactionDto));
    }

    @Test
    void completeTransaction_SendersBalanceIsInsufficient_InsufficientBalanceExceptionIsThrown() {
        final Long senderId = 5L;
        final Long recipientId = 10L;
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setSenderAccountId(senderId);
        transactionDto.setRecipientAccountId(recipientId);
        transactionDto.setSum(250.0);
        assertThrows(InsufficientBalanceException.class, () -> transactionService.completeTransaction(transactionDto));
    }

    @Test
    @DirtiesContext
    @Transactional
    void completeTransaction_AccountsExist_And_SendersBalanceIsSufficient_And_SumIsPositive_TransactionIsCompleted() {
        final Long senderId = 5L;
        final Long recipientId = 10L;
        assumeTrue(accountRepository.existsById(senderId));
        Account senderAccount = accountRepository.findById(senderId).get();
        Double oldSenderAccountBalance = senderAccount.getBalance();
        int oldSentSize = senderAccount.getSentTransactions().size();

        assumeTrue(accountRepository.existsById(recipientId));
        Account recipientAccount = accountRepository.findById(recipientId).get();
        Double oldRecipientAccountBalance = recipientAccount.getBalance();
        int oldReceivedSize = recipientAccount.getReceivedTransactions().size();

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setSenderAccountId(senderId);
        transactionDto.setRecipientAccountId(recipientId);
        final Double sum = 100.0;
        transactionDto.setSum(sum);
        List<Transaction> oldTransactions = ((List<Transaction>) transactionRepository.findAll());
        int oldNumberOfAllTransactions = oldTransactions.size();

        assertDoesNotThrow(() -> transactionService.completeTransaction(transactionDto), "Balance should be sufficient");
        assertEquals(oldSenderAccountBalance - sum, senderAccount.getBalance(), "Sender's balance should be decremented");
        assertEquals(oldRecipientAccountBalance + sum, recipientAccount.getBalance(), "Recipient's balance should be incremented");
        assertEquals(oldSentSize + 1, senderAccount.getSentTransactions().size(), "Sender should have one more sent transaction");
        assertEquals(oldReceivedSize + 1, recipientAccount.getReceivedTransactions().size(), "Recipient should have one more received transaction");

        List<Transaction> transactions = ((List<Transaction>) transactionRepository.findAll());
        int numberOfAllTransactions = transactions.size();
        assertEquals(numberOfAllTransactions, oldNumberOfAllTransactions + 1, "The number of all transactions should be incremented by one");
    }
}