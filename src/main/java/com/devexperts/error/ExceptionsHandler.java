package com.devexperts.error;

import com.devexperts.error.exception.AccountNotFoundException;
import com.devexperts.error.exception.InsufficientAccountBalance;
import com.devexperts.error.exception.InvalidAmountParameter;
import com.devexperts.log.Loggable;
import com.devexperts.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@ControllerAdvice
public class ExceptionsHandler implements Loggable {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Error> handleAccountNotFoundException(AccountNotFoundException exception,
                                                            HttpServletRequest request) {

        logger().error(exception.getMessage(), exception);

        return new ResponseEntity<>(buildError(exception, request, HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InsufficientAccountBalance.class)
    public ResponseEntity<Error> handleAccountNotFoundException(InsufficientAccountBalance exception,
                                                            HttpServletRequest request) {

        logger().error(exception.getMessage(), exception);

        return new ResponseEntity<>(buildError(exception, request, HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidAmountParameter.class)
    public ResponseEntity<Error> handleIllegalArgumentException(InvalidAmountParameter exception,
                                                            HttpServletRequest request) {

        logger().error(exception.getMessage(), exception);

        return new ResponseEntity<>(buildError(exception, request, HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> handleIllegalArgumentException(IllegalArgumentException exception,
                                                            HttpServletRequest request) {

        logger().error(exception.getMessage(), exception);

        return new ResponseEntity<>(buildError(exception, request, HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    private Error buildError(RuntimeException exception, HttpServletRequest request, HttpStatus httpStatus) {
        return new Error.Builder()
                .withTimestamp(OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()))
                .withException(exception.getClass().getName())
                .withMessage(exception.getMessage())
                .withPath(request.getRequestURI())
                .withStatus(httpStatus.value())
                .withError(httpStatus.getReasonPhrase())
                .build();
    }
}