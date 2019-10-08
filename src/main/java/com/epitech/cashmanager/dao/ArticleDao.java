package com.epitech.cashmanager.dao;

import com.epitech.cashmanager.model.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleDao extends CrudRepository<Article, String> {

}
