package com.epitech.cashmanager.service;

import com.epitech.cashmanager.repository.ArticleRepository;
import com.epitech.cashmanager.repository.CartRepository;
import com.epitech.cashmanager.repository.CartQuantityRepository;
import com.epitech.cashmanager.repository.UserRepository;
import com.epitech.cashmanager.exception.ArticleNotExist;
import com.epitech.cashmanager.exception.CashManagerException;
import com.epitech.cashmanager.exception.NotEnoughQuantity;
import com.epitech.cashmanager.model.*;
import com.epitech.cashmanager.tools.CartStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartQuantityRepository cartQuantityRepository;

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * Add an article to the cart with a given quantity
     *
     * @param username
     * @param articleId
     * @param quantity
     * @return
     */
    @Override
    public Cart addArticleToCart(String username, int articleId, int quantity) {
        Optional<Article> article = articleRepository.findById(articleId);
        Cart currentCart = findCurrentCart(username);
        Optional<CartQuantity> currentCartQuantity = findCartQuantityOfArticle(currentCart, articleId);
        CartQuantity newCartQuantity = new CartQuantity();

        if (quantity <= 0) {
            throw new CashManagerException("Trying to insert negative or neutral quantity !");
        } else if (article.isEmpty()) {
            throw new ArticleNotExist(articleId);
        } else if (article.get().getQuantity() < quantity) {
            throw new NotEnoughQuantity(article.get().getQuantity(), quantity);
        }

        if (currentCartQuantity.isPresent()) {
            CartQuantity cartQuantity = currentCartQuantity.get();
            cartQuantity.setQuantity(quantity);

            cartQuantityRepository.save(cartQuantity);
        } else {
            newCartQuantity.setId(new CartQuantityKey(articleId, currentCart.getId()));
            newCartQuantity.setQuantity(quantity);
            newCartQuantity.setArticle(article.get());
            currentCart.getArticlesWithQuantity().add(newCartQuantity);

            cartQuantityRepository.save(newCartQuantity);
        }

        return currentCart;
    }

    /**
     * Remove a given quantity of an article from the cart
     *
     * @param username
     * @param articleId
     * @param quantity
     * @return
     */
    @Override
    public Cart removeArticleFromCart(String username, int articleId, int quantity) {
        Cart currentCart = findCurrentCart(username);
        Optional<CartQuantity> currentCartQuantity = findCartQuantityOfArticle(currentCart, articleId);

        if (quantity <= 0) {
            throw new CashManagerException("Trying to delete negative or neutral quantity !");
        } else if (currentCartQuantity.isEmpty()) {
            throw new CashManagerException("Trying to delete non existing article in the cart");
        } else if (currentCartQuantity.get().getQuantity() < quantity) {
            throw new CashManagerException("Trying to delete more quantity than available");
        }

        if (currentCartQuantity.get().getQuantity() == quantity) {
            currentCart.getArticlesWithQuantity().remove(currentCartQuantity.get());
            cartQuantityRepository.delete(currentCartQuantity.get());
        } else {
            CartQuantity cartQuantity = currentCartQuantity.get();
            cartQuantity.setQuantity(cartQuantity.getQuantity() - quantity);
            cartQuantityRepository.save(cartQuantity);
        }

        return currentCart;
    }

    /**
     * Find the cart of the user connected (create a new one if not exist)
     *
     * @param username username if the current user
     * @return current cart or new one
     */
    public Cart findCurrentCart(String username) {
        User currentUser = findCurrentUser(username);
        Optional<Cart> cart = Optional.empty();

        if (currentUser.getCarts() != null) {
            cart = currentUser.getCarts().stream().filter(c -> CartStatus.NEW.equals(c.getStatus())).findFirst();
        }

        return cart.orElseGet(() -> initNewCart(currentUser));
    }

    /**
     * Create a new empty cart
     *
     * @param user
     * @return
     */
    private Cart initNewCart(User user) {
        Cart cart = new Cart();

        cart.setStatus(CartStatus.NEW);
        cart.setUser(user);
        cart.setArticlesWithQuantity(new HashSet<>());

        return cartRepository.save(cart);
    }

    /**
     * Get information about the current user
     *
     * @param username
     * @return
     */
    private User findCurrentUser(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Retrieve relation between an article and the cart
     *
     * @param cart
     * @param articleId
     * @return
     */
    private Optional<CartQuantity> findCartQuantityOfArticle(Cart cart, int articleId) {
        Optional<CartQuantity> cartQuantity = Optional.empty();

        if (cart.getArticlesWithQuantity() != null) {
            cartQuantity = cart.getArticlesWithQuantity().stream().filter(
                    (cq) -> articleId == cq.getArticle().getId()).findFirst();
        }

        return cartQuantity;
    }
}
