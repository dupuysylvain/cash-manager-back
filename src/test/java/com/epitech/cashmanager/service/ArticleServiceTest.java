package com.epitech.cashmanager.service;

import com.epitech.cashmanager.model.Article;
import com.epitech.cashmanager.repository.ArticleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Before
    public void init() {
        Article screen = new Article();
        screen.setQuantity(20);
        screen.setBarcode("12345678");
        screen.setPrice(new BigDecimal(200));
        screen.setName("MSI Screen 144hz");

        Article mobile = new Article();
        mobile.setQuantity(10);
        mobile.setBarcode("69673206");
        mobile.setPrice(new BigDecimal(900));
        mobile.setName("Iphone X");

        when(articleRepository.findAll()).thenReturn(new ArrayList<>(List.of(screen, mobile)));
        when(articleRepository.save(any(Article.class))).thenAnswer((Answer<Article>) invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            return (Article) args[0];
        });
    }

    @Test
    public void getAllArticlesTest() {
        ArrayList<Article> allArticles = (ArrayList<Article>) articleService.getAllArticles();

        assertEquals("The size of articles is 2", 2, allArticles.size());
        assertEquals("screen price is 200", new BigDecimal(200), allArticles.get(0).getPrice());
        assertEquals("mobile quantity is 10", 10, allArticles.get(1).getQuantity());
    }

    @Test
    public void createArticleTest(){
        Article article = new Article();
        article.setQuantity(10);
        article.setBarcode("69673206");
        article.setPrice(new BigDecimal(900));
        article.setName("Random article");

        Article newArticle = articleService.createArticle(article);

        assertNotNull("Should return the new article", newArticle);
        assertEquals("Article price is 900", new BigDecimal(900), newArticle.getPrice());
    }
}
