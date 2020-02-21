package com.devexperts.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractAccountController {
    abstract ResponseEntity<Void> transfer(@RequestParam("source_id") long sourceId,
                                           @RequestParam("target_id") long targetId,
                                           @RequestParam("amount") double amount);
}
