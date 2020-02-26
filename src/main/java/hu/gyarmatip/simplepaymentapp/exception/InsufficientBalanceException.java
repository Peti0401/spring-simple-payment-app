package hu.gyarmatip.simplepaymentapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = InsufficientBalanceException.ERROR_MESSAGE)
public class InsufficientBalanceException extends Exception {

    static final String ERROR_MESSAGE = "The sender account's balance is insufficient for the transaction.";

    public InsufficientBalanceException() {
        super(ERROR_MESSAGE);
    }
}