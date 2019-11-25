package com.epitech.cashmanager.service;

import com.epitech.cashmanager.model.Article;

public interface ArticleService {
    Iterable<Article> getAllArticles();
    Article createArticle(Article article);
}
