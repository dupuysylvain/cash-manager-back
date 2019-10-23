package com.epitech.cashmanager.service;

import com.epitech.cashmanager.model.Cart;

public interface CartService {
    Cart findCurrentCart(String username);
    Cart addArticleToCart(String username, int articleId, int quantity);
    Cart removeArticleFromCart(String username, int articleId, int quantity);
}
