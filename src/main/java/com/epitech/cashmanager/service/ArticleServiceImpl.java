package com.epitech.cashmanager.service;

import com.epitech.cashmanager.repository.ArticleRepository;
import com.epitech.cashmanager.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Iterable<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }
}
