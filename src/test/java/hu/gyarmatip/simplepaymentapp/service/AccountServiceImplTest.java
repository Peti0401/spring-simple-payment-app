package hu.gyarmatip.simplepaymentapp.service;

import hu.gyarmatip.simplepaymentapp.SimplePaymentAppApplication;
import hu.gyarmatip.simplepaymentapp.dto.AccountDto;
import hu.gyarmatip.simplepaymentapp.entity.Account;
import hu.gyarmatip.simplepaymentapp.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@SpringBootTest(classes = SimplePaymentAppApplication.class)
class AccountServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    @DirtiesContext
    void save_AccountDtoNotNull_AccountIsSaved() {
        AccountDto accountDto = new AccountDto();
        accountDto.setName("Johnny Ive");
        accountDto.setEmail("johnny@icloud.com");
        Account savedAccount = accountService.save(accountDto);
        logger.info("Saved account -> {}", savedAccount);

        assertTrue(accountRepository.existsById(savedAccount.getId()));
    }

    @Test
    void save_AccountDtoIsNull_NullPointerExceptionIsThrown() {
        assertThrows(NullPointerException.class, () -> accountService.save(null));
    }

    @Test
    @DirtiesContext
    void deleteById_IdExists_AccountIsDeleted() {
        final Long id = 10L;
        assumeTrue(accountRepository.existsById(id));
        logger.info("Deleting account -> {}", accountRepository.findById(id).get());
        assertDoesNotThrow(() -> accountService.deleteById(id));
        assertFalse(accountRepository.existsById(id));
    }

}