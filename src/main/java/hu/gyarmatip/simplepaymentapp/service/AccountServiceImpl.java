package hu.gyarmatip.simplepaymentapp.service;

import hu.gyarmatip.simplepaymentapp.dto.AccountDto;
import hu.gyarmatip.simplepaymentapp.entity.Account;
import hu.gyarmatip.simplepaymentapp.exception.AccountNotFoundException;
import hu.gyarmatip.simplepaymentapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(AccountDto accountDto) {
        Account account = new Account();
        account.setName(accountDto.getName());
        account.setEmail(accountDto.getEmail());

        return accountRepository.save(account);
    }

    @Override
    public void deleteById(Long accountId) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        Account account = accountOptional.orElseThrow(() -> new AccountNotFoundException(accountId));
        accountRepository.delete(account);
    }

    @Override
    public List<Account> findAllAccounts() {
        return ((List<Account>) accountRepository.findAll());
    }
}
