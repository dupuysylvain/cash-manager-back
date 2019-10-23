package com.epitech.cashmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ArticleNotExist extends RuntimeException {
    public ArticleNotExist(int articleId) {
        super("Article " + articleId + " not found");
    }
}
