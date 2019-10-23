package com.epitech.cashmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "article_cart")
public class CartQuantity {

    @EmbeddedId
    @JsonIgnore
    private CartQuantityKey id;

    @ManyToOne
    @MapsId("article_id")
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @MapsId("cart_id")
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    private int quantity;

    public CartQuantity() {
    }

    public CartQuantityKey getId() {
        return id;
    }

    public void setId(CartQuantityKey id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartQuantity{" +
                "id=" + id +
                ", article=" + article +
                ", cart=" + cart +
                ", quantity=" + quantity +
                '}';
    }
}
