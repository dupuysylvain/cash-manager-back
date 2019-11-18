package com.epitech.cashmanager.dao;

import com.epitech.cashmanager.model.CartQuantity;
import com.epitech.cashmanager.model.CartQuantityKey;
import org.springframework.data.repository.CrudRepository;

public interface CartQuantityDao extends CrudRepository<CartQuantity, CartQuantityKey> {
}
