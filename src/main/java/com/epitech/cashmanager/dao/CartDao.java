package com.epitech.cashmanager.dao;

import com.epitech.cashmanager.model.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartDao extends CrudRepository<Cart, String> {

    List<Cart> findByStatus(String status);
}
