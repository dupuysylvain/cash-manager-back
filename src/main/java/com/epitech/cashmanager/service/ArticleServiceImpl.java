package com.epitech.cashmanager.service;

import com.epitech.cashmanager.dao.ArticleDao;
import com.epitech.cashmanager.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public Iterable<Article> getAllArticles() {
        return articleDao.findAll();
    }
}
