package com.epitech.cashmanager.web.controller;

import com.epitech.cashmanager.model.Article;
import com.epitech.cashmanager.model.User;
import com.epitech.cashmanager.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * controller of articles
 * STANDARD and ADMIN users can use it
 */
@RestController
@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * get list of all articles
     * @return
     */
    @RequestMapping(value="/api/articles", method= RequestMethod.GET)
    public Iterable<Article> listArticles() {
        return articleService.getAllArticles();
    }

    /**
     * Create an article
     * @param article
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @RequestMapping(value="/api/articles", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Article createUser(@RequestBody Article article) {
        return articleService.createArticle(article);
    }
}
