package hu.gyarmatip.simplepaymentapp.controllers;

import hu.gyarmatip.simplepaymentapp.dto.TransactionDto;
import hu.gyarmatip.simplepaymentapp.entity.Transaction;
import hu.gyarmatip.simplepaymentapp.exception.AccountNotFoundException;
import hu.gyarmatip.simplepaymentapp.exception.InsufficientBalanceException;
import hu.gyarmatip.simplepaymentapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction-service")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> findAllTransactions() {
        return ResponseEntity.ok(transactionService.findAllTransactions());
    }

    @GetMapping("/transactions/{accountId}")
    public ResponseEntity<List<Transaction>> findAllTransactionsByAccount(@PathVariable Long accountId)
            throws AccountNotFoundException {
        return ResponseEntity.ok(transactionService.findAllTransactionsByAccount(accountId));
    }

    @PostMapping("/complete-transaction")
    public ResponseEntity<Transaction> completeTransaction(@RequestBody TransactionDto transactionDto)
            throws AccountNotFoundException, InsufficientBalanceException {
        Transaction transaction = transactionService.completeTransaction(transactionDto);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}
