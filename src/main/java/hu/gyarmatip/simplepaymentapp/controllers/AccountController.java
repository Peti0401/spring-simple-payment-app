package hu.gyarmatip.simplepaymentapp.controllers;

import hu.gyarmatip.simplepaymentapp.dto.AccountDto;
import hu.gyarmatip.simplepaymentapp.entity.Account;
import hu.gyarmatip.simplepaymentapp.exception.AccountNotFoundException;
import hu.gyarmatip.simplepaymentapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account-service")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/save")
    public ResponseEntity<Account> save(@RequestBody AccountDto accountDto) {
        Account savedAccount = accountService.save(accountDto);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws AccountNotFoundException {
        accountService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> findAllAccounts() {
        return ResponseEntity.ok(accountService.findAllAccounts());
    }

}
