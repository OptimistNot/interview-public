package com.devexperts.rest;

import com.devexperts.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class AbstractAccountController {
    abstract ResponseEntity<Void> transfer(@RequestBody Transaction transaction);
}
