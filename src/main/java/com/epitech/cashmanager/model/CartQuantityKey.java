package com.epitech.cashmanager.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartQuantityKey implements Serializable {
 
    @Column(name = "article_id")
    int articleId;
 
    @Column(name = "cart_id")
    int cartId;

    public CartQuantityKey() {
    }

    public CartQuantityKey(int articleId, int cartId) {
        this.articleId = articleId;
        this.cartId = cartId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartQuantityKey that = (CartQuantityKey) o;
        return Objects.equals(articleId, that.articleId) &&
                Objects.equals(cartId, that.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, cartId);
    }

    @Override
    public String toString() {
        return "CartQuantityKey{" +
                "articleId=" + articleId +
                ", cartId=" + cartId +
                '}';
    }
}