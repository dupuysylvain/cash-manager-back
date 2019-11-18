package com.epitech.cashmanager.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status")
    private char status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart")
    Set<CartQuantity> articlesWithQuantity;

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public Set<CartQuantity> getArticlesWithQuantity() {
        return articlesWithQuantity;
    }

    public void setArticlesWithQuantity(Set<CartQuantity> articlesWithQuantity) {
        this.articlesWithQuantity = articlesWithQuantity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
