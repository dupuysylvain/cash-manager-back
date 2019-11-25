package com.epitech.cashmanager.web.controller;

import com.epitech.cashmanager.model.*;
import com.epitech.cashmanager.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * Add an article to the cart with a given quantity
     * @param principal
     * @param articleId
     * @param quantity
     * @return
     */
    @PostMapping("/api/cart/{articleId}/{quantity}")
    @ResponseStatus(HttpStatus.CREATED)
    public Cart addToCart(Principal principal, @PathVariable int articleId, @PathVariable int quantity) {
        return cartService.addArticleToCart(principal.getName(), articleId, quantity);
    }

    @DeleteMapping("/api/cart/{articleId}/{quantity}")
    public Cart removeFromCart(Principal principal, @PathVariable int articleId, @PathVariable int quantity) {
        return cartService.removeArticleFromCart(principal.getName(), articleId, quantity);
    }

    /**
     * Get information about the current cart
     * @param principal
     * @return
     */
    @GetMapping("/api/cart")
    public Cart currentCart(Principal principal) {
        return cartService.findCurrentCart(principal.getName());
    }

    /**
     * Cancel current cart
     * @param principal
     */
    @DeleteMapping("/api/cart")
    public void deleteCart(Principal principal) {
        cartService.deleteCart(principal.getName());
    }
}
