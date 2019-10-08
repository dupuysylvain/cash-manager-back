package com.epitech.cashmanager.web.controller;

import com.epitech.cashmanager.dao.ArticleDao;
import com.epitech.cashmanager.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleDao articleDao;

    @RequestMapping(value="/Articles", method= RequestMethod.GET)
    public Iterable<Article> listArticles() {
        return articleDao.findAll();
    }

}
