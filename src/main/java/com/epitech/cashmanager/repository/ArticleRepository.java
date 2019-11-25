package com.epitech.cashmanager.repository;

import com.epitech.cashmanager.model.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Integer> {

}
