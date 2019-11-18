package com.epitech.cashmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotEnoughQuantity extends RuntimeException {
    public NotEnoughQuantity(int maxQuantity, int quantity) {
        super("Not enough quantity available (available : " + maxQuantity + ", given : " + quantity + ")");
    }
}
