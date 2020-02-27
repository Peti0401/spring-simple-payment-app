package hu.gyarmatip.simplepaymentapp.service;


import hu.gyarmatip.simplepaymentapp.dto.AccountDto;
import hu.gyarmatip.simplepaymentapp.entity.Account;
import hu.gyarmatip.simplepaymentapp.exception.AccountNotFoundException;

import java.util.List;

public interface AccountService {

    /**
     * Saves a given Account.
     * @param accountDto must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws NullPointerException in case the given {@literal accountDto} or any of its fields is {@literal null}.
     */
    Account save(AccountDto accountDto);

    /**
     *
     * @param accountId must not be {@literal null}.
     * @throws AccountNotFoundException if no account exists with the specified accountId.
     * @throws IllegalArgumentException in case the given {@literal accountId} is {@literal null}.
     */
    void deleteById(Long accountId) throws AccountNotFoundException;

    /**
     * Returns a list of all the accounts.
     * @return a list of all the accounts.
     */
    List<Account> findAllAccounts();

}
