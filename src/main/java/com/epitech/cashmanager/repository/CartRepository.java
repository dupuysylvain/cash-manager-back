package com.epitech.cashmanager.repository;

import com.epitech.cashmanager.model.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart, String> {
}
