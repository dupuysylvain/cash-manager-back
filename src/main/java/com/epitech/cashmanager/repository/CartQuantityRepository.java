package com.epitech.cashmanager.repository;

import com.epitech.cashmanager.model.CartQuantity;
import com.epitech.cashmanager.model.CartQuantityKey;
import org.springframework.data.repository.CrudRepository;

public interface CartQuantityRepository extends CrudRepository<CartQuantity, CartQuantityKey> {
}
