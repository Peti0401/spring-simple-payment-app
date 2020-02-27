package hu.gyarmatip.simplepaymentapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = AccountNotFoundException.ERROR_MESSAGE)
public class AccountNotFoundException extends RuntimeException {

    static final String ERROR_MESSAGE = "No account with such id.";

    public AccountNotFoundException(Long accountId) {
        super("No account was found with id " + accountId + ".");
    }
}